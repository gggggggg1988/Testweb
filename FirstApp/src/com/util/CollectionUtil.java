package com.util;

import com.google.common.collect.Maps;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 集合工具类
 * @author Wen.Jian
 * @date 2013-4-28下午3:48:26
 * @Copyright(c) Chengdu Chant Software Technology Co.,LTD.
 */
public final class CollectionUtil {

    /**
     * 判断集合是否为空,使用到了org.springframework.util.CollectionUtils
     * @param collection 实现Collection集合类
     * @return 为空返回true , 否则返回false
     */
    public static boolean isEmpty(Collection collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断集合是否不为空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection) {
        return !CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断Map集合是否为空
     * @param map Map集合
     * @return 为空返回true , 否则返回false
     */
    public static boolean isEmpty(Map<? extends Object, ? extends Object> map) {
        boolean isFlag = true;
        if (map != null) {
            isFlag = map.isEmpty();
        }
        return isFlag;
    }

    /**
     * 从集合中获取指定类型的元素
     * @param collection 要查找的集合
     * @param t 查询类型
     * @return 置顶类型查找后的集合
     */
    public static <T> List<T> findElementsByType(Collection<?> collection, Class<T> t) {
        List<T> result = new ArrayList<T>();
        if (collection != null && t != null && !collection.isEmpty()) {
            for (Object o : collection) {
                if (o != null && t.isAssignableFrom(o.getClass())) {
                    result.add((T) o);
                }
            }
        }
        return result;
    }

    /**
     * 把一个或者多个对象转换为集合
     * @param ts
     * @return
     */
    public static <T> List<T> asList(T... ts) {
        List<T> result = null;
        if (ArrayUtil.isNotEmpty(ts)) {
            result = new ArrayList<T>();
            //改foreach为以index遍历，保留顺序
            for (int i = 0; i < ts.length; i++) {
                result.add(ts[i]);
            }
        }
        return result;
    }

    /**
     * 根据key查找value
     * @param key
     * @param param
     * @return
     */
    public static Object findMapByKey(String key, Map<? extends Object, ? extends Object> param) {
        if (!CollectionUtil.isEmpty(param)) {
            if (param.containsKey(key)) {
                return param.get(key);
            }
        }
        return null;
    }

    public static List<String> longListToStringList(List<Long> list) {

        if (isNotEmpty(list)) {
            List<String> list2 = new ArrayList<String>();
            for (Long s : list) {
                list2.add(String.valueOf(s));
            }
            return list2;
        }
        return null;
    }

    // 将list按blockSize大小等分，最后多余的单独一份
    public static <T> List<List<T>> subList(List<T> list, int blockSize) {
        List<List<T>> lists = new ArrayList<List<T>>();
        try {
            if (list != null && blockSize > 0) {
                int listSize = list.size();
                if (listSize <= blockSize) {
                    lists.add(list);
                    return lists;
                }
                int batchSize = listSize / blockSize;
                int remain = listSize % blockSize;
                for (int i = 0; i < batchSize; i++) {
                    int fromIndex = i * blockSize;
                    int toIndex = fromIndex + blockSize;
                    lists.add(list.subList(fromIndex, toIndex));
                }
                if (remain > 0) {
                    lists.add(list.subList(listSize - remain, listSize));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 比较两个List集合，删除其中相同的
     * @param list1
     * @param list2
     * @return 返回第一个集合中被删除后剩下
     */
    public static List<String> getDiffrent(List<String> list1, List<String> list2) {
        long st = System.nanoTime();
        List<String> diff = new ArrayList<String>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if (list2.size() > list1.size()) {
            maxList = list2;
            minList = list1;
        }
        // 将List中的数据存到Map中
        Map<String, Integer> maxMap = new HashMap<String, Integer>(maxList.size());
        for (String string : maxList) {
            maxMap.put(string, 1);
        }
        // 循环minList中的值，标记 maxMap中 相同的 数据2
        for (String string : minList) {
            // 相同的
            if (maxMap.get(string) != null) {
                maxMap.put(string, 2);
                continue;
            }
            // 不相等的
//            diff.add(string);
        }
        // 循环maxMap
        for (Map.Entry<String, Integer> entry : maxMap.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }

    public static List<Long> stringListToLongList(List<String> list) {
        if (isNotEmpty(list)) {
            List<Long> list2 = new ArrayList<Long>();
            for (String s : list) {
                list2.add(Long.parseLong(s));
            }
            return list2;
        }
        return null;
    }

    public static List<Long> stringArrayToLongList(String[] arr) {
    	if (arr!=null && arr.length>0) {
    		List<Long> list2 = new ArrayList<Long>(arr.length);
    		for (String s:arr) {
    			list2.add(Long.parseLong(s));
    		}
    		return list2;
    	}
    	return null;
    }

    /**
     * 转字符串
     * @author huzy
     * @param objects
     * @param joinChar
     * @return
     */
    public static String join(List objects, String joinChar) {
        if(isEmpty(objects)){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for(Object o:objects){
            builder.append(o == null?"null":o.toString()).append(joinChar);
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

//    /**
//     * 将,隔开的字符串转为Long类型集合
//     * @author huzy
//     * @param longStr
//     * @return
//     */
//    public static List<Long> splitLongList(String longStr) {
//        String[] idsStrings = longStr.split(",");
//        List<Long> idList = new ArrayList<Long>();
//        for (int i = 0; i < idsStrings.length; i++) {
//            String str = idsStrings[i];
//            try {
//                idList.add(Long.valueOf(str));
//            } catch (NumberFormatException e) {
//                logger.error("字符串转Long数组转换错误,忽略该字符串(\""+str+"\")",e);
//            }
//        }
//        return idList;
//    }

    /**
     * 将集合内元素的某个字段的值获取传来拼接成joinChar分割的字符串</br>
     * 只支持对象，不支持Map，Array,List等等
     * created by huzy 2017/10/11 下午3:54
     * @param list
     * @param joinChar
     * @param fieldName
     * @return
     */
    public static String join(List list, String joinChar, String fieldName){
        if(isEmpty(list)){
            return "";
        }
        //缓存一下字段
        Map<Class,Field> classFieldMap = Maps.newHashMap();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            if(o!=null){
                try {
                    Class clazz = o.getClass();
                    Field field = classFieldMap.get(clazz);
                    if(field==null){
                        field = o.getClass().getDeclaredField(fieldName);
                        if(!field.isAccessible()){
                            ReflectionUtils.makeAccessible(field);
                        }
                        classFieldMap.put(clazz,field);
                    }
                    Object value = field.get(o);
                    builder.append(value).append(joinChar);
                } catch (NoSuchFieldException e) {
                    //todo 讲道理，这里应该记录一下并且抛出
                    e.printStackTrace();
                    return "";
                } catch (IllegalAccessException e) {
                    //获取field的值出错了
                    e.printStackTrace();
                    return "";
                }
            }
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

//    /**
//     * 数组转为LinkedHashSet
//     * @param ts
//     * @param <T>
//     * @return
//     */
//    public static <T> Set<T> asSet(T... ts) {
//        if(ArrayUtil.isNotEmpty(ts)){
//            Set<T> set = new LinkedHashSet<>(ts.length);
//            for (T t : ts) {
//                set.add(t);
//            }
//            return set;
//        }
//        return new LinkedHashSet<>(1);
//    }
}
