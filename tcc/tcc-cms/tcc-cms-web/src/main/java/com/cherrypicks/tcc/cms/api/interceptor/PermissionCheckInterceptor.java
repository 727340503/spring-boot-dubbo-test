package com.cherrypicks.tcc.cms.api.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherrypicks.tcc.cms.exception.HaveNoPermissionException;
import com.cherrypicks.tcc.cms.system.model.SystemFunction;
import com.cherrypicks.tcc.cms.system.service.SystemFuncService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.Constants;
import com.cherrypicks.tcc.util.I18nUtil;

public class PermissionCheckInterceptor extends HandlerInterceptorAdapter {

    private final Log logger = LogFactory.getLog(this.getClass());
    
    @Reference(version="1.0")
    private SystemFuncService systemFuncService;
    
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        logger.debug("PermissionCheckInterceptor.preHandle run....");
        
//        final String uri = request.getRequestURI();
        final String uri = request.getServletPath();
        final Long userId = Long.parseLong(request.getParameter(Constants.USERID));
        
        List<SystemFunction> userFuncs = systemFuncService.findUserButtonPermission(userId);
        for(SystemFunction userFunc : userFuncs){
        	if(userFunc.getPageInfo().toLowerCase().indexOf(uri.toLowerCase()) != -1){
        		return true;
        	}
        }
        
        final String lang = request.getParameter(Constants.LANG);
        throw new HaveNoPermissionException(I18nUtil.getMessage(CmsCodeStatus.NO_PERMISSION, null, lang));
    }
    
}
