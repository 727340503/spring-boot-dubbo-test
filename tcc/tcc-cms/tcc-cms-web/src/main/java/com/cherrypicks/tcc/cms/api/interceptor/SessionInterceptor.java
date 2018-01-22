package com.cherrypicks.tcc.cms.api.interceptor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherrypicks.tcc.cms.enums.Lang;
import com.cherrypicks.tcc.cms.system.exception.InvalidArgumentException;
import com.cherrypicks.tcc.cms.system.exception.InvalidUserSessionException;
import com.cherrypicks.tcc.cms.system.service.IAuthorizeService;
import com.cherrypicks.tcc.cms.system.vo.AuthenticatedUserDetails;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.Constants;
import com.cherrypicks.tcc.util.I18nUtil;

public class SessionInterceptor extends HandlerInterceptorAdapter {

    private final Log logger = LogFactory.getLog(this.getClass());
    
    private static ThreadLocal<AuthenticatedUserDetails> threadLocal = new ThreadLocal<AuthenticatedUserDetails>();

    @Reference(version="1.0")
    private IAuthorizeService authorizeService;
    

    @PostConstruct
    public void init() {
        logger.info("init");
    }

    @PreDestroy
    public void destroy() {
        logger.info("destroy");
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        logger.debug("SessionInterceptor.preHandle run....");

        final String userId = request.getParameter(Constants.USERID);
        final String session = request.getParameter(Constants.SESSIONID);
        final String lang = request.getParameter(Constants.LANG);
        if(!StringUtils.isNotEmpty(lang)){
        	throw new InvalidArgumentException("Lang "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, Lang.getDefaultLang().toValue()));
        }
        
        if (!StringUtils.isNotEmpty(userId) || !NumberUtils.isNumber(userId)) {
            throw new InvalidArgumentException("User Id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, Lang.getDefaultLang().toValue()));
        }

//        if (!NumberUtils.isNumber(userId)) {
//            throw new InvalidArgumentException("User Id"+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, Lang.getDefaultLang().toValue()));
//        }
        if (!StringUtils.isNotEmpty(session)) {
            throw new InvalidArgumentException("Session "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, Lang.getDefaultLang().toValue()));
        }

        if (!authorizeService.checkUserSession(Long.parseLong(userId), session,lang)) {
            throw new InvalidUserSessionException(I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_SESSION, null, Lang.getDefaultLang().toValue()));
        }
        
        final AuthenticatedUserDetails userDetail = new AuthenticatedUserDetails();
        userDetail.setSystemUserId(Long.valueOf(userId));
        userDetail.setSession(session);
        threadLocal.set(userDetail);

        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
    	 logger.debug("SessionInterceptor.afterCompletion run....");

         final AuthenticatedUserDetails userDetail = threadLocal.get();
         
         if (null != userDetail) {
             // renewal session
        	 authorizeService.renewalSessoin(userDetail.getSystemUserId(), userDetail.getSession());
         }
         
         threadLocal.remove();
    }
}
