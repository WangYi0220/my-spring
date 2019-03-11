package com.wy.bean.factory.xml;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 获取xml中的信息
 * @author 百逸同学
 *
 */
public class XmlLoad {
	
	/**
	 * <p>创建根节点对象
	 * @param url
	 * <p>xml文件路径
	 * @return rootElm
	 * <p>返回根节点对象
	 */
	public static Element CreateRootElement(String url) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(url));
			Element rootElm = document.getRootElement();
			return rootElm;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * <p>获取bean节点和bean的属性
	 * @param url
	 * <p>xml文件路径
	 * @return beanMap
	 * <p>Map<bean名称,bean所有属性>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Bean> getBeanMap(String url) throws Exception{
		Map<String, Bean> beanMap = new HashMap<String,Bean>();
		List<Element> elements =  CreateRootElement(url).elements("bean");
		for (Element element : elements) {
			Bean bean = new Bean();
			if(element.attribute("id") == null){
				throw new Exception("bean属性id找不到");
			} else {
				bean.setId(element.attribute("id").getValue());
			}
			if(element.attribute("class") == null){
				throw new Exception("bean属性class找不到");
			} else {
				bean.setClassName(element.attribute("class").getValue());
			}
			List<Property> propertyList = getPropertyList(element);
			bean.setPropertyList(propertyList);
			beanMap.put(bean.getId(), bean);
		}
		return beanMap;
	}
	
	/**
	 * 获取bean子节点property
	 * @param element
	 * 当前bean的Element对象
	 * @return propertyList
	 * List<Element>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Property> getPropertyList(Element element) throws Exception{
		List<Property> propertyList = new ArrayList<Property>();
		List<Element> elements = element.elements("property");
		for (Element element1 : elements) {
			Property property = new Property();
			if(element1.attribute("name") == null){
				throw new Exception("property属性name找不到");
			} else {
				property.setName(element1.attribute("name").getValue());
			}
			if(element1.attribute("value") == null){
				property.setValue(null);
			} else {
				property.setValue(element1.attribute("value").getValue());
			}
			if(element1.attribute("ref") == null){
				property.setRef(null);
			} else {
				property.setRef(element1.attribute("ref").getValue());
			}
			propertyList.add(property);
		}
		return propertyList;
	}
	
	/**
	 * 获取扫描标签中的包名
	 * @param url
	 * <p>xml文件路径
	 * @return packageName
	 * <p>返回包名
	 */
	public static String getPackageNameByXML(String url){
		Element element = CreateRootElement(url).element("scanner");
		if(element == null){
			return null;
		}
		String packageName= element.attribute("base-package").getValue();
		return packageName;
	}
}
