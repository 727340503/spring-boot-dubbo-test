package com.cherrypicks.tcc.util.rsa;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/** *//**
* RSA安全编码组件
*
* @version 1.0
* @since 1.0
*/
public class RSACoder  extends Coder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;

    /** *//**
     * 用私钥对信息生成数字签名
     *
     * @param data
     *            加密数据
     * @param privateKey
     *            私钥
     *
     * @return
     * @throws Exception
     */
    public static String sign(final byte[] data, final String privateKey) throws Exception {
        // 解密由base64编码的私钥
        final byte[] keyBytes = decryptBASE64(privateKey);

        // 构造PKCS8EncodedKeySpec对象
        final PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥匙对象
        final PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        final Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return encryptBASE64(signature.sign());
    }

    /** *//**
     * 校验数字签名
     *
     * @param data
     *            加密数据
     * @param publicKey
     *            公钥
     * @param sign
     *            数字签名
     *
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     *
     */
    public static boolean verify(final byte[] data, final String publicKey, final String sign)
            throws Exception {

        // 解密由base64编码的公钥
        final byte[] keyBytes = decryptBASE64(publicKey);

        // 构造X509EncodedKeySpec对象
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象
        final PublicKey pubKey = keyFactory.generatePublic(keySpec);

        final Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    /** *//**
     * 解密<br>
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(final byte[] data, final String key)
            throws Exception {
        // 对密钥解密
        final byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        final PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        final Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    public static String decryptByPrivateKeyLonger(final String data, final PrivateKey privateKey) throws Exception {
        final byte[] dataBytes = decryptBASE64(data);

        // 对数据解密
        final Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

//        return new String(cipher.doFinal(dataBytes));

        final int inputLen = dataBytes.length;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        final byte[] decryptedData = out.toByteArray();
        out.close();

        return new String(decryptedData);
    }

    public static String decryptByPrivateKey(final String data, final PrivateKey privateKey) throws Exception {
        final byte[] dataBytes = decryptBASE64(data);

        final Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
        final byte[] decbytes = rsaCipher.doFinal(dataBytes);
        return new String(decbytes);
    }

    /** *//**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(final byte[] data, final String key)
            throws Exception {
        // 对密钥解密
        final byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        final X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        final Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    public static String decryptByPublicKey(final String data, final PublicKey publicKey) throws Exception {
        final byte[] dataBytes = decryptBASE64(data);

        // 对数据解密
        final Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return new String(cipher.doFinal(dataBytes));
    }

    /** *//**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(final byte[] data, final String key)
            throws Exception {
        // 对公钥解密
        final byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        final X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        final Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    public static String encryptByPublicKeyLonger(final String data, final PublicKey publicKey) throws Exception {
        // 对数据加密
        final Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

//        final byte[] encryptedData = cipher.doFinal(data.getBytes());

        final int inputLen = data.getBytes().length;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        final byte[] encryptedData = out.toByteArray();
        out.close();

        return encryptBASE64(encryptedData);
    }

    public static String encryptByPublicKey(final String data, final PublicKey publicKey) throws Exception {
        // 对数据加密
        final Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        final byte[] encbytes = rsaCipher.doFinal(data.getBytes());
        return encryptBASE64(encbytes);
    }

    /** *//**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(final byte[] data, final String key)
            throws Exception {
        // 对密钥解密
        final byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        final PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        final Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    public static String encryptByPrivateKey(final String data, final PrivateKey privateKey) throws Exception {
        // 对数据加密
        final Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        final byte[] encryptedData = cipher.doFinal(data.getBytes());

        return encryptBASE64(encryptedData);
    }

    /** *//**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(final Map<String, Object> keyMap)
            throws Exception {
        final PrivateKey key = (PrivateKey) keyMap.get(PRIVATE_KEY);

        // return encryptBASE64(key.getEncoded());
        return key;
    }

    public static PrivateKey getPrivateKey(final String privateKeyStr) throws Exception {
        // 对密钥解密
        final byte[] keyBytes = decryptBASE64(privateKeyStr);

        // 取得私钥
        final PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        final PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        return privateKey;
    }

    /** *//**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(final Map<String, Object> keyMap)
            throws Exception {
        final PublicKey key = (PublicKey) keyMap.get(PUBLIC_KEY);

        // return encryptBASE64(key.getEncoded());
        return key;
    }

    public static PublicKey getPublicKey(final String publicKeyStr) throws Exception {
        // 对公钥解密
        final byte[] keyBytes = decryptBASE64(publicKeyStr);

        // 取得公钥
        final X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        final PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

        return publicKey;
    }

    /** *//**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        final KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(2048);

        final KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        final Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
}