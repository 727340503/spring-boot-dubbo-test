package com.cherrypicks.tcc.util.rsa;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

/**
 * 小加解密类，主要是BASE64的加解密
 *
 */
public class Coder {
    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(final String key) throws Exception {
        return Base64.decodeBase64(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(final byte[] key) throws Exception {
        return Base64.encodeBase64String(key);
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
//    public static byte[] encryptMD5(final byte[] data) throws Exception {
//
//        final MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
//        md5.update(data);
//
//        return md5.digest();
//
//    }

    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(final byte[] data) throws Exception {

        final MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }
}
