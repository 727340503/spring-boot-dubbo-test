package com.cherrypicks.tcc.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 类说明：根据语言返回Locale
 * 
 */
public class I18nUtil {
	private static Log LOGGER = LogFactory.getLog(I18nUtil.class);
	private static final String emptyString = "";
	private static final Map<String, Locale> paramMap = new HashMap<String, Locale>();
	private static final String baseName = "messages/messages";
	static {
		paramMap.put("en", Locale.US);
		paramMap.put("en_US", Locale.US);
		paramMap.put("en_UK", Locale.US);
		paramMap.put("zh", Locale.SIMPLIFIED_CHINESE);
		paramMap.put("zh_CN", new Locale("zh", "CN"));
		paramMap.put("zh_TW", new Locale("zh", "TW"));
		paramMap.put("zh_HK", new Locale("zh", "HK"));
		paramMap.put("th", new Locale("th", "TH"));
		paramMap.put("th_ZH", new Locale("th", "TH"));

	}

	public static Locale getLocale(String langCode) {

		try {
			if (langCode != null && langCode.split("_").length == 2) {

				String[] param = langCode.split("_");
				String lang = param[0];
				String country = param[1];
				return new Locale(lang, country);
			}
			return Locale.SIMPLIFIED_CHINESE;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return Locale.SIMPLIFIED_CHINESE;
		}
	}

	public static String getKeyMessage(Integer Key, String[] paramater,
			Locale locale) {

		try {
			ResourceBundleMessageSource s = new ResourceBundleMessageSource();
			s.setBasename(baseName);
			String str = s.getMessage(Key.toString(), paramater, locale);
			return str;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return emptyString;
		}

	}

	public static String getMessage(Integer Key, String[] paramater,
			String langCode) {
		try {
			ResourceBundleMessageSource s = new ResourceBundleMessageSource();
			s.setBasename(baseName);
			String str = s.getMessage(Key.toString(), paramater,
					getLocale(langCode));
			return str;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return emptyString;
		}

	}

	public static String getKeyMessage(String Key, String[] paramater,
			Locale locale) {

		try {
			ResourceBundleMessageSource s = new ResourceBundleMessageSource();
			s.setBasename(baseName);
			String str = s.getMessage(Key.toString(), paramater, locale);
			return str;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return emptyString;
		}

	}

	public static String getMessage(String Key, String[] paramater,
			String langCode) {
		try {
			ResourceBundleMessageSource s = new ResourceBundleMessageSource();
			s.setBasename(baseName);
			String str = s.getMessage(Key.toString(), paramater,
					getLocale(langCode));
			return str;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return emptyString;
		}

	}
	
	public static void main(String[] args) {

		//Locale aa=getLocale("vi_VN");
	}
}