package com.util;

import com.google.common.collect.Maps;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * ���Ϲ�����
 * @author Wen.Jian
 * @date 2013-4-28����3:48:26
 * @Copyright(c) Chengdu Chant Software Technology Co.,LTD.
 */
public final class CollectionUtil {

    /**
     * �жϼ����Ƿ�Ϊ��,ʹ�õ���org.springframework.util.CollectionUtils
     * @param collection ʵ��Collection������
     * @return Ϊ�շ���true , ���򷵻�false
     */
    public static boolean isEmpty(Collection collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * �жϼ����Ƿ�Ϊ��
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection) {
        return !CollectionUtils.isEmpty(collection);
    }

    /**
     * �ж�Map�����Ƿ�Ϊ��
     * @param map Map����
     * @return Ϊ�շ���true , ���򷵻�false
     */
    public static boolean isEmpty(Map<? extends Object, ? extends Object> map) {
        boolean isFlag = true;
        if (map != null) {
            isFlag = map.isEmpty();
        }
        return isFlag;
    }

    /**
     * �Ӽ����л�ȡָ�����͵�Ԫ��
     * @param collection Ҫ���ҵļ���
     * @param t ��ѯ����
     * @return �ö����Ͳ��Һ�ļ���
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
     * ��һ�����߶������ת��Ϊ����
     * @param ts
     * @return
     */
    public static <T> List<T> asList(T... ts) {
        List<T> result = null;
        if (ArrayUtil.isNotEmpty(ts)) {
            result = new ArrayList<T>();
            //��foreachΪ��index����������˳��
            for (int i = 0; i < ts.length; i++) {
                result.add(ts[i]);
            }
        }
        return result;
    }

    /**
     * ����key����value
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

    // ��list��blockSize��С�ȷ֣�������ĵ���һ��
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
     * �Ƚ�����List���ϣ�ɾ��������ͬ��
     * @param list1
     * @param list2
     * @return ���ص�һ�������б�ɾ����ʣ��
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
        // ��List�е����ݴ浽Map��
        Map<String, Integer> maxMap = new HashMap<String, Integer>(maxList.size());
        for (String string : maxList) {
            maxMap.put(string, 1);
        }
        // ѭ��minList�е�ֵ����� maxMap�� ��ͬ�� ����2
        for (String string : minList) {
            // ��ͬ��
            if (maxMap.get(string) != null) {
                maxMap.put(string, 2);
                continue;
            }
            // ����ȵ�
//            diff.add(string);
        }
        // ѭ��maxMap
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
     * ת�ַ���
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
//     * ��,�������ַ���תΪLong���ͼ���
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
//                logger.error("�ַ���תLong����ת������,���Ը��ַ���(\""+str+"\")",e);
//            }
//        }
//        return idList;
//    }

    /**
     * ��������Ԫ�ص�ĳ���ֶε�ֵ��ȡ����ƴ�ӳ�joinChar�ָ���ַ���</br>
     * ֻ֧�ֶ��󣬲�֧��Map��Array,List�ȵ�
     * created by huzy 2017/10/11 ����3:54
     * @param list
     * @param joinChar
     * @param fieldName
     * @return
     */
    public static String join(List list, String joinChar, String fieldName){
        if(isEmpty(list)){
            return "";
        }
        //����һ���ֶ�
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
                    //todo ����������Ӧ�ü�¼һ�²����׳�
                    e.printStackTrace();
                    return "";
                } catch (IllegalAccessException e) {
                    //��ȡfield��ֵ������
                    e.printStackTrace();
                    return "";
                }
            }
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

//    /**
//     * ����תΪLinkedHashSet
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
