package com.cherrypicks.tcc.util;

import java.security.MessageDigest;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EncryptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);

    private static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e",
            "f"};

    private EncryptionUtil() {
    }

    public static String getSHA256(final String value) {
        String result = null;

        if (value == null) {
            return null;
        }

        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            final byte[] results = md.digest(value.getBytes(Constants.UTF8));
            final String resultString = byteArrayToHexString(results);
            result = resultString.toUpperCase(Locale.ENGLISH);
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return result;

    }

    private static String byteArrayToHexString(final byte[] b) {
        final StringBuffer resultSb = new StringBuffer();

        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(final byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        final int d1 = n / 16;
        final int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String getMD5(final String value) {
        String result = null;

        if (value == null) {
            return null;
        }

        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] results = md.digest(value.getBytes(Constants.UTF8));
            final String resultString = byteArrayToHexString(results);
            result = resultString.toUpperCase(Locale.ENGLISH);
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return result;
    }


    public static void main(final String[] args) {
        System.out.println(getSHA256("123456HlgDm"));

        System.out.println(getMD5("test1234567"));
        System.out.println(getMD5("tccadmind846664c5f854238afc1edc289561650"));
    }
}
