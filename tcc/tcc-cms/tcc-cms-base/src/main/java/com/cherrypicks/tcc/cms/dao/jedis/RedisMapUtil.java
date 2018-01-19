package com.cherrypicks.tcc.cms.dao.jedis;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.cherrypicks.tcc.util.DateUtil;

public final class RedisMapUtil {

    private static Log logger = LogFactory.getLog(RedisMapUtil.class);

    private RedisMapUtil() {
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean
     *            要转化的JavaBean 对象
     * @return 转化出来的 Map 对象
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, String> toMap(final Object bean) {
        final Class type = bean.getClass();
        final Map<String, String> returnMap = new HashMap<String, String>();

        final PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(type);
        final BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
        for (final PropertyDescriptor pd : pds) {
            final String propertyName = pd.getName();
            final Class propertyType = beanWrapper.getPropertyType(propertyName);
            final Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            if (propertyValue != null && !"class".equals(propertyName)) {
                if (propertyType.equals(Date.class)) {
                    returnMap.put(propertyName, DateUtil.formatTime((Date) propertyValue));
                } else if (propertyType.equals(Time.class)) {
                    returnMap.put(propertyName, DateUtil.getFormatTime((Time) propertyValue));
                } else if (propertyType.equals(List.class)) {
                    final StringBuilder valueStr = new StringBuilder();

                    boolean flag = false;
                    for (final Object obj : (List) propertyValue) {
                        if (flag) {
                            valueStr.append(RedisKey.DELIMITER);
                        } else {
                            flag = true;
                        }
                        valueStr.append(String.valueOf(obj));
                    }

                    returnMap.put(propertyName, valueStr.toString());
                } else if (propertyType.equals(boolean.class) || propertyType.equals(Boolean.class)) {
                    returnMap.put(propertyName, String.valueOf(propertyValue));
                } else if (propertyType.equals(byte.class) || propertyType.equals(Byte.class)) {
                    returnMap.put(propertyName, String.valueOf(propertyValue));
                } else if (propertyType.equals(char.class) || propertyType.equals(Character.class)) {
                    returnMap.put(propertyName, String.valueOf(propertyValue));
                } else if (propertyType.equals(short.class) || propertyType.equals(Short.class)) {
                    returnMap.put(propertyName, String.valueOf(propertyValue));
                } else if (propertyType.equals(int.class) || propertyType.equals(Integer.class)) {
                    returnMap.put(propertyName, String.valueOf(propertyValue));
                } else if (propertyType.equals(long.class) || propertyType.equals(Long.class)) {
                    returnMap.put(propertyName, String.valueOf(propertyValue));
                } else if (propertyType.equals(float.class) || propertyType.equals(Float.class)) {
                    returnMap.put(propertyName, String.valueOf(propertyValue));
                } else if (propertyType.equals(double.class) || propertyType.equals(Double.class)) {
                    returnMap.put(propertyName, String.valueOf(propertyValue));
                } else if (propertyType.equals(String.class)) {
                    returnMap.put(propertyName, String.valueOf(propertyValue));
                } else {
                    logger.error("Unknown or unsupport property type[" + propertyType + "]");
                }
            }
        }

        return returnMap;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param type
     *            要转化的类型
     * @param map
     *            包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws InstantiationException
     *             如果实例化 JavaBean 失败
     * @throws IllegalAccessException
     *             如果实例化 JavaBean 失败
     * @throws InvocationTargetException
     *             如果调用属性的 setter 方法失败
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws SecurityException
     */
    @SuppressWarnings("rawtypes")
    public static Object toBean(final Class type, final Map<Object, Object> map)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            SecurityException, NoSuchFieldException {
        final Object bean = type.newInstance(); // 创建 JavaBean 对象

        final PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(type);
        final BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
        for (final PropertyDescriptor pd : pds) {
            final String propertyName = pd.getName();
            final Class propertyType = beanWrapper.getPropertyType(propertyName);

            final String propertyValue = (String) map.get(propertyName);

            if (propertyValue != null && !"class".equals(propertyName)) {
                if (propertyType.equals(Date.class)) {
                    PropertyUtils.setProperty(bean, propertyName, DateUtil.parseDateTime(propertyValue));
                } else if (propertyType.equals(Time.class)) {
                    final Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtil.parseDate(DateUtil.TIME_PATTERN_DEFAULT, propertyValue));
                    PropertyUtils.setProperty(bean, propertyName, new Time(calendar.getTimeInMillis()));
                } else if (propertyType.equals(List.class)) {
                    processList(type, propertyType, propertyName, propertyValue, bean);
                } else if (propertyType.equals(boolean.class) || propertyType.equals(Boolean.class)) {
                    PropertyUtils.setProperty(bean, propertyName, Boolean.valueOf(propertyValue));
                } else if (propertyType.equals(byte.class) || propertyType.equals(Byte.class)) {
                    PropertyUtils.setProperty(bean, propertyName, Byte.valueOf(propertyValue));
                } else if (propertyType.equals(char.class) || propertyType.equals(Character.class)) {
                    PropertyUtils.setProperty(bean, propertyName, propertyValue.charAt(0));
                } else if (propertyType.equals(short.class) || propertyType.equals(Short.class)) {
                    PropertyUtils.setProperty(bean, propertyName, Short.valueOf(propertyValue));
                } else if (propertyType.equals(int.class) || propertyType.equals(Integer.class)) {
                    PropertyUtils.setProperty(bean, propertyName, Integer.valueOf(propertyValue));
                } else if (propertyType.equals(long.class) || propertyType.equals(Long.class)) {
                    PropertyUtils.setProperty(bean, propertyName, Long.valueOf(propertyValue));
                } else if (propertyType.equals(float.class) || propertyType.equals(Float.class)) {
                    PropertyUtils.setProperty(bean, propertyName, Float.valueOf(propertyValue));
                } else if (propertyType.equals(double.class) || propertyType.equals(Double.class)) {
                    PropertyUtils.setProperty(bean, propertyName, Double.valueOf(propertyValue));
                } else if (propertyType.equals(String.class)) {
                    PropertyUtils.setProperty(bean, propertyName, propertyValue);
                } else {
                    logger.error("Unknown or unsupport property type[" + propertyType + "]");
                }
            }
        }
        return bean;
    }

    @SuppressWarnings("rawtypes")
    private static Class getPropertyGenericTypeClass(final Class type, final String propertyName)
            throws SecurityException, NoSuchFieldException {
        final Field field = type.getDeclaredField(propertyName);
        final ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        return (Class) genericType.getActualTypeArguments()[0];
    }

    @SuppressWarnings("rawtypes")
    private static void processList(final Class type, final Class propertyType, final String propertyName, final String propertyValue,
            final Object bean) throws SecurityException, NoSuchFieldException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {
        final String[] values = propertyValue.split(RedisKey.DELIMITER);
        final Class propertyGenericTypeClass = getPropertyGenericTypeClass(type, propertyName);

        // 泛型support的类型
        if (propertyGenericTypeClass.equals(boolean.class) || propertyGenericTypeClass.equals(Boolean.class)) {
            final List<Boolean> list = new ArrayList<Boolean>();

            for (final String value : values) {
                list.add(Boolean.valueOf(value));
            }

            PropertyUtils.setProperty(bean, propertyName, list);
        } else if (propertyGenericTypeClass.equals(byte.class) || propertyGenericTypeClass.equals(Byte.class)) {
            final List<Byte> list = new ArrayList<Byte>();

            for (final String value : values) {
                list.add(Byte.valueOf(value));
            }

            PropertyUtils.setProperty(bean, propertyName, list);
        } else if (propertyGenericTypeClass.equals(char.class) || propertyGenericTypeClass.equals(Character.class)) {
            final List<Character> list = new ArrayList<Character>();

            for (final String value : values) {
                list.add(value.charAt(0));
            }

            PropertyUtils.setProperty(bean, propertyName, list);
        } else if (propertyGenericTypeClass.equals(short.class) || propertyGenericTypeClass.equals(Short.class)) {
            final List<Short> list = new ArrayList<Short>();

            for (final String value : values) {
                list.add(Short.valueOf(value));
            }

            PropertyUtils.setProperty(bean, propertyName, list);
        } else if (propertyGenericTypeClass.equals(int.class) || propertyGenericTypeClass.equals(Integer.class)) {
            final List<Integer> list = new ArrayList<Integer>();

            for (final String value : values) {
                list.add(Integer.valueOf(value));
            }

            PropertyUtils.setProperty(bean, propertyName, list);
        } else if (propertyGenericTypeClass.equals(long.class) || propertyGenericTypeClass.equals(Long.class)) {
            final List<Long> list = new ArrayList<Long>();

            for (final String value : values) {
                list.add(Long.valueOf(value));
            }

            PropertyUtils.setProperty(bean, propertyName, list);
        } else if (propertyType.equals(float.class) || propertyType.equals(Float.class)) {
            final List<Float> list = new ArrayList<Float>();

            for (final String value : values) {
                list.add(Float.valueOf(value));
            }

            PropertyUtils.setProperty(bean, propertyName, list);
        } else if (propertyType.equals(double.class) || propertyType.equals(Double.class)) {
            final List<Double> list = new ArrayList<Double>();

            for (final String value : values) {
                list.add(Double.valueOf(value));
            }

            PropertyUtils.setProperty(bean, propertyName, list);
        } else if (propertyGenericTypeClass.equals(String.class)) {
            final List<String> list = new ArrayList<String>();

            for (final String value : values) {
                list.add(value);
            }

            PropertyUtils.setProperty(bean, propertyName, list);
        } else {
            logger.error("Unknown or unsupport property generic type[" + propertyType + "]");
        }
    }
}
