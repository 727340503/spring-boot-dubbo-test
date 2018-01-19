package com.cherrypicks.tcc.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class URLAnalysis {
    private final Map<String, String> paramMap = new HashMap<String, String>();

    public void analysis(String url) {
        paramMap.clear();
        if (!"".equals(url)) {// 如果URL不是空字符串
            if (url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("https")) {
                url = url.substring(url.indexOf('?') + 1);
            }
            final String[] paramaters = url.split("&");
            for (final String param : paramaters) {
                final String[] values = param.split("=");
                if (values.length == 2) {
                    paramMap.put(values[0], values[1]);
                } else {
                    paramMap.put(values[0], "");
                }
            }
        }
    }

    public String getParam(final String name) {
        try {
            return URLDecoder.decode(paramMap.get(name), Constants.UTF8);
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(final String[] args) {
        final String test = "username=???&timestamp=20140825134419&loginid=46707&bankcode=012&deptcode=H90&divcode=H33&userroles=Operator";
        final URLAnalysis urlAnalysis = new URLAnalysis();
        urlAnalysis.analysis(test);
        System.out.println("loginid = " + urlAnalysis.getParam("loginid"));
        System.out.println("username = " + urlAnalysis.getParam("username"));
        System.out.println("userroles = " + urlAnalysis.getParam("userroles"));
    }
}
