package com.cherrypicks.tcc.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
    public static long divide(final double v1, final double v2, final int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        final BigDecimal b1 = new BigDecimal(Double.toString(v1));
        final BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).longValue();
    }
}
