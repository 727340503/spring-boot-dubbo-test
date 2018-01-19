package com.cherrypicks.tcc.util;

public class ImageUtil {


	/**
	 *
	 * @param path downPath
	 * @param module @see ImageModule
	 * @param image
	 * @return
	 */
    public static String getImageFullPath(final String path, final String image) {

        if (image == null || image.length() == 0) {
            return "";
        } else {
            if (image.startsWith("http://") || image.startsWith("https://") || image.startsWith("ftp://")
                    || image.startsWith("ftps://")) {
                return image;
            } else {
                return (path == null ? "" : path) + "/" + image;
            }
        }
    }
}
