package com.cherrypicks.tcc.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ReleaseResourceUtil {

    private static Logger logger = LoggerFactory.getLogger(ReleaseResourceUtil.class);

    private ReleaseResourceUtil() {
    }

    public static void closeFileInputStream(final FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static void closeFileOutputStream(final FileOutputStream fos) {
        if (fos != null) {
            try {
                fos.close();
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static void closeBufferedInputStream(final BufferedInputStream bis) {
        if (bis != null) {
            try {
                bis.close();
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static void closeBufferedOutputStream(final BufferedOutputStream bos) {
        if (bos != null) {
            try {
                bos.close();
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static void closeInputStream(final InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static void closeOutputStream(final OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
