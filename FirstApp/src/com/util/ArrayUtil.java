package com.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组操作工具类
 * @author Yang.Cheng
 * @date 2013-5-8下午2:41:19
 * @Copyright(c) Chengdu Chant Software Technology Co.,LTD.
 */
public final class ArrayUtil {

    private ArrayUtil(){}
    /**
     * 数组转换为集合,如果数组为null返回null
     * @param objects 转换的数组
     * @return List 转换后的List集合
     */
    public static <T> List<T> convertList(T[] objects) {
        List<T> list = null;
        if(!isEmpty(objects)) {
            list = new ArrayList<T>();
            list.addAll(Arrays.asList(objects));
        }
        return list;
    }
    /**long数组转换为包装类型数组
     * @param objs long数组
     * @return Long[] 包装类型Long数组
     */
    public static Long[] objectArray(long[] objs) {
        Long[] result = null;
        if(objs != null){
            result = new Long[objs.length];
            for(int i = 0; i < objs.length ; i++){
                result[i] = objs[i];
            }
        }
        return result;
    }

    /**int数组转换为包装类型数组
     * @param objs int数组
     * @return Integer[] 包装类型Integer数组
     */
    public static Integer[] objectArray(int[] objs) {
        Integer[] result = null;
        if(objs != null){
            result = new Integer[objs.length];
            for(int i = 0; i < objs.length ; i++){
                result[i] = objs[i];
            }
        }
        return result;
    }

    /**char类型数组转换为包装类型数组
     * @param objs char数组
     * @return Character[] 包装类型Character数组
     */
    public static Character[] objectArray(char[] objs) {
        Character[] result = null;
        if(objs != null){
            result = new Character[objs.length];
            for(int i = 0; i < objs.length ; i++){
                result[i] = objs[i];
            }
        }
        return result;
    }

    /**
     * boolean类型数组转换为包装类型数组
     * @param objs
     * @return
     */
    public static Boolean[] objectArray(boolean[] objs) {
        Boolean[] result = null;
        if(objs != null){
            result = new Boolean[objs.length];
            for(int i = 0; i < objs.length ; i++){
                result[i] = objs[i];
            }
        }
        return result;
    }
    /**
     * String数组转换为Long数组
     * @param objs
     * @return
     */
    public static Long[] StringArrayToLongArray(String[] objs) {
        Long[] result = null;
        if(objs != null) {
            result = new Long[objs.length];
            for(int i = 0; i < objs.length; i++) {
                result[i] = Long.valueOf(objs[i]);
            }
        }
        return result;
    }
    /**
     * Long数组转换为String数组
     * @param objs
     * @return
     */
    public static String[] LongArrayToStringArray(Long[] objs) {
        String[] result = null;
        if(objs != null) {
            result = new String[objs.length];
            for(int i = 0; i < objs.length; i++) {
                result[i] = String.valueOf(objs[i]);
            }
        }
        return result;
    }
    /**
     * 连接数组，返回连接收的字符串
     * @param objs 数组
     * @param joinChar 分隔符号
     * @return 数组连连接后的字符串
     */
    public static String joinArray(Object[] objs,String joinChar){
        if(isEmpty(objs)){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for(Object o:objs){
            builder.append(o == null?"null":o.toString()).append(joinChar);
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    /**
     * 连接int数组
     * @param objs int类型数组
     * @param joinChar 分隔符
     * @return 连接后的字符串
     */
    public static String joinArray(int[] objs,String joinChar){
        return joinArray(objectArray(objs), joinChar);
    }

    /**连接long数组
     * @param objs long类型数组
     * @param joinChar 分隔符
     * @return 连接后的字符串
     */
    public static String joinArray(long[] objs,String joinChar){
        return joinArray(objectArray(objs), joinChar);
    }

    /** 连接char数组
     * @param objs char类型数组
     * @param joinChar 分隔符号
     * @return 连接后的字符串
     */
    public static String joinArray(char[] objs,String joinChar){
        return joinArray(objectArray(objs), joinChar);
    }

    /**判断object类型的数组是否为空
     * @param objs 数组
     * @return true为空，false不为空
     */
    public static boolean isEmpty(Object[] objs) {
        return ck(objs);
    }

    /**
     * 判断object类型的数组是否不为空
     * @param objs
     * @return
     */
    public static boolean isNotEmpty(Object[] objs) {
        return !isEmpty(objs);
    }

    /**
     * @param objs
     * @return
     */
    private static boolean ck(Object[] objs) {
        if(objs != null && objs.length > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**判断int类型数组是否为空
     * @param objs int类型数组
     * @return true为空，false不为空
     */
    public static boolean isEmpty(int[] objs) {
        return ck(objectArray(objs));
    }

    /**判断byte类型数组是否为空
     * @param objs byte类型数组
     * @return true为空，false不为空
     */
    public static boolean isEmpty(byte[] objs) {
        boolean result = objs == null?true:objs.length<=0;
        return result;
    }

    /**判断long类型数组是否为空
     * @param objs long类型数组
     * @return true为空，false不为空
     */
    public static boolean isEmpty(long[] objs) {
        return ck(objectArray(objs));
    }

    /**判断char类型数组是否为空
     * @param objs char数组
     * @return true为空，false不为空
     */
    public static boolean isEmpty(char[] objs) {
        return ck(objectArray(objs));
    }

    public static boolean isEmpty(boolean[] objs) {
        return ck(objectArray(objs));
    }

    /**
     * 从数组中查找指定类型的元素，返回该类型List集合
     * @param objs 查找的数组
     * @param t 指定类型
     * @return 查找后的List集合，否则返回Null
     */
    public static <T> List<T> findElementsByType(Object[] objs,Class<T> t){
        List<?> coll = objs == null?null: Arrays.asList(objs);
        return CollectionUtil.findElementsByType(coll, t);
    }
    /**
     * Object数组转换为Integer数组
     * @param objs Object数组
     * @return Integer[] Integer类型数组
     */
    public static Integer[] arrayTypeConvert(Object[] objs) {
        if(objs != null && objs.length > 0) {
            Integer[] nums = new Integer[objs.length];
            int index = 0;
            for(Object o : objs) {
                if(o != null && !"".equals(o)) {
                    if(StringUtils.isNumeric(o.toString())) {
                        nums[index] = Integer.valueOf(o.toString());
                    }
                }
                index++;
            }
            return nums;
        }
        return new Integer[]{};
    }

    public static Long[] splitLongArray(String str) {
        if(StringUtil.isNotEmpty(str)){
            String[] strings = str.split(",");
            Long[] longs = new Long[strings.length];
            for (int i = 0; i < strings.length; i++) {
                String string = strings[i];
                longs[i] = Long.valueOf(string);
            }
            return longs;
        }
        return null;
    }

    /**
     * 根据index获取元素
     * @param array 数组
     * @param index index
     * @param defaultIndex 如果为空取哪一个index
     * @param <T>
     * @return
     */
    public static <T> T get(T[] array, int index,int defaultIndex) {
        if(isNotEmpty(array)){
            if(array.length<index && array.length<defaultIndex){
                throw new IndexOutOfBoundsException("array length:"+array.length+",index:"+index+",defaultIndex:"+defaultIndex);
            }
            T t = array[index];
            if(t == null){
                t = array[defaultIndex];
            }
            return t;
        }
        return null;
    }
}
