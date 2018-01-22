package com.cherrypicks.tcc.cms.api.util;

import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求公用类
 *
 * @author edwin.zhou
 */
public final class RequestUtil {

    protected static Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    private static final java.util.regex.Pattern IS_LICIT_IP_PATTERN = java.util.regex.Pattern
            .compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");

    private RequestUtil() {
    }

    public static int getPageid(final HttpServletRequest request, final String name) {
        final int pageid = NumberUtils.toInt(request.getParameter(name));
        if (pageid <= 0) {
            return 1;
        }
        return pageid;
    }

    public static String getUrl(final HttpServletRequest request) {
        final StringBuffer sb = request.getRequestURL();
        final String queryString = request.getQueryString();
        if (StringUtils.isNotEmpty(queryString)) {
            sb.append('?').append(queryString);
        }
        return sb.toString();
    }

    public static String getReferer(final HttpServletRequest request) {
        return request.getHeader("referer");
    }

    public static String getRequestUri(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    public static boolean isLicitIp(final String ip) {
        if (StringUtils.isEmpty(ip)) {
            return false;
        }

        final Matcher m = IS_LICIT_IP_PATTERN.matcher(ip);
        if (!m.find()) {
            return false;
        }
        return true;
    }

    public static boolean isSsl(final HttpServletRequest request) {
        final boolean isSsl = "true".equalsIgnoreCase(request.getHeader("ssl"));
        return isSsl;
    }

    public static String getProtocol(final HttpServletRequest request) {
        final boolean isSsl = RequestUtil.isSsl(request);
        String protocol;
        if (isSsl) {
            protocol = "https";
        } else {
            protocol = "http";
        }
        return protocol;
    }

    public static String getRedirect(final HttpServletRequest request, String url) {
        if (StringUtils.isEmpty(url)) {
            logger.error("url parameters can not be null.");
            throw new NullPointerException("url parameters can not be null.");
        }
        if (url.startsWith("http")) {
            return url;
        }
        final boolean isSsl = RequestUtil.isSsl(request);
        if (!isSsl) {
            return url;
        }
        if (!url.startsWith("/")) {
            final String path = request.getServletPath();
            final int index = path.lastIndexOf('/');
            if (index != -1) {
                url = path.substring(0, index + 1) + url;
            }
        }
        final String serverName = request.getServerName();

        return "https://" + serverName + url;
    }

//    public static void printHeaders(final HttpServletRequest request) {
//        final Enumeration<String> e = request.getHeaderNames();
//        while (e.hasMoreElements()) {
//            final String name = e.nextElement();
//            final String value = request.getHeader(name);
//            logger.info(name + ":" + value);
//        }
//    }

    /**
     * 获取真实客户端IP
     *
     * @param request
     * @return
     */
    public static String getRealIp(final HttpServletRequest request) {
        // String ip = request.getHeader("X-Forwarded-For");
        // if (StringUtils.isEmpty(ip)) {
        // return request.getRemoteAddr();
        // }
        // int index = ip.lastIndexOf(',');
        // String lastip = ip.substring(index + 1).trim();
        //
        // if ("127.0.0.1".equals(lastip) || !isLicitIp(lastip)) {
        // return request.getRemoteAddr();
        // }
        // return lastip;

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        int index = ip.lastIndexOf(',');
        if (index <= 0) {
            index = ip.length();
        }
        final String lastip = ip.substring(0, index).trim();

        if ("127.0.0.1".equals(lastip) || !isLicitIp(lastip)) {
            return request.getRemoteAddr();
        }

        return lastip;
    }

    public static String getIp(final HttpServletRequest request) {
        final String remoteAddr = request.getRemoteAddr();
        final String forwarded = request.getHeader("X-Forwarded-For");
        final String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded;
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                ip = realIp + "/" + forwarded.replaceAll(", " + realIp, "");
            }
        }
        return ip;
    }

    public static String getIp2(final HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            final int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 从Request对象中获得客户端IP，处理了HTTP代理服务器和Nginx的反向代理截取了ip
     * @param request
     * @return ip
     */
   public static String getLocalIp(final HttpServletRequest request) {
       final String remoteAddr = request.getRemoteAddr();
       String forwarded = request.getHeader("X-Forwarded-For");
       final String realIp = request.getHeader("X-Real-IP");

       String ip = null;
       if (realIp == null) {
           if (forwarded == null) {
               ip = remoteAddr;
           } else {
               ip = remoteAddr + "/" + forwarded.split(",")[0];
           }
       } else {
           if (realIp.equals(forwarded)) {
               ip = realIp;
           } else {
               if(forwarded != null){
                   forwarded = forwarded.split(",")[0];
               }
               ip = realIp + "/" + forwarded;
           }
       }
       return ip;
   }

//    public static String getRealUrl(final HttpServletRequest request) {
//        final String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
//                + request.getRequestURI() + "?" + request.getQueryString();
//        logger.info("Request Real URL:" + StringEscapeUtils.escapeHtml(url));
//        logger.info("Request Refer Header:" + request.getHeader("referer"));
//        return url;
//    }
}
