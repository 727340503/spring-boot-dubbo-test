package com.cherrypicks.tcc.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;

public final class EncryptionCBC {

    private EncryptionCBC() {
    }

    public static String encrypt(final String data, final String key, final String iv) throws Exception {

        final Key keyspec = new SecretKeySpec(Hex.decodeHex(key.toCharArray()), "AES");
        final IvParameterSpec ivspec = new IvParameterSpec(Hex.decodeHex(iv.toCharArray()));

        return encrypt(data.getBytes(), keyspec, ivspec);
    }

    public static String encrypt(final byte[] data, final Key key, final IvParameterSpec ivspec) throws Exception {
        final Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        final int blockSize = cipher.getBlockSize();

        int plaintextLength = data.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }

        final byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(data, 0, plaintext, 0, data.length);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
        final byte[] encrypted = cipher.doFinal(plaintext);

        return Hex.encodeHexString(encrypted).toUpperCase(Locale.ENGLISH);
    }

    public static String decrypt(final String data, final String key, final String iv) throws Exception {
        final Key keyspec = new SecretKeySpec(Hex.decodeHex(key.toCharArray()), "AES");
        final IvParameterSpec ivspec = new IvParameterSpec(Hex.decodeHex(iv.toCharArray()));

        return decrypt(Hex.decodeHex(data.toCharArray()), keyspec, ivspec);
    }

    public static String decrypt(final byte[] data, final Key key, final IvParameterSpec ivspec) throws Exception {
        final Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        final int blockSize = cipher.getBlockSize();

        int plaintextLength = data.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }

        final byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(data, 0, plaintext, 0, data.length);

        cipher.init(Cipher.DECRYPT_MODE, key, ivspec);

        final byte[] original = cipher.doFinal(plaintext);

        return new String(original).trim();
    }

    public static void encryptFile(final String destFilePath, final InputStream is, final String key, final String iv) throws Exception {
        final byte[] keyByte = Hex.decodeHex(key.toCharArray());
        final byte[] ivByte = Hex.decodeHex(iv.toCharArray());
        final Key keyspec = new SecretKeySpec(keyByte, "AES");
        final IvParameterSpec ivspec = new IvParameterSpec(ivByte);

        encryptFile(destFilePath, is, keyspec, ivspec);
    }

    public static void encryptFile(final String destFilePath, final InputStream is, final Key key, final IvParameterSpec ivspec) throws Exception {
        final BufferedImage input = ImageIO.read(is);
        final Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);

        FileOutputStream output = null;
        CipherOutputStream cos = null;
        try {
            // output = new FileOutputStream(new File(destFilePath));
            final String finalPath = FileUtil.getSecureFile(destFilePath);
            FileUtil.createFolder(finalPath);
            output = new FileOutputStream(finalPath);
            cos = new CipherOutputStream(output, cipher);

            ImageIO.write(input, FileUtil.getSuffixName(destFilePath), cos);
        } catch (final IOException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(cos);
        }
    }

    public static void decryptFile(final OutputStream os, final File sourceFile, final String key, final String iv) throws Exception {
        final byte[] keyByte = Hex.decodeHex(key.toCharArray());
        final byte[] ivByte = Hex.decodeHex(iv.toCharArray());
        final Key keyspec = new SecretKeySpec(keyByte, "AES");
        final IvParameterSpec ivspec = new IvParameterSpec(ivByte);

        decryptFile(os, sourceFile, keyspec, ivspec);
    }

    public static void decryptFile(final OutputStream os, final File sourceFile, final Key key, final IvParameterSpec ivspec) throws Exception {
        final Cipher pbeCipher = Cipher.getInstance("AES/CBC/NoPadding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, ivspec);

        FileInputStream fileIn = null;
        CipherOutputStream cos = null;
        try {
            fileIn = new FileInputStream(sourceFile);
            cos = new CipherOutputStream(os, pbeCipher);
            final byte[] cache = new byte[1024];
            int nRead = 0;
            while ((nRead = fileIn.read(cache)) != -1) {
                cos.write(cache, 0, nRead);
                cos.flush();
            }

        } catch (final IOException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(cos);
            IOUtils.closeQuietly(fileIn);
        }
    }

    public static void decryptFile(final OutputStream os, final InputStream inputStream, final String key, final String iv)
            throws Exception {
        final byte[] keyByte = Hex.decodeHex(key.toCharArray());
        final byte[] ivByte = Hex.decodeHex(iv.toCharArray());
        final SecretKeySpec keyspec = new SecretKeySpec(keyByte, "AES");
        final IvParameterSpec ivspec = new IvParameterSpec(ivByte);

        final Cipher pbeCipher = Cipher.getInstance("AES/CBC/NoPadding");
        pbeCipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

        CipherOutputStream cos = null;
        try {
            cos = new CipherOutputStream(os, pbeCipher);
            final byte[] cache = new byte[1024];
            int nRead = 0;
            while ((nRead = inputStream.read(cache)) != -1) {
                cos.write(cache, 0, nRead);
                cos.flush();
            }

        } catch (final IOException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(cos);
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static void main(final String[] args) throws Exception {

       /* final Key cherrypicksKeyspec = new SecretKeySpec(Hex.decodeHex("767464537A496C6D6955535956716F6D".toCharArray()), Constants.ALGORITHM_AES);
        final IvParameterSpec cherrypicksIvspec = new IvParameterSpec(Hex.decodeHex("30303030303030303030303030303030".toCharArray()));

        final String cherrypicksDataEncryptionKey = "5ECF408A55169A868576AEB9AFA7AD23CB9976D0BC809CDB0A5270CB14E22CDC";
        final String cherrypicksDataEncryptionIv = "A2E016AD7A8001723CDA05F2038387D4132BCB1B8EDA8F47CA069ABF92EFD4C7";

        // init cherrypicks data encryption key
        final String cherrypicksDataEncryptionKeyDecrypted = EncryptionCBC.decrypt(Hex.decodeHex(cherrypicksDataEncryptionKey.toCharArray()),
                cherrypicksKeyspec, cherrypicksIvspec);

        final Key cherrypicksDataEncryptionKeyspec = new SecretKeySpec(Hex.decodeHex(cherrypicksDataEncryptionKeyDecrypted.toCharArray()),
                Constants.ALGORITHM_AES);

        final String cherrypicksDataEncryptionIvDecrypted = EncryptionCBC.decrypt(Hex.decodeHex(cherrypicksDataEncryptionIv.toCharArray()),
                cherrypicksKeyspec, cherrypicksIvspec);
        final IvParameterSpec cherrypicksDataEncryptionIvspec = new IvParameterSpec(Hex.decodeHex(cherrypicksDataEncryptionIvDecrypted.toCharArray()));*/
    }
}
