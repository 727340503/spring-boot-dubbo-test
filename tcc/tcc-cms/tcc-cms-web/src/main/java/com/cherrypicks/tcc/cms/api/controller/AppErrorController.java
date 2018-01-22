package com.cherrypicks.tcc.cms.api.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cherrypicks.tcc.cms.api.service.MessageService;
import com.cherrypicks.tcc.cms.enums.Lang;
import com.cherrypicks.tcc.cms.exception.BaseException;
import com.cherrypicks.tcc.cms.exception.RecordVersionException;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.Constants;
import com.cherrypicks.tcc.util.JsonUtil;

@Controller
public class AppErrorController implements ErrorController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static AppErrorController appErrorController;

	@Autowired
	private MessageService messageService;

	/**
	 * Error Attributes in the Application
	 */
	@Autowired
	private ErrorAttributes errorAttributes;

	private final static String ERROR_PATH = "/error";
	private final static String PAGE_NOT_FOUND_PATH = "/pageNotFound";

	/**
	 * Controller for the Error Controller
	 *
	 * @param errorAttributes
	 * @return
	 */

	public AppErrorController(final ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	public AppErrorController() {
		if (appErrorController == null) {
			appErrorController = new AppErrorController(errorAttributes);
		}
	}

	@RequestMapping(value = PAGE_NOT_FOUND_PATH, produces = Constants.CONTENT_TYPE_HTML)
	public ModelAndView pageNotFoundHtml(final HttpServletRequest request, final HttpServletResponse response) {
		final Map<String, Object> model = getErrorAttributes(request, getTraceParameter(request));
		// return error JSON page
		final String json = JsonUtil.toJson(model);
		model.put(Constants.MESSAGE_JSON, json);

		// return velocity templates json.vm
		response.setContentType(Constants.CONTENT_TYPE_JSON);
		return new ModelAndView("json", model);
	}

	/**
	 * Supports the HTML Error View
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ERROR_PATH, produces = Constants.CONTENT_TYPE_HTML)
	public ModelAndView errorHtml(final HttpServletRequest request, final HttpServletResponse response) {
		final Map<String, Object> model = getErrorAttributes(request, getTraceParameter(request));
		// return error JSON page
		final String json = JsonUtil.toJson(model);
		model.put(Constants.MESSAGE_JSON, json);

		// return velocity templates json.vm
		response.setContentType(Constants.CONTENT_TYPE_JSON);
		return new ModelAndView("json", model);
	}

	/**
	 * Supports other formats like JSON, XML
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { ERROR_PATH, PAGE_NOT_FOUND_PATH }, produces = Constants.CONTENT_TYPE_JSON)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> error(final HttpServletRequest request,
			final HttpServletResponse response) {
		final Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
		final HttpStatus status = getStatus(request);
		return new ResponseEntity<Map<String, Object>>(body, status);
	}

	/**
	 * Returns the path of the error page.
	 *
	 * @return the error path
	 */
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	private boolean getTraceParameter(final HttpServletRequest request) {
		final String parameter = request.getParameter("trace");
		if (parameter == null) {
			return false;
		}
		return !"false".equals(parameter.toLowerCase());
	}

	private Map<String, Object> getErrorAttributes(final HttpServletRequest request, final boolean includeStackTrace) {
		final RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		final Map<String, Object> map = this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
		final Exception ex = (Exception) errorAttributes.getError(requestAttributes);

		if (ex != null) {

			// is a custom exception ?
			if (ex instanceof BaseException) {

				final BaseException baseEx = (BaseException) ex;
				
				logger.error(baseEx.getErrorCode() + ":" + ex.getMessage(), ex);
				
				final int status = baseEx.getErrorCode();
				String message = ex.getMessage();
				if (StringUtils.isBlank(message)) {
					final String lang = request.getParameter(Constants.LANG);
					if (StringUtils.isNotBlank(lang)) {
						message = messageService.getMessage(status, baseEx.getArgs(), lang);
					} else {
						message = messageService.getMessage(status, baseEx.getArgs(), Lang.getDefaultLang().toLocale());
					}
				}
				if (baseEx.getData() != null) {
					map.put(Constants.DATA, baseEx.getData());
				}

				// custom errorCode
				map.put(Constants.ERROR_CODE, status);
				// custom errorMessage
				if (StringUtils.isNotEmpty(message)) {
					map.put(Constants.MESSAGE, message);
				}
			} else if (ex instanceof RecordVersionException) {
				
				logger.error(CmsCodeStatus.RECORD_VERSION_EXCEPTION + ":" + ex.getMessage(), ex);
				
				final int status = CmsCodeStatus.RECORD_VERSION_EXCEPTION;
				String message = ex.getMessage();
				if (StringUtils.isBlank(message)) {
					final String lang = request.getParameter(Constants.LANG);
					if (StringUtils.isNotBlank(lang)) {
						message = messageService.getMessage(status, null, lang);
					} else {
						message = messageService.getMessage(status, null, Lang.getDefaultLang().toLocale());
					}
				}
				// custom errorCode
				map.put(Constants.ERROR_CODE, status);
				// custom errorMessage
				if (StringUtils.isNotEmpty(message)) {
					map.put(Constants.MESSAGE, message);
				}
			}
//			else if (ex instanceof MySQLIntegrityConstraintViolationException) {
//				final int status = CmsCodeStatus.RECORD_IS_REFERENCED;
//				String message = ex.getMessage();
//				if (StringUtils.isBlank(message)) {
//					final String lang = request.getParameter(Constants.LANG);
//					if (StringUtils.isNotBlank(lang)) {
//						message = messageService.getMessage(status, null, lang);
//					} else {
//						message = messageService.getMessage(status, null, Lang.getDefaultLang().toLocale());
//					}
//				}
//				// custom errorCode
//				map.put(Constants.ERROR_CODE, status);
//				// custom errorMessage
//				if (StringUtils.isNotEmpty(message)) {
//					map.put(Constants.MESSAGE, message);
//				}
//			} 
			else {
				
				logger.error(ex.getMessage(), ex);
				
				final int status = CmsCodeStatus.FAILED;
				String message = ex.getMessage();
				if (StringUtils.isBlank(message)) {
					final String lang = request.getParameter(Constants.LANG);
					if (StringUtils.isNotBlank(lang)) {
						message = messageService.getMessage(status, null, lang);
					} else {
						message = messageService.getMessage(status, null, Lang.getDefaultLang().toLocale());
					}
				}

				// custom errorCode
				map.put(Constants.ERROR_CODE, status);
				// custom errorMessage
				if (StringUtils.isNotEmpty(message)) {
					map.put(Constants.MESSAGE, message);
				}
			}
		}

		logger.debug("AppErrorController.method [error info]: errorCode-" + map.get(Constants.ERROR_CODE)
				+ ", request url-" + map.get("path"));
		return map;
	}

	private HttpStatus getStatus(final HttpServletRequest request) {
		final Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode != null) {
			try {
				return HttpStatus.valueOf(statusCode);
			} catch (final Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

}
