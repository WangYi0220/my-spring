package com.wy.bean.factory.xml.utils;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class CommonUtils {
	/**
	 * 字符串首字母转小写
	 * @return
	 */
	public static String lowerFirst(String oldStr) {
		char[] chars = oldStr.toCharArray();
		chars[0] += 32;
		return String.valueOf(chars);
	}

	/**
	 * 将map集合封装到bean
	 * @param map
	 * @param object
	 */
	@SuppressWarnings("rawtypes")
	public static void toBean(Map map, Object object) {
		try {
			BeanUtils.populate(object, map);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
