package com.cherrypicks.tcc.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hmac<br/>
 * algorithm HmacMD5/HmacSHA/HmacSHA256/HmacSHA384/HmacSHA512
 *
 * @author
 */
public final class Hmac {

    private static Logger logger = LoggerFactory.getLogger(Hmac.class);

    private Hmac() {
    }

    /**
     * 转换密钥
     *
     * @param key
     *            二进制密钥
     * @param algorithm
     *            密钥算法
     * @return 密钥
     */
    private static Key toKey(final byte[] key, final String algorithm) {
        // 生成密钥
        return new SecretKeySpec(key, algorithm);
    }

    /**
     * 使用HmacMD5消息摘要算法计算消息摘要
     *
     * @param data
     *            做消息摘要的数据
     * @param key
     *            密钥
     * @return BASE64 encoded string
     */
    public static String encodeHmacMD5(final byte[] data, final Key key) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacMD5");
            mac.init(key);
        } catch (final NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            return null;
        } catch (final InvalidKeyException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return Base64.encodeBase64String(mac.doFinal(data));
    }

    /**
     * 使用HmacMD5消息摘要算法计算消息摘要
     *
     * @param data
     *            做消息摘要的数据
     * @param key
     *            密钥
     * @return BASE64 encoded string
     */
    public static String encodeHmacMD5(final byte[] data, final byte[] key) {
        final Key k = toKey(key, "HmacMD5");
        return encodeHmacMD5(data, k);
    }

    /**
     * 使用HmacSHA消息摘要算法计算消息摘要
     *
     * @param data
     *            做消息摘要的数据
     * @param key
     *            密钥
     * @return BASE64 encoded string
     */
    public static String encodeHmacSHA(final byte[] data, final Key key) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(key);
        } catch (final NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            return null;
        } catch (final InvalidKeyException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return Base64.encodeBase64String(mac.doFinal(data));
    }

    /**
     * 使用HmacSHA消息摘要算法计算消息摘要
     *
     * @param data
     *            做消息摘要的数据
     * @param key
     *            密钥
     * @return BASE64 encoded string
     */
    public static String encodeHmacSHA(final byte[] data, final byte[] key) {
        final Key k = toKey(key, "HmacSHA1");
        return encodeHmacSHA(data, k);
    }

    /**
     * 使用HmacSHA256消息摘要算法计算消息摘要
     *
     * @param data
     *            做消息摘要的数据
     * @param key
     *            密钥
     * @return BASE64 encoded string
     */
    public static String encodeHmacSHA256(final byte[] data, final Key key) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
        } catch (final NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            return null;
        } catch (final InvalidKeyException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return Base64.encodeBase64String(mac.doFinal(data));
    }

    public static byte[] decodeHmacSHA256(final byte[] data, final Key key) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
        } catch (final NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            return new byte[0];
        } catch (final InvalidKeyException e) {
            logger.error(e.getMessage(), e);
            return new byte[0];
        }
        return mac.doFinal(data);
    }

    /**
     * 使用HmacSHA256消息摘要算法计算消息摘要
     *
     * @param data
     *            做消息摘要的数据
     * @param key
     *            密钥
     * @return BASE64 encoded string
     */
    public static String encodeHmacSHA256(final byte[] data, final byte[] key) {
        final Key k = toKey(key, "HmacSHA256");
        return encodeHmacSHA256(data, k);
    }

    /**
     * 使用HmacSHA384消息摘要算法计算消息摘要
     *
     * @param data
     *            做消息摘要的数据
     * @param key
     *            密钥
     * @return BASE64 encoded string
     */
    public static String encodeHmacSHA384(final byte[] data, final Key key) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA384");
            mac.init(key);
        } catch (final NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            return null;
        } catch (final InvalidKeyException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return Base64.encodeBase64String(mac.doFinal(data));
    }

    /**
     * 使用HmacSHA384消息摘要算法计算消息摘要
     *
     * @param data
     *            做消息摘要的数据
     * @param key
     *            密钥
     * @return BASE64 encoded string
     */
    public static String encodeHmacSHA384(final byte[] data, final byte[] key) {
        final Key k = toKey(key, "HmacSHA384");
        return encodeHmacSHA384(data, k);
    }

    /**
     * 使用HmacSHA512消息摘要算法计算消息摘要
     *
     * @param data
     *            做消息摘要的数据
     * @param key
     *            密钥
     * @return BASE64 encoded string
     */
    public static String encodeHmacSHA512(final byte[] data, final Key key) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA512");
            mac.init(key);
        } catch (final NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            return null;
        } catch (final InvalidKeyException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return Base64.encodeBase64String(mac.doFinal(data));
    }

    /**
     * 使用HmacSHA512消息摘要算法计算消息摘要
     *
     * @param data
     *            做消息摘要的数据
     * @param key
     *            密钥
     * @return BASE64 encoded string
     */
    public static String encodeHmacSHA512(final byte[] data, final byte[] key) {
        final Key k = toKey(key, "HmacSHA512");
        return encodeHmacSHA512(data, k);
    }

    public static void main(final String[] args) {
        final String header = "{\"typ\": \"JWT\", \"alg\": \"HS256\"}";
        final String payload = "{\"userId\": 29}";
        final String encodeHeader = Base64.encodeBase64String(StringUtils.getBytesUtf8(header));
        System.out.println(encodeHeader);
        String encodePayload = Base64.encodeBase64String(StringUtils.getBytesUtf8(payload));
        encodePayload = encodePayload.substring(0, encodePayload.lastIndexOf("="));
        System.out.println(encodePayload);
        final String key = "MySecretKey";

        String encodedStr = encodeHmacSHA256(StringUtils.getBytesUtf8(encodeHeader + "." + encodePayload),
                StringUtils.getBytesUtf8(key));
        encodedStr = encodedStr.substring(0, encodedStr.lastIndexOf("="));
        System.out.println(encodedStr);

        // eyJ0eXAiOiAiSldUIiwgImFsZyI6ICJIUzI1NiJ9\.eyJ1c2VySWQiOiAyOX0
        String test = encodeHmacSHA256(
                StringUtils.getBytesUtf8("eyJ0eXAiOiAiSldUIiwgImFsZyI6ICJIUzI1NiJ9.eyJ1c2VySWQiOiAyOX0"),
                StringUtils.getBytesUtf8(key));
        test = test.substring(0, test.lastIndexOf("="));
        System.out.println(test);

        final String str = "eyJ0eXAiOiAiSldUIiwgImFsZyI6ICJIUzI1NiJ9.eyJ1c2VySWQiOiAyOX0.UZQdNcxNacIdS+BtvYozewtlDwkOx/43GADBbwtZSoc";
        final String[] strs = str.split("\\.");
        System.out.println(strs.length);
    }
}
