package com.cherrypicks.tcc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sea
 * @date 2017-6-1
 */
public class JavaShellUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JavaShellUtil.class);
	
	public static boolean executeShell(String shellCommandFile) {
		//替换"\r\n", "\n"
		FileUtil.fileReplaceSpace(shellCommandFile, shellCommandFile);
		try {
			String cmdstring = "chmod 777 " + shellCommandFile;
			Process proc = Runtime.getRuntime().exec(cmdstring);
			proc.waitFor(); //阻塞，直到上述命令执行完
			  
			String[] cmd = { "/bin/sh", "-c", shellCommandFile };
			// 执行Shell命令
			Runtime.getRuntime().exec(cmd);
			return true;
		} catch (Exception ioe) {
			logger.error(ioe.getMessage(), ioe);
			return false;
		} 
	}
	
}
