package com.cherrypicks.tcc.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

public abstract class Coder {

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f'};

    public static byte[] uploadPath(final String data) throws Exception {
        return Base64.decodeBase64(data.getBytes(Constants.UTF8));
    }

    public static String encryptBASE64(final byte[] data) throws Exception {
        return new String(Base64.encodeBase64(data), Constants.UTF8);
    }

    /**
     * encode string
     *
     * @param algorithm
     * @param str
     * @return String
     */
    public static String encode(final String algorithm, final String str) {
        if (str == null) {
            return null;
        }
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes(Constants.UTF8));
            return getFormattedText(messageDigest.digest());
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * encode By MD5
     *
     * @param str
     * @return String
     */
//    public static String encodeByMD5(final String str) {
//        if (str == null) {
//            return null;
//        }
//        try {
//            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//            messageDigest.update(str.getBytes(Constants.UTF8));
//            return getFormattedText(messageDigest.digest());
//        } catch (final Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * encode By SHA1
     *
     * @param str
     * @return String
     */
    public static String encodeBySHA1(final String str) {
        return encode("SHA1", str);
    }

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes
     *            the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(final byte[] bytes) {
        final int len = bytes.length;
        final StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static void main(final String[] args) {
//        System.out.println("111111 MD5  :" + Coder.encodeByMD5("111111"));
        System.out.println("111111 MD5  :" + Coder.encode("MD5", "111111"));
        System.out.println("111111 SHA1 :" + Coder.encode("SHA1", "111111"));
    }
}
