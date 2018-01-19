package com.cherrypicks.tcc.util;


public class IPUtil {

//	private final static Log logger = LogFactory.getLog(IPUtil.class);
//
//	public static String[] getAcccessIpRange() {
//		String accessIpRanges = "";
//		try {
//			accessIpRanges = PropertiesUtil.getProperties("project.properties", "access.ip.ranges");
//		} catch (final Exception e) {
//			logger.error("load properties error", e);
//		}
//		return accessIpRanges.split(",");
//	}

	private static long getIpNum(final String ipAddress) {
		final String[] ip = ipAddress.split("\\.");
		final long a = Integer.parseInt(ip[0]);
		final long b = Integer.parseInt(ip[1]);
		final long c = Integer.parseInt(ip[2]);
		final long d = Integer.parseInt(ip[3]);
		final long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
		return ipNum;
	}

	public static boolean isInner(final String userIp, final String beginIp, final String endIp) {
		final long begin = getIpNum(beginIp);
		final long end = getIpNum(endIp);
		final long user = getIpNum(userIp);
		return ((user >= begin) && (user <= end)) || userIp.equals("127.0.0.1");
	}
}
