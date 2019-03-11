package com.wy.bean.factory.xml;

import java.util.List;

public class Bean {
	private String id;//bean的id
	private String className;//类名+包名
	private List<Property> propertyList;//属性集合

	public Bean() {
		super();
	}

	public Bean(String id, String className, List<Property> propertyList) {
		super();
		this.id = id;
		this.className = className;
		this.propertyList = propertyList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Property> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<Property> propertyList) {
		this.propertyList = propertyList;
	}

	@Override
	public String toString() {
		return "Bean [id=" + id + ", className=" + className + ", propertyList=" + propertyList
				+ "]";
	}

}
