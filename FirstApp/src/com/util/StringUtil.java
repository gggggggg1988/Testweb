package com.util;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author Yang.Cheng
 * @date 2013-2-19上午11:34:31
 * @Copyright(c) Chengdu Chant Software Technology Co.,LTD.
 */
public final class StringUtil {

    // 过滤所有以<开头以>结尾的标签
    private final static String regxpForHtml = "<([^>]*)>";

    private StringUtil() {

    }

    /**
     * @param s
     * @return
     */
    public static String trim(String s) {
        return s == null ? s : s.trim().replaceAll("&nbsp;*","").replaceAll("[　*| *]*","").replaceAll("[　*| *]*$","");
    }

    /**
     * 判断字符串是否为null
     * @param s 需要非空判断的字符串
     * @return 为空返回true,否则返回false
     */
    public static boolean isEmpty(String s) {
        return s == null ? true : ("".equals(s.trim())?true:s.trim().equalsIgnoreCase("null"));
    }

    /**
     * 判断不为null
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean isNotEmpty(String... s) {
    	boolean result = true;
    	if (ArrayUtil.isNotEmpty(s)) {
			for (String str : s) {
				result = isNotEmpty(str);
				if (!result) {
					return false;
				}
			}
		}
        return result;
    }

    /**
     * 把字符串转换为标准的html格式字符串
     * @param value 转换的字符串
     * @return 转换后的html格式字符串
     */
    public final static String convert2HtmlStr(String value) {
        if (isEmpty(value))
            return value;
        StringBuffer result = new StringBuffer(value.length() + 50);
        char ftemp;
        int i = 0;
        while (i < value.length()) {
            ftemp = value.charAt(i);
            switch (ftemp) {
            //                case 32: //空白
            //                    //result.append("&nbsp;");
            //                    break;
                case 10://换行
                    //判断下一个字符是否为回车键
                    if ((i + 1) < value.length()) {
                        if (value.charAt(i + 1) == 13)
                            i++;//跳过判断，避免多产生空白行
                    }
                    result.append("<br/>");
                    break;
                case 13://回车
                    //判断下一个字符是否为换行键
                    if ((i + 1) < value.length()) {
                        if (value.charAt(i + 1) == 10)
                            i++;//跳过判断，避免多产生空白行
                    }
                    result.append("<br>");
                    break;
                case 34://双引号
                    result.append("&quot;");
                    break;
                case 39://单引号
                	result.append("&apos;");
                	break;
                case 38://&
                    result.append("&amp;");
                    break;
                case 60://<
                    result.append("&lt;");
                    break;
                case 62://>
                    result.append("&gt;");
                    break;
                case 160:
                    result.append("&nbsp;");
                    break;
                default:
                    result.append(ftemp);
                    break;
            }
            i++;
        }
        return result.toString();
    }

    /**
     *
     * 基本功能：过滤所有以"<"开头以">"结尾的标签
     * <p>
     *
     * @param str
     * @return String
     */
    public static String filterHtml(String str) {
        Pattern pattern = Pattern.compile(regxpForHtml);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 判断是否有中文
     * @param c 字符
     * @return 有中文返回true，否则返回false
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否有中文
     * @param strName 判断的字符串
     * @return 有中文返回true，否则返回false
     */
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param strtemp 要截取的字符串
     * @param width 保留的字节长度
     * @param ellipsis 替代超长部分的字符串
     * @return
     */
    public static String abbreviate(String strtemp, int width, String ellipsis) {
        if (isEmpty(strtemp)) {
            return "";
        }
        String[] strs = strtemp.split("<br/>");
        String[] strsTemp = new String[strs.length];
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            // byte length
            int d = 0;
            // char length
            int n = 0;
            for (; n < str.length(); n++) {
                d = (int) str.charAt(n) > 256 ? d + 2 : d + 1;
                if (d > width) {
                    break;
                }
            }
            if (d > width) {
                strsTemp[i] = str.substring(0, n > 0 ? n : 0) + ellipsis;
                break;
            } else {
                strsTemp[i] = str.substring(0, n);
            }
            width -= d;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strsTemp.length; i++) {
            String o = strsTemp[i];
            if (!isEmpty(o)) {
                builder.append(o == null ? "null" : o.toString());
                if (i != strs.length - 1) {
                    builder.append("<br />");
                }
            }
        }
        return builder.toString();
    }

    /**
     * 过滤字符串中的[image_XXXX]
     * @param str
     * @return
     */
    public static String moveImageInfo(String str) {
        Pattern p = Pattern.compile("\\[image_\\w+]");
        Matcher m = p.matcher(str);
        while (m.find()) {
            str = str.replace(m.group(), "");
        }
        return str.replaceAll("&nbsp;", "");
    }

    /**
    * 过滤字符串中的[image_XXXX]
    * moveImageInfo(str) 相同
    * @param str
    * @return
    */
    public static String moveImageInfo2(String str) {
        if (str == null || str == "") {
            return "";
        }
        int index0 = -1;
        int index1 = -1;
        while ((index0 = str.indexOf("[image_")) > -1) {
            index1 = str.indexOf("]");
            if (index1 <= -1) {
                break;
            }
            str = str.substring(0, index0) + ((index1 + 1) > str.length() ? "" : str.substring(index1 + 1));
        }
        return str;
    }

    /**
     * 替换[image_XXXX]为[图片],[em_XX]为[表情]
     * @param str
     * @return
     */
    public static String moveImageInfo3(String str) {
        Pattern p = Pattern.compile("\\[image_\\w+]");
        Matcher m = p.matcher(str);
        while (m.find()) {
            str = str.replace(m.group(), "[图片]");
        }
        p = Pattern.compile("\\[em_.+]");
        m = p.matcher(str);
        while (m.find()) {
            str = str.replace(m.group(), "[表情]");
        }
        return str;
    }

    /**
     * 根据[image_XXXX]获取文件的id
     * @param str 格式为[image_XXXX]
     * @return
     */
    public static List<String> getContentIds(String str) {
        List<String> ids = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        Pattern p = Pattern.compile("\\[image_\\w+]");
        Matcher m = p.matcher(str);
        while (m.find()) {
            builder.append(m.group()).append(",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.lastIndexOf(","));
            String[] images = builder.toString().split(",");
            for (String s : images) {
                String id = s.substring(s.indexOf("_") + 1, s.lastIndexOf("]"));
                ids.add(id);
            }
        }
        return ids;
    }
    /**
     * 根据字符串获取fids获取所有的id
     * <li>fids标准格式为[XXXXXX,XXXXXXX,XXXXXX]</li>
     * @param fids 具有指定格式的有逗号分隔的字符串
     * @return
     */
    public static List<String> getFileIds(String fids) {
        List<String> fileIds = null;
        if (StringUtil.isNotEmpty(fids)) {
            fileIds = new ArrayList<String>();
            String[] ids = fids.split(",");
            for (String id : ids) {
                if (id != null) {
                    fileIds.add(id);
                }
            }
        }
        return fileIds;
    }

    /**
     * 根据给定的content，把ids和sign链接在一起
     * @param content 内容
     * @param ids 链接的id号
     * @param sign 链接的符号
     * <li>demo:[content:"你好"] , [ids:{1,2,3}] , [sign:"image"]</br>最后的结构为：你好[image_1][image_2][image_3]</li>
     * @return
     */
    public static String connetStr(String content, List<String> ids, String sign) {
//        StringBuilder builder = new StringBuilder(content + "\n\n");
//        if (CollectionUtil.isNotEmpty(ids)) {
//            for (String id : ids) {
//                builder.append("[" + sign + "_" + id + "]\n");
//            }
//        }
//        return builder.toString();
        StringBuilder builder = new StringBuilder(content);
        if (CollectionUtil.isNotEmpty(ids)) {
            for (String id : ids) {
                builder.append("[" + sign + "_" + id + "]");
            }
        }
        return builder.toString();
    }

    /**
     * 把文件id组装为标准格式[image_XXXXXXX,image_XXXXXXX]
     * @param fids
     * @param content
     * @param sign
     * @return
     */
    public static String builderStrForImageSign(String fids, String content, String sign) {
        if (StringUtil.isNotEmpty(fids)) {
            return connetStr(content, getFileIds(fids), sign);
        }
        return content;
    }
//    /**
//     * 获得标题图片id
//     * @param content
//     * @return
//     */
//    public static Long getHtmlImgId(String content)  {
//		Document doc = Jsoup.parse(content);
//		Elements imgs = doc.select("img");
//		if (imgs.size() > 0) {
//			String contentHtml = imgs.get(0).attr("src");
//			Pattern pat = Pattern.compile("/tc_web/tc/space/([0-9]*)/\\?m=showFile");
//			Matcher mat = pat.matcher(contentHtml);
//			if (mat.matches()) {
//				return Long.valueOf(mat.group(1));
//			}
//		}
//		return null;
//    }
//    /**
//     * 去除正文的图片标签
//     * @param content
//     * @return
//     */
//    public static String removeHtmlImg(String content) {
//    	if (isNotEmpty(content)) {
//    		Document doc = Jsoup.parse(content);
//			Elements imgs = doc.select("img");
//			imgs.remove();
//			return doc.toString();
//		}
//    	return content;
//    }
//    /**
//     * 替换正文图片中的路径
//     * @param content
//     * @return
//     */
//    public static String replaceHtmlImg(String content) {
//    	if (StringUtil.isNotEmpty(content)) {
//    		Document doc = Jsoup.parse(content);
//    		Elements imgs = doc.select("img");
//    		if (imgs.size() > 0) {
//    			Iterator<Element> elemenmIterator = imgs.iterator();
//    			Pattern pat = Pattern.compile("/tc_web/tc/space/([0-9]*)/\\?m=showFile");
//    			while (elemenmIterator.hasNext()) {
//    				Element et = elemenmIterator.next();
//    				String contentHtml = et.attr("src");
//    				Matcher mat = pat.matcher(contentHtml);
//    				if (mat.matches()) {
//    					Long fileId = Long.valueOf(mat.group(1));
//    					String newString = et.attr("src").replaceAll("/tc_web/tc/space/([0-9]*)/\\?m=showFile", "/td_web/td/showFile/"+fileId+"/");
//    					et.attr("src", newString);
//    				}
//    			}
//    			return doc.toString();
//    		}
//		}
//    	return content;
//    }

    /**
     * 替换正文图片中的路径
     * @param content
     * @return
     */
    public static String replaceHtmlImg2(String content) {
    	if (StringUtil.isNotEmpty(content)) {
			Pattern p = Pattern.compile("<img.*?/>");
			 Matcher m = p.matcher(content);
		        while (m.find()) {
		        	content = content.replace(m.group(), "[图片]");
		        }
		}
    	return content;
    }

    public static String connetStr(String str, Integer type ) {
    	StringBuilder result = new StringBuilder();
    	if (isNotEmpty(str)) {
			String[] resouce = str.split(",");
			for (int i = 0; i < resouce.length; i++) {
				String res = resouce[i];
				if (res.split("_").length <= 0) {
					result.append(type + "_" + res).append(",");
				}

			}
			if (result.length() > 0) {
				result.deleteCharAt(result.length() - 1);
			}
		}
		return result.toString();
	}

    public static List<Long> splitStrForList(String str) {
    	List<Long> ids = new ArrayList<Long>();
    	if (StringUtil.isNotEmpty(str)) {
    		String[] firstSends = str.split(",");
        	for (String s : firstSends) {
        		if (s != null) {
					String[] source = s.split("_");
					if (source.length > 1) {
						ids.add(Long.valueOf(source[1]));
					} else {
						ids.add(Long.valueOf(source[0]));
					}
				}
			}
		}
    	return ids;
    }

    public static String fixString(String content) {
        if (null != content) {
            content = content.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("&lt;br /&gt;", "<br />");
        }
        return content;
    }

    /**
     * 判断是否是html片段
     * @param title
     * @return
     */
    public static boolean isHtml(String title) {
        String regEx_html = "<[^>]+>";
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(title);
        return m_html.find();
    }

    /**
     * 转换99999以内数字为文字描述
     * @param sno
     * @return
     */
    public static String getSnoDes(int sno) {
        StringBuffer des = new StringBuffer();
        String snoString = ""+sno;
        String[] units = {"万","千","百","十"};
        String[] unit = new String[snoString.length()-1];
        for (int i = 0; i < unit.length;i++) {
            unit[i] = units[units.length-unit.length+i];
        }
        int prev = 1;
        for (int i=0; i< snoString.length(); i++) {
            int pint = Integer.parseInt(snoString.substring(i, i + 1));
            switch (pint) {
                case 1:
                    des.append("一");
                    break;
                case 2:
                    des.append("二");
                    break;
                case 3:
                    des.append("三");
                    break;
                case 4:
                    des.append("四");
                    break;
                case 5:
                    des.append("五");
                    break;
                case 6:
                    des.append("六");
                    break;
                case 7:
                    des.append("七");
                    break;
                case 8:
                    des.append("八");
                    break;
                case 9:
                    des.append("九");
                    break;
                case 0:
                    if (prev != 0 && i+1!=snoString.length()) {
                        des.append("零");
                    }
                    break;
                default:
                    break;
            }
            if (i+1<snoString.length()&&pint!=0) {
                des.append(unit[i]);
            }
            prev = pint;
        }
        if (des.toString().startsWith("一十")) {
            des.deleteCharAt(0);
        }
        if (des.toString().endsWith("零")) {
            des.deleteCharAt(des.length()-1);
        }
        return des.toString();
    }

    /**
     * 转换数据库字段名为实体类中的字段名
     * @param fieldName
     * @return
     */
    public static String DBField2BeanField(String fieldName) {
        StringBuffer result = new StringBuffer();
        String[] spitParts = fieldName.split("_");
        int i = 0;
        for (String part:spitParts) {
            String p1 = part.substring(0,1);
            if ("0,1,2,3,4,5,6,7,8,9".contains(p1)||i==0) {
                result.append(part);
            } else {
                result.append(p1.toUpperCase()).append(part.substring(1,part.length()));
            }
            i++;
        }
        return result.toString();
    }

    /**
     * 判断字符串是否是整数,包括正整数和负整数
     * @param num
     * @return
     */
    public static boolean isDigit(String num) {
        Pattern pattern = Pattern.compile("-?[0-9]+");
        Matcher isNum = pattern.matcher(num);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    
    public static String getChinese(String paramValue) {
        if(isEmpty(paramValue)){
            return null;
        }
    	String regex = "([\u4e00-\u9fa5]+)";
    	String str = "";
    	Matcher matcher = Pattern.compile(regex).matcher(paramValue);
    	while (matcher.find()) {
    		str+= matcher.group(0);
    	}
    	return str;
	}
}
