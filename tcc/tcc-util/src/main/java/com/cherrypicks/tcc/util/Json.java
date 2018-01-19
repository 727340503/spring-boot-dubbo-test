package com.cherrypicks.tcc.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public final class Json {

    private static final Logger logger = LoggerFactory.getLogger(Json.class);
    private static final ObjectMapper mapper = new ObjectMapper(); // can reuse, share

    static {
        mapper.setTimeZone(TimeZone.getDefault());
        mapper.setDateFormat(new SimpleDateFormat(DateUtil.DATETIME_PATTERN_DEFAULT));
    }

    private Json() {
    }

    /**
     * 将对象转成json.
     *
     * @param obj
     *            对象
     * @return
     */
    public static String toJson(final Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (final JsonGenerationException e) {
            logger.error(e.getMessage(), e);
        } catch (final JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (final IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * json转List.
     *
     * @param content
     *            json数据
     * @param valueType
     *            泛型数据类型
     * @return
     */
    public static <T> List<T> toListObject(final String content, final Class<T> valueType) {
        if (content == null || content.length() == 0) {
            return null;
        }
        try {
            return mapper.readValue(content,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, valueType));
        } catch (final JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (final JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (final IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> List<T> toObject(final List<String> jsonList, final Class<T> valueType) {
        if (jsonList == null || jsonList.isEmpty()) {
            return null;
        }
        final List<T> list = new ArrayList<T>();
        for (final String json : jsonList) {
            list.add(Json.toObject(json, valueType));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(final String content) {
        return Json.toObject(content, Map.class);
    }

    @SuppressWarnings("unchecked")
    public static Set<Object> toSet(final String content) {
        return Json.toObject(content, Set.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> toMap(final String json, final Class<T> clazz) {
        return Json.toObject(json, Map.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> toSet(final String json, final Class<T> clazz) {
        return Json.toObject(json, Set.class);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toNotNullMap(final String json) {
        Map<String, Object> map = Json.toObject(json, Map.class);
        if (map == null) {
            map = new LinkedHashMap<String, Object>();
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> toNotNullMap(final String json, final Class<T> clazz) {
        Map<String, T> map = Json.toObject(json, Map.class);
        if (map == null) {
            map = new LinkedHashMap<String, T>();
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> toNotNullSet(final String json, final Class<T> clazz) {
        Set<T> set = Json.toObject(json, Set.class);
        if (set == null) {
            set = new LinkedHashSet<T>();
        }
        return set;
    }

    /**
     * 类型转换.
     *
     * @param obj
     * @param clazz
     * @return
     */
    public static <T> T convert(final Object obj, final Class<T> clazz) {
        final String json = Json.toJson(obj);
        return toObject(json, clazz);
    }

    /**
     * 将Json转换成对象.
     *
     * @param json
     * @param valueType
     * @return
     */
    public static <T> T toObject(final String json, final Class<T> clazz) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (final JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (final JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (final IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static void print(final Object obj) {
        final String json = Json.toJson(obj);
        logger.info("json:" + json);
    }

    public static void print(final Object obj, final String name) {
        final String json = Json.toJson(obj);
        logger.info("json info " + name + "::" + json);
    }

    public static void main(final String[] args) {

    }
}
