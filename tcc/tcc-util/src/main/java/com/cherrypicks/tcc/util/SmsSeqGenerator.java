package com.cherrypicks.tcc.util;

import java.security.SecureRandom;

public class SmsSeqGenerator {

    public static String getSeqId() {
        final char[] myChars = new char[62];
        for (int i = 0; i < 62; i++) {
            int j = 0;
            if (i < 10) {
                j = i + 48;
            } else if (i > 9 && i <= 35) {
                j = i + 55;
            } else {
                j = i + 61;
            }
            myChars[i] = (char) j;
        }

        String key = "";
        for (int i = 0; i < 11; i++) {
            key += myChars[new SecureRandom().nextInt(62)];
        }

        return key;
    }

    public static void main(final String[] args) {
       System.out.println(getSeqId());
    }
}
