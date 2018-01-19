package com.cherrypicks.tcc.util;

public final class VersionUtil {

    private VersionUtil() {
    }

    /**
     * 比较2个版本号
     * @param v1
     * @param v2
     * @param complete 是否完整的比较两个版本
     *
     * @return (v1 < v2) ? -1 : ((v1 == v2) ? 0 : 1)
     */
    public static int compare(final String v1, final String v2, final boolean complete) {
        if (v1.equals(v2)) {
            return 0;
        }
        final String[] v1s = v1.split("\\.");
        final String[] v2s = v2.split("\\.");
        final int len = complete
                ? Math.max(v1s.length, v2s.length)
                : Math.min(v1s.length, v2s.length);

        for (int i = 0; i < len; i++) {
            final String c1 = null == v1s[i] ? "" : v1s[i];
            final String c2 = null == v2s[i] ? "" : v2s[i];

            final int result = c1.compareTo(c2);
            if (result != 0) {
                return result;
            }
        }

        return 0;
    }
}
