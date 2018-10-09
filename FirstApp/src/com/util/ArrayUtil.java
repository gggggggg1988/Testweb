package com.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * �������������
 * @author Yang.Cheng
 * @date 2013-5-8����2:41:19
 * @Copyright(c) Chengdu Chant Software Technology Co.,LTD.
 */
public final class ArrayUtil {

    private ArrayUtil(){}
    /**
     * ����ת��Ϊ����,�������Ϊnull����null
     * @param objects ת��������
     * @return List ת�����List����
     */
    public static <T> List<T> convertList(T[] objects) {
        List<T> list = null;
        if(!isEmpty(objects)) {
            list = new ArrayList<T>();
            list.addAll(Arrays.asList(objects));
        }
        return list;
    }
    /**long����ת��Ϊ��װ��������
     * @param objs long����
     * @return Long[] ��װ����Long����
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

    /**int����ת��Ϊ��װ��������
     * @param objs int����
     * @return Integer[] ��װ����Integer����
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

    /**char��������ת��Ϊ��װ��������
     * @param objs char����
     * @return Character[] ��װ����Character����
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
     * boolean��������ת��Ϊ��װ��������
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
     * String����ת��ΪLong����
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
     * Long����ת��ΪString����
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
     * �������飬���������յ��ַ���
     * @param objs ����
     * @param joinChar �ָ�����
     * @return ���������Ӻ���ַ���
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
     * ����int����
     * @param objs int��������
     * @param joinChar �ָ���
     * @return ���Ӻ���ַ���
     */
    public static String joinArray(int[] objs,String joinChar){
        return joinArray(objectArray(objs), joinChar);
    }

    /**����long����
     * @param objs long��������
     * @param joinChar �ָ���
     * @return ���Ӻ���ַ���
     */
    public static String joinArray(long[] objs,String joinChar){
        return joinArray(objectArray(objs), joinChar);
    }

    /** ����char����
     * @param objs char��������
     * @param joinChar �ָ�����
     * @return ���Ӻ���ַ���
     */
    public static String joinArray(char[] objs,String joinChar){
        return joinArray(objectArray(objs), joinChar);
    }

    /**�ж�object���͵������Ƿ�Ϊ��
     * @param objs ����
     * @return trueΪ�գ�false��Ϊ��
     */
    public static boolean isEmpty(Object[] objs) {
        return ck(objs);
    }

    /**
     * �ж�object���͵������Ƿ�Ϊ��
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

    /**�ж�int���������Ƿ�Ϊ��
     * @param objs int��������
     * @return trueΪ�գ�false��Ϊ��
     */
    public static boolean isEmpty(int[] objs) {
        return ck(objectArray(objs));
    }

    /**�ж�byte���������Ƿ�Ϊ��
     * @param objs byte��������
     * @return trueΪ�գ�false��Ϊ��
     */
    public static boolean isEmpty(byte[] objs) {
        boolean result = objs == null?true:objs.length<=0;
        return result;
    }

    /**�ж�long���������Ƿ�Ϊ��
     * @param objs long��������
     * @return trueΪ�գ�false��Ϊ��
     */
    public static boolean isEmpty(long[] objs) {
        return ck(objectArray(objs));
    }

    /**�ж�char���������Ƿ�Ϊ��
     * @param objs char����
     * @return trueΪ�գ�false��Ϊ��
     */
    public static boolean isEmpty(char[] objs) {
        return ck(objectArray(objs));
    }

    public static boolean isEmpty(boolean[] objs) {
        return ck(objectArray(objs));
    }

    /**
     * �������в���ָ�����͵�Ԫ�أ����ظ�����List����
     * @param objs ���ҵ�����
     * @param t ָ������
     * @return ���Һ��List���ϣ����򷵻�Null
     */
    public static <T> List<T> findElementsByType(Object[] objs,Class<T> t){
        List<?> coll = objs == null?null: Arrays.asList(objs);
        return CollectionUtil.findElementsByType(coll, t);
    }
    /**
     * Object����ת��ΪInteger����
     * @param objs Object����
     * @return Integer[] Integer��������
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
     * ����index��ȡԪ��
     * @param array ����
     * @param index index
     * @param defaultIndex ���Ϊ��ȡ��һ��index
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
