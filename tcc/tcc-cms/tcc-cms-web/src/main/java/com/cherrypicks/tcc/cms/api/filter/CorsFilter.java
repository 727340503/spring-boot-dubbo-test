package com.cherrypicks.tcc.cms.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.cherrypicks.tcc.util.Constants;



public class CorsFilter implements Filter {

    private final String allowedOrigins;
    private final String allowCredentials;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CorsFilter(final String allowedOrigins, final String allowCredentials) {
        this.allowedOrigins = allowedOrigins;
        this.allowCredentials = allowCredentials;
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String method = request.getMethod();
        logger.info("set cors infor start...........");
        // this origin value could just as easily have come from a database
        response.setHeader("Access-Control-Allow-Origin", allowedOrigins);
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
        // "Content-Type, X-Requested-With, accept, Origin, Access-Control-Request-Method, Access-Control-Request-Headers"
        response.setHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization," + Constants.SESSIONID);
        if ("OPTIONS".equals(method)) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            chain.doFilter(req, res);
        }
        logger.info("set cors infor complete.........");
    }

    @Override
    public void destroy() {
    }
}
