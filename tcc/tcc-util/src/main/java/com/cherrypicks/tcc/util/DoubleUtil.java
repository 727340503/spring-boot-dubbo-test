package com.cherrypicks.tcc.util;

public final class DoubleUtil {

    private final static String DEFAULT_FORMAT = "#.00";

    private DoubleUtil() {
    }

    /**
     * <pre>
     * DoubleUtil.isSameValue(null, null)   = true
     * DoubleUtil.isSameValue(null, 1d)  = false
     * DoubleUtil.isSameValue(1d, null)  = false
     * DoubleUtil.isSameValue(1d, 1d) = true
     * </pre>
     */
    public static boolean isSameValue(final Double d1, final Double d2) {
        if (d1 == null && d2 != null) {
            return false;
        }
        if (d1 != null && d2 == null) {
            return false;
        }
        if (d1 != null && d2 != null && d1.compareTo(d2) != 0) {
            return false;
        }
        return true;
    }

    public static String getFormat(final double doubleValue) {
        return getFormat(doubleValue, DEFAULT_FORMAT);
    }

    public static String getFormat(final double doubleValue, String format) {
        if (format == null || format.equals("")) {
            format = DEFAULT_FORMAT;
        }
        final java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(format);
        final String value = decimalFormat.format(doubleValue);
        if (doubleValue == 0d) {
            return 0 + value;
        }else{
            return value;
        }
    }

    public static void main(final String[] args) {
        System.out.println(getFormat(1d));
    }
}
