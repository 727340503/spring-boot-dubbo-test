package com.cherrypicks.tcc.cms.api.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.cherrypicks.tcc.cms.system.exception.InvalidArgumentException;


/**
 * 参数验证
 */
public final class AssertUtil {

    private static final int MIN_TINY_INT = -128;

    private static final int MAX_TINY_INT = 127;

    private AssertUtil() {
    }

    public static void notNull(final Object obj, final String message) {
        assertTrue(obj != null, message);
    }

    public static <E> void notEmpty(final List<E> list, final String message) {
        assertTrue(list != null && !list.isEmpty(), message);
    }

    public static void notBlank(final String str, final String message) {
        assertTrue(StringUtils.isNotBlank(str), message);
    }

    public static void minLength(final String str, final int minLen, final String message) {
        assertTrue(str == null || str.length() >= minLen, message);
    }

    public static void maxLength(final String str, final int maxLen, final String message) {
        assertTrue(str == null || str.length() <= maxLen, message);
    }

    public static void decimalRange(final double minValue, final double maxValue, final double data, final String message) {
        assertTrue(data >= minValue && data <= maxValue, message);
    }

    public static void tinyIntRange(final int data, final String message){
        assertTrue(data >= MIN_TINY_INT && data <= MAX_TINY_INT, message);
    }

    public static void isNumeric(final String str, final String message) {
        assertTrue(NumberUtils.isDigits(str), message);
    }
    
    public static void isValidEmailAddress(final String email, final String message) {
        assertTrue(isValidEmailAddress(email), message);
    }
    
    public static void isLetterOrDigit(final String str, final String message) {
        assertTrue(isLetterDigit(str), message);
    }
    
    public static void isLetterAndDigit(final String str, final String message) {
    	assertTrue(isLetterAndDigit(str), message);
    }
    
    //check email format
    private static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    //check string just include letter or digit
	private static boolean isLetterDigit(String str) {
		String regex = "^[a-z0-9A-Z]+$";
		return str.matches(regex);
	}
    //check string just indule letter and digit
	private static boolean isLetterAndDigit(String str){
		String regex = "^(\\d+[A-Za-z]+[A-Za-z0-9]*)|([A-Za-z]+\\d+[A-Za-z0-9]*)$";
		return str.matches(regex);
	}
	
    public static void assertTrue(final boolean flag, final String message) {
        if (!flag) {
            throw new InvalidArgumentException(message);
        }
    }
    
}
