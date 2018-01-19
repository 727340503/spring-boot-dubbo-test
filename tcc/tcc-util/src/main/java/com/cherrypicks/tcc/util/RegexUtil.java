package com.cherrypicks.tcc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public final class RegexUtil {

    private RegexUtil(){
    }

    public static boolean match(final String regex, final String str) {
        if(StringUtils.isEmpty(regex) || StringUtils.isEmpty(str)) {
            return Boolean.FALSE;
        }

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

//    public static void main(final String[] args) {
//        final String HK_ID_REGEX = "^[a-zA-Z]{1,2}\\d{6}\\([0-9a-zAZ-Z]\\)$";
//        final String HK_ID = "P958270(2)";
//
//        System.out.println(RegexUtil.match(HK_ID_REGEX, HK_ID));
//    }
}
