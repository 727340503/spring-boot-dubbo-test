package com.cherrypicks.tcc.cms.api.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.HttpPutFormContentFilter;

import com.cherrypicks.tcc.util.Constants;

public class RestfulFilter extends HttpPutFormContentFilter {

    /** Default method parameter: <code>_method</code> */
    public static final String DEFAULT_METHOD_PARAM = "_method";

    private String methodParam = DEFAULT_METHOD_PARAM;

    private final FormHttpMessageConverter formConverter = new AllEncompassingFormHttpMessageConverter();

    /**
     * Set the parameter name to look for HTTP methods.
     *
     * @see #DEFAULT_METHOD_PARAM
     */
    public void setMethodParam(final String methodParam) {
        Assert.hasText(methodParam, "'methodParam' must not be empty");
        this.methodParam = methodParam;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException {

        // set Lang to resolved AppErrorController request can't get Lang
        logger.info(request.getParameter(Constants.LANG));
        request.setAttribute(Constants.LANG, request.getParameter(Constants.LANG));

        String method = request.getParameter(this.methodParam);
        if ("POST".equalsIgnoreCase(request.getMethod()) && StringUtils.hasLength(method)) {
            method = method.toUpperCase(Locale.ENGLISH);
        } else {
            method = request.getMethod().toUpperCase(Locale.ENGLISH);
        }

        if (("DELETE".equals(method) || "PUT".equals(method) || "PATCH".equals(method)) && isFormContentType(request)) {
            final HttpInputMessage inputMessage = new ServletServerHttpRequest(request) {
                @Override
                public InputStream getBody() throws IOException {
                    return request.getInputStream();
                }
            };
            final MultiValueMap<String, String> formParameters = formConverter.read(null, inputMessage);
            final HttpServletRequest wrapper = new HttpPutFormContentRequestWrapper(request, method, formParameters);
            filterChain.doFilter(wrapper, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isFormContentType(final HttpServletRequest request) {
        final String contentType = request.getContentType();
        if (contentType != null) {
            try {
                final MediaType mediaType = MediaType.parseMediaType(contentType);
                return (MediaType.APPLICATION_FORM_URLENCODED.includes(mediaType));
            } catch (final IllegalArgumentException ex) {
                logger.error(ex.getMessage(), ex);
                return false;
            }
        } else {
            return false;
        }
    }

    private static class HttpPutFormContentRequestWrapper extends HttpServletRequestWrapper {

        private final String method;
        private final MultiValueMap<String, String> formParameters;

        HttpPutFormContentRequestWrapper(final HttpServletRequest request, final String method,
                final MultiValueMap<String, String> parameters) {
            super(request);
            this.method = method;
            this.formParameters = (parameters != null) ? parameters : new LinkedMultiValueMap<String, String>();
        }

        @Override
        public String getMethod() {
            return this.method;
        }

        @Override
        public String getParameter(final String name) {
            final String queryStringValue = super.getParameter(name);
            final String formValue = this.formParameters.getFirst(name);
            return (queryStringValue != null) ? queryStringValue : formValue;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            final Map<String, String[]> result = new LinkedHashMap<String, String[]>();
            final Enumeration<String> names = this.getParameterNames();
            while (names.hasMoreElements()) {
                final String name = names.nextElement();
                result.put(name, this.getParameterValues(name));
            }
            return result;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            final Set<String> names = new LinkedHashSet<String>();
            names.addAll(Collections.list(super.getParameterNames()));
            names.addAll(this.formParameters.keySet());
            return Collections.enumeration(names);
        }

        @Override
        public String[] getParameterValues(final String name) {
            final String[] queryStringValues = super.getParameterValues(name);
            final List<String> formValues = this.formParameters.get(name);
            if (formValues == null) {
                return queryStringValues;
            } else if (queryStringValues == null) {
                return formValues.toArray(new String[formValues.size()]);
            } else {
                final List<String> result = new ArrayList<String>();
                result.addAll(Arrays.asList(queryStringValues));
                result.addAll(formValues);
                return result.toArray(new String[result.size()]);
            }
        }
    }

}
