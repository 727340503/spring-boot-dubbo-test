package com.cherrypicks.tcc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public final class FileUtil {


    private FileUtil() {
    }

    public static String randomFileName(final String originalFilename) {
        return new StringBuilder(UUID.randomUUID().toString().replace("-", "")).append(getSuffix(originalFilename)).toString();
    }

    public static String getSuffixName(final String originalFilename) {
        if (StringUtils.isEmpty(originalFilename)) {
            return "";
        }

        final int start = originalFilename.lastIndexOf(".");

        if (start == -1) {
            return "";
        }

        final String suffix = originalFilename.substring(start + 1, originalFilename.length());

        if (StringUtils.isEmpty(suffix)) {
            return "";
        }

        return suffix;
    }

    public static String getSuffix(final String originalFilename) {
        if (StringUtils.isEmpty(originalFilename)) {
            return "";
        }

        final int start = originalFilename.lastIndexOf(".");

        if (start == -1) {
            return "";
        }

        final String suffix = originalFilename.substring(start, originalFilename.length());

        if (StringUtils.isEmpty(suffix)) {
            return "";
        }

        return suffix;
    }

    public static String uploadFile(final InputStream uploadFileInputStream, final String destFilePath) throws IOException {
        FileUtils.copyInputStreamToFile(uploadFileInputStream, new File(getSecureFile(destFilePath)));
        return destFilePath;
    }

    public static String uploadFile(final InputStream uploadFileInputStream, final String originalFilename,  final String uploadPath) throws IOException {
        final StringBuilder imageName = new StringBuilder();
        imageName.append("/");
        imageName.append(randomFileName(originalFilename));

        final StringBuilder destFilePath = new StringBuilder(uploadPath);
        destFilePath.append("/");
        destFilePath.append(imageName);

        FileUtil.uploadFile(uploadFileInputStream, destFilePath.toString());
        return imageName.toString();
    }

    public static String uploadFile(final InputStream uploadFileInputStream, final String originalFilename, final String ImageModule, final String uploadPath) throws IOException {
        final StringBuilder imageName = new StringBuilder();
        imageName.append(ImageModule);
        imageName.append("/");
        imageName.append(randomFileName(originalFilename));

        final StringBuilder destFilePath = new StringBuilder(uploadPath);
        destFilePath.append("/");
        destFilePath.append(imageName);

        FileUtil.uploadFile(uploadFileInputStream, destFilePath.toString());
        return imageName.toString();
    }

    public static void deleteFile(final String filePath) throws IOException {
    }

    public static String format(final String originalFilename) {
        if (StringUtils.isEmpty(originalFilename)) {
            return "";
        }

        final int start = originalFilename.lastIndexOf(".");

        if (start == -1) {
            return "";
        }

        final String fileName = originalFilename.substring(0, start);

        return fileName.replace(" ", "_").replace(".", "_");
    }

    public static String getFileContentType(final String suffix) {
        String contentType = "image/jpeg";
        if (suffix.equals("jpg") || suffix.equals("jpeg")) {
            contentType = "image/jpeg";
        } else if (suffix.equals("png")) {
            contentType = "image/png";
        } else if (suffix.equals("gif")) {
            contentType = "image/gif";
        }
        return contentType;
    }

    public static void createFolder(final String destFilePath) throws IOException {
        final File file = new File(getSecureFilePath(destFilePath));
        final File parent = file.getParentFile();
        if (parent != null) {
            if (!parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("Directory '" + parent + "' could not be created");
            }
        }
    }

    public static void mkdir(final String destFilePath) throws IOException {
        final File file = new File(getSecureFilePath(destFilePath));
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String getSecureFileName(final String fielName) {
        return FilenameUtils.getName(fielName);
    }

    public static String getSecureFilePath(final String filePath) {
        return "/" + FilenameUtils.getPath(filePath);
    }

    public static String getSecureFile(final String file) {
        return "/" + FilenameUtils.getPath(file) + "/" + FilenameUtils.getName(file);
    }

	public static void fileReplaceSpace(final String sourcePath, final String destPath) {
		final File file = new File(sourcePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			final byte[] mybyte = new byte[1024];
			int len = 0;
			final StringBuilder sb = new StringBuilder();
			while ((len = fis.read(mybyte)) > 0) {
				final String aa = new String(mybyte, 0, len);
				sb.append(aa);
			}
			String out = sb.toString();
			out = out.replaceAll("\r\n", "\n");

			final FileOutputStream fos = new FileOutputStream(destPath);

			fos.write(out.getBytes());

			fos.flush();

			fos.close();
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			ReleaseResourceUtil.closeFileInputStream(fis);
		}
	}

	public static long getFileSize(final File f) throws Exception {
		long size = 0;
		final File[] flist = f.listFiles();
		if(flist != null) {
		    for (int i = 0; i < flist.length; i++) {
	            if (flist[i].isDirectory()) {
	                size = size + getFileSize(flist[i]);
	            } else {
	                size = size + flist[i].length();
	            }
	        }
		}
		return size;
	}

	// 转换文件大小
	public static String formetFileSize(final long fileS) {
		final DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	public static int getFileCount(final File f) throws Exception {
		int count = 0;
		final File[] flist = f.listFiles();
		if(flist != null) {
		    for (int i = 0; i < flist.length; i++) {
	            if (!flist[i].isDirectory()) {
	                count ++;
	            } else {
	                count =count + getFileCount(flist[i]);
	            }
	        }
		}
		return count;
	}

	public static boolean checkImageFileSuffix(final String originalFilename){
		final String suffix = getSuffixName(originalFilename);
		if("jpg".equalsIgnoreCase(suffix) || "jpeg".equalsIgnoreCase(suffix) || "png".equalsIgnoreCase(suffix) || "gif".equalsIgnoreCase(suffix)){
			return true;
		}
		return false;
	}

	public static void main(final String[] args) throws Exception {
		FileUtil.fileReplaceSpace("D:\\dev_sync_s3_files.sh","E:\\dev_sync_s3_files.sh");
		FileUtil.fileReplaceSpace("D:\\uat_sync_s3_files.sh","E:\\uat_sync_s3_files.sh");
		FileUtil.fileReplaceSpace("D:\\prd_sync_s3_files.sh","E:\\prd_sync_s3_files.sh");
		//System.out.println("store size:" + FileUtil.formetFileSize(FileUtil.getFileSize(new File("D:\\s3_files\\store1"))));
		//System.out.println("tiles size:" + FileUtil.formetFileSize(FileUtil.getFileSize(new File("D:\\s3_files\\tiles1"))));
	}
}
