package com.jitai.amqDemo.util;

import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

/**
 * String工具类
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月8日 下午2:46:30
 */
public class StringUtil {

	/**
	 * 
	 * 校验数字 <br>
	 * 长度：数字 n 位 <br>
	 * 开头：1~9 <br>
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:52:41
	 * @version 1.0.0
	 */
	public static boolean isNum(String input, int n) {
		return isNum(input, n, n);
	}

	/**
	 * 
	 * 校验数字 <br>
	 * 长度：数字 n ~ m 位 <br>
	 * 开头：1~9 <br>
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:52:52
	 * @version 1.0.0
	 */
	public static boolean isNum(String input, int n, int m) {
		if (input == null || n < 1 || m < 1 || n > m) {
			return false;
		}
		String regex = "[1-9][0-9]{" + --n + "," + --m + "}";
		return Pattern.matches(regex, input);
	}

	/**
	 * 
	 * 校验数字 <br>
	 * 长度：数字 n 位 <br>
	 * 开头：0~9 <br>
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:53:03
	 * @version 1.0.0
	 */
	public static boolean isNumber(String input, int n) {
		return isNumber(input, n, n);
	}

	/**
	 * 
	 * 校验数字 <br>
	 * 长度：数字 n ~ m 位 <br>
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:53:13
	 * @version 1.0.0
	 */
	public static boolean isNumber(String input, int n, int m) {
		if (input == null || n < 0 || m < 0 || n > m) {
			return false;
		}
		String regex = "[0-9]{" + n + "," + m + "}";
		return Pattern.matches(regex, input);
	}

	/**
	 * 
	 * 校验数字 <br>
	 * 长度：不限
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:53:22
	 * @version 1.0.0
	 */
	public static boolean isNumber(String input) {
		String regex = "[0-9]{1,}";
		return input == null ? false : Pattern.matches(regex, input);
	}

	public static boolean isDecimal(String input) {
		String regex = "^\\d+(\\.\\d+)?$";
		return input == null ? false : Pattern.matches(regex, input);
	}

	/**
	 * 
	 * 校验QQ号码 <br>
	 * 长度：5~10数字<br>
	 * 
	 * @param input
	 * @return
	 * 
	 */
	public static boolean isQQ(String input) {
		String regex = "[1-9]{1}[0-9]{4,9}";
		return input == null ? false : Pattern.matches(regex, input);
	}

	/**
	 * 
	 * 校验手机号码（单个）
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:53:58
	 * @version 1.0.0
	 */
	public static boolean isMobile(String input) {
		String regex = "1[3-5,7,8][0-9]{9}$";
		return input == null ? false : Pattern.matches(regex, input);
	}

	/**
	 * 
	 * 校验任意字符长度 <br>
	 * 长度： n 位 <br>
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:54:10
	 * @version 1.0.0
	 */
	public static boolean length(String input, int n) {
		return length(input, n, n);
	}

	/**
	 * 
	 * 校验任意字符长度 <br>
	 * 长度： n ~ m 位 <br>
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:54:19
	 * @version 1.0.0
	 */
	public static boolean length(String input, int n, int m) {
		if (input == null || n < 0 || m < 0 || n > m) {
			return false;
		}
		String regex = ".{" + n + "," + m + "}";
		return Pattern.matches(regex, input);
	}

	/**
	 * 
	 * 匹配包括下划线的任何单词字符。 <br>
	 * 长度： n 位 <br>
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:54:29
	 * @version 1.0.0
	 */
	public static boolean isWord(String input, int n) {
		return isWord(input, n, n);
	}

	/**
	 * 
	 * 匹配包括下划线的任何单词字符。 <br>
	 * 长度： n ~ m 位 <br>
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:54:37
	 * @version 1.0.0
	 */
	public static boolean isWord(String input, int n, int m) {
		if (input == null || n < 0 || m < 0 || n > m) {
			return false;
		}
		String regex = "[a-zA-Z0-9_]{" + n + "," + m + "}";
		return Pattern.matches(regex, input);
	}

	/**
	 * 
	 * 匹配包括下划线的任何单词字符。 <br>
	 * 长度： 不限 <br>
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:54:46
	 * @version 1.0.0
	 */
	public static boolean isWord(String input) {
		String regex = "[a-zA-Z0-9_]{1,}";
		return input == null ? false : Pattern.matches(regex, input);
	}

	/**
	 * 
	 * 校验 values 是否包含 input 值，如果 input 为 null，否则返回 false
	 * 
	 * @param ignoreCase
	 *            是否忽略大小写
	 * @param input
	 *            进行比较的 String
	 * @param values
	 *            进行比较的 String 集合
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:55:25
	 * @version 1.0.0
	 */
	public static boolean contains(boolean ignoreCase, String input, String... values) {
		if (input == null) {
			return false;
		}
		for (String value : values) {
			// 忽略大小写
			if (ignoreCase && input.equalsIgnoreCase(value)) {
				return true;
			}
			// 区分大小写
			if (!ignoreCase && input.equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是空字符串，为空时返回true
	 * 
	 * @author Robin Chang
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null ? true : s.length() == 0;
	}

	/**
	 * 自定义的分隔字符串函数 例如: 1,2,3 =>[1,2,3] 3个元素 ,2,3=>[,2,3] 3个元素 ,2,3,=>[,2,3,] 4个元素 ,,,=>[,,,] 4个元素
	 * 
	 * 5.22算法修改，为提高速度不用正则表达式 两个间隔符,,返回""元素
	 * 
	 * @param split
	 *            分割字符 默认,
	 * @param src
	 *            输入字符串
	 * @return 分隔后的list
	 * @author Robin
	 */
	public static List<String> splitToList(String split, String src) {
		// 默认,
		String sp = ",";
		if (split != null && split.length() == 1) {
			sp = split;
		}
		List<String> r = new ArrayList<String>();
		int lastIndex = -1;
		int index = src.indexOf(sp);
		if (-1 == index && src != null) {
			r.add(src);
			return r;
		}
		while (index >= 0) {
			if (index > lastIndex) {
				r.add(src.substring(lastIndex + 1, index));
			} else {
				r.add("");
			}

			lastIndex = index;
			index = src.indexOf(sp, index + 1);
			if (index == -1) {
				r.add(src.substring(lastIndex + 1, src.length()));
			}
		}
		return r;
	}

	/**
	 * 
	 * String数组转换long数组
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年10月11日 下午4:48:19
	 * @version 1.0.0
	 */
	public static Long[] stringToLong(String stringArray[]) {
		if (stringArray == null || stringArray.length < 1) {
			return null;
		}
		Long[] longArray = new Long[stringArray.length];
		for (int i = 0; i < longArray.length; i++) {
			try {
				longArray[i] = Long.valueOf(stringArray[i]);
			} catch (NumberFormatException e) {
				longArray[i] = 0L;
				continue;
			}
		}
		return longArray;
	}

	/**
	 * 判断object对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		return !isObjectEmpty(str);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isObjectEmpty(Object str) {
		if (null == str || "null".equals(str) || "".equals(str) || "undefined".equals(str)) {
			return true;
		}

		if (str instanceof JsonNull) {
			return true;
		}

		if (str instanceof JsonPrimitive) {
			String temp = ((JsonPrimitive) str).getAsString();
			if (null == temp || "null".equals(temp) || "".equals(temp)) {
				return true;
			}
		}

		if (str instanceof CharSequence) {
			return ((CharSequence) str).length() == 0;
		}

		if (str instanceof Collection) {
			return ((Collection) str).isEmpty();
		}

		if (str instanceof Map) {
			return ((Map) str).isEmpty();
		}

		if (str instanceof Object[]) {
			Object[] object = (Object[]) str;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNotEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}

		return false;
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 *
	 * @param value指定的字符串
	 * @return 字符串的长度
	 * @author yangwenbin
	 * @date 2016年7月6日 上午10:30:50
	 * @version 1.0.0
	 */
	public static int length(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String addr = request.getHeader("X-Forwarded-For");

		// 如果通过多级反向代理，X-Forwarded-For的值不止一个，而是一串用逗号分隔的IP值，此时取X-Forwarded-For中第一个非unknown的有效IP字符串
		if (!(addr == null || "".equals(addr.trim()) || "unknown".equalsIgnoreCase(addr.trim())) && (addr.indexOf(",") > -1)) {
			String[] arr = addr.split(",");
			for (String str : arr) {
				if (!(str == null || "".equals(str.trim()) || "unknown".equalsIgnoreCase(str.trim()))) {
					addr = str;
					break;
				}
			}
		}

		if (addr == null || "".equals(addr.trim()) || "unknown".equalsIgnoreCase(addr.trim())) {
			addr = request.getHeader("X-Real-IP");
		}

		if (addr == null || "".equals(addr.trim()) || "unknown".equalsIgnoreCase(addr.trim())) {
			addr = request.getRemoteAddr();
		}

		return ("0:0:0:0:0:0:0:1").equals(addr) ? "127.0.0.1" : addr;
	}

	/**
	 * 
	 * 获取传递service参数方法，当传递参数为object的"" 或 null 或 """" 或 "null"，放回null
	 *
	 * @param
	 * @return
	 * @author zhangyi
	 * @date 2016年9月5日 上午10:29:35
	 * @version 1.0.0
	 */
	public static String getPara(Object o) {
		String s = String.valueOf(o);
		if ("\"\"".equals(s) || "null".equals(s) || "".equals(s) || s == null) {
			return null;
		}
		return s;
	}

	/**
	 * 
	 * 生成随机数字和字母
	 *
	 * @param length
	 *            表示生成几位随机数
	 * @return
	 * @exception @author
	 *                lvzonghai
	 * @date 2016年11月2日 上午11:21:40
	 * @version 1.0.0
	 */
	public static String getStringRandom(int length) {
		String val = "";
		Random random = new Random();

		// 参数length，表示生成几位随机数
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	public static String formatString(List<String> list) {
		if (list == null) {
			return "";
		}
		return list.toString().replace("[", "").replace("]", "").replaceAll(" ", "");
	}

	public static String getUUID32() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/***
	 * 匹配指定长度的字母 长度： n ~ m 位
	 * 
	 * @param input
	 * @param n
	 * @param m
	 * @return
	 * @author xusisi
	 * @time 2018年1月18日 下午3:23:22
	 */
	public static boolean isLetter(String input, int n, int m) {
		if (input == null || n < 0 || m < 0 || n > m) {
			return false;
		}
		String regex = "[a-zA-Z]{" + n + "," + m + "}";
		return Pattern.matches(regex, input);
	}

	

	public static void main(String[] args) {
		String s = "000012";
		// if (StringUtil.isNumber(s)) {
		// System.out.println("是");
		// }

		String x = "A00011";
		if (StringUtil.isNumber(x)) {
			System.out.println("是");
		}
	}
}