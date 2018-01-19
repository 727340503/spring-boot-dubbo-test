package com.cherrypicks.tcc.util;

public final class IntegerUtil {

    private IntegerUtil() {
    }

    /**
     * <pre>
     * IntegerUtil.isSameValue(null, null)   = true
     * IntegerUtil.isSameValue(null, 1)  = false
     * IntegerUtil.isSameValue(1, null)  = false
     * IntegerUtil.isSameValue(1, 1) = true
     * </pre>
     */
    public static boolean isSameValue(final Integer i1, final Integer i2) {
        if (i1 == null && i2 != null) {
            return false;
        }
        if (i1 != null && i2 == null) {
            return false;
        }
        if (i1 != null && i2 != null && i1.compareTo(i2) != 0) {
            return false;
        }
        return true;
    }
}
