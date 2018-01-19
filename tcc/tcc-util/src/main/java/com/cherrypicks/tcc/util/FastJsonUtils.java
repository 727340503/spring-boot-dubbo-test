package com.cherrypicks.tcc.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * fastjson工具类
 * 
 * @version:1.0.0
 */
public class FastJsonUtils {

	private static final Logger logger = LoggerFactory.getLogger(FastJsonUtils.class);


	/**
	 * 用fastjson 将JavaBean解析为一个json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		if (object == null) {
			return null;
		}
		try {
			return JSON.toJSONString(object);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 用fastjson 将json字符串解析为一个 JavaBean
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		if (json == null || json.length() == 0) {
			return null;
		}
		try {
			return JSON.parseObject(json, clazz);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> toListObject(String json, Class<T> cls) {
		if (json == null || json.length() == 0) {
			return null;
		}
		try {
			return JSON.parseArray(json, cls);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 用fastjson 将json 解析成 List<Map<String,Object>>
	 * 
	 * @param json
	 * @return
	 */
	public static List<Map<String, Object>> getListMap(String json) {
		if (json == null || json.length() == 0) {
			return null;
		}
		try {
			return JSON.parseObject(json, new TypeReference<List<Map<String, Object>>>() {
			});
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
