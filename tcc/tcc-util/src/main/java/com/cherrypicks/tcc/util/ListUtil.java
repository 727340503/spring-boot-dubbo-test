package com.cherrypicks.tcc.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public final class ListUtil {

    private ListUtil() {
    }

    public static int size(final Object[] list) {
        if (list == null) {
            return 0;
        } else {
            return list.length;
        }
    }

    public static int size(final List<?> list) {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    public static List<String> uniq(final List<String> list) {
        if (list == null) {
            return null;
        }
        final List<String> result = new ArrayList<String>();
        for (final String str : list) {
            if (!result.contains(str)) {
                result.add(str);
            }
        }
        return result;
    }

    public static boolean isNotEmpty(final List<?> list) {
        return !isEmpty(list);
    }

    public static boolean isEmpty(final List<?> list) {
        return list == null || list.isEmpty();
    }

    public static List<String> toStringResult(final List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public static String[] defaultStrings(final String[] strs) {
        if (strs == null) {
            return new String[0];
        }
        return strs;
    }

    public static <T> List<T> defaultList(final List<T> list) {
        if (list == null) {
            return new ArrayList<T>();
        }
        return list;
    }

    public static List<?> noNull(final List<?> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        // 删除空记录
        final Iterator<?> iterator = list.iterator();
        while (iterator.hasNext()) {
            final Object next = iterator.next();
            if (next == null) {
                iterator.remove();
            }
        }
        return list;
    }

    public static List<String> toList(final Set<String> set) {
        if (set == null || set.isEmpty()) {
            return null;
        }
        final List<String> list = new ArrayList<String>();
        final Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    public static Set<Integer> toIntSet(final List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        final Set<Integer> set = new LinkedHashSet<Integer>();
        for (final Integer element : list) {
            set.add(element);
        }
        return set;
    }

    public static Set<String> toSet(final List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        final Set<String> set = new LinkedHashSet<String>();
        for (final String element : list) {
            set.add(element);
        }
        return set;
    }

    public static List<String> toList(final String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        final String[] strs = content.split(",");
        final List<String> list = new ArrayList<String>();
        for (final String str : strs) {
            list.add(str);
        }
        return list;
    }

    public static List<String> toList(String content, final boolean trim) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        if (!trim) {
            return toList(content);
        }
        content = content.trim();
        final String[] strs = content.split(",");
        final List<String> list = new ArrayList<String>();
        for (String str : strs) {
            str = str.trim();
            list.add(str);
        }
        return list;
    }

    public static List<Integer> toIntList(final Set<String> members) {
        if (members == null || members.isEmpty()) {
            return null;
        }
        final List<Integer> result = new ArrayList<Integer>();
        final Iterator<String> iterator = members.iterator();
        while (iterator.hasNext()) {
            final String str = iterator.next();
            final Integer newsId = Integer.parseInt(str);
            result.add(newsId);
        }
        return result;
    }

    // public static Map<Integer, String> toIntMap(List<Integer> keyList,
    // List<String> valueList) {
    // if (keyList.size() != valueList.size()) {
    // throw new IllegalArgumentException("两个list参数不一致.");
    // }
    //
    // String[] values = new String[valueList.size()];
    // valueList.toArray(values);
    // Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    //
    // int index = 0;
    // for (Integer key : keyList) {
    // String value = values[index];
    // map.put(key, value);
    // index++;
    // }
    // return map;
    // }

    // public static Map<String, String> toMap(List<String> keyList,
    // List<String> valueList) {
    // if (keyList.size() != valueList.size()) {
    // throw new IllegalArgumentException("两个list参数不一致.");
    // }
    //
    // String[] values = new String[valueList.size()];
    // valueList.toArray(values);
    // Map<String, String> map = new LinkedHashMap<String, String>();
    //
    // int index = 0;
    // for (String key : keyList) {
    // String value = values[index];
    // map.put(key, value);
    //
    // index++;
    // }
    // return map;
    // }

    // public static Map<Integer, String> toIntMap(Set<Integer> keyList,
    // List<String> valueList) {
    //
    // if (keyList.size() != valueList.size()) {
    // throw new IllegalArgumentException("两个list参数不一致.");
    // }
    //
    // String[] values = new String[valueList.size()];
    // valueList.toArray(values);
    // Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    //
    // int index = 0;
    // for (Integer key : keyList) {
    // String value = values[index];
    // map.put(key, value);
    //
    // index++;
    // }
    // return map;
    // }

    // public static Map<String, String> toMap(Set<String> keyList, List<String>
    // valueList) {
    //
    // if (keyList.size() != valueList.size()) {
    // throw new IllegalArgumentException("两个list参数不一致.");
    // }
    // String[] values = new String[valueList.size()];
    // valueList.toArray(values);
    // Map<String, String> map = new LinkedHashMap<String, String>();
    //
    // int index = 0;
    // for (String key : keyList) {
    // String value = values[index];
    // map.put(key, value);
    // index++;
    // }
    // return map;
    // }

    public static String[] getIntKeys(final String prefix, final Set<Integer> idSet) {
        final String[] keys = new String[idSet.size()];
        int index = 0;
        for (final Integer id : idSet) {
            keys[index] = prefix + ":" + id;
            index++;
        }
        return keys;
    }

    public static String[] getKeys(final String prefix, final Set<String> usernameSet) {
        final String[] keys = new String[usernameSet.size()];
        int index = 0;
        for (final String username : usernameSet) {
            keys[index] = prefix + ":" + username;
            index++;
        }
        return keys;
    }

    public static String[] toStringArray(final List<Integer> list) {
        if (isEmpty(list)) {
            return null;
        }
        final String[] fields = new String[list.size()];
        int index = 0;
        for (final Integer num : list) {
            fields[index] = Integer.toString(num);
            index++;
        }
        return fields;
    }

    public static List<Integer> toIntList(final List<String> members) {
        if (members == null || members.isEmpty()) {
            return null;
        }
        final List<Integer> result = new ArrayList<Integer>();
        final Iterator<String> iterator = members.iterator();
        while (iterator.hasNext()) {
            final String str = iterator.next();
            if (str == null) {
                result.add(null);
            } else {
                final int newsId = Integer.parseInt(str);
                result.add(newsId);
            }
        }
        return result;
    }

    public static List<String> makeList(final String prefix, final int start, final int size) {
        final List<String> list = new ArrayList<String>();
        final int end = start + size;
        for (int i = start; i < end; i++) {
            list.add(prefix + i);
        }
        return list;
    }

    public static void main(final String[] args) {
        final List<String> ids = new ArrayList<String>();
        for (int i = 0; i < 300; i++) {
            ids.add(String.valueOf(i));
        }

        final List<List<String>> idArray = ListUtil.chopped(ids, 500);

        for (final List<String> choppedIds : idArray) {
            System.out.println(choppedIds.size());
        }
    }

    public static <T> List<List<T>> chopped(final List<T> list, final int l) {
        final List<List<T>> parts = new ArrayList<List<T>>();
        final int n = list.size();
        for (int i = 0; i < n; i += l) {
            parts.add(new ArrayList<T>(list.subList(i, Math.min(n, i + l))));
        }
        return parts;
    }
}
