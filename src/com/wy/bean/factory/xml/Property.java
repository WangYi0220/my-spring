package com.wy.bean.factory.xml;

public class Property {
	private String name;//属性的名称
	private String value;//属性值
	private String ref;//要注入的对象

	public Property() {
		super();
	}

	public Property(String name, String value, String ref) {
		super();
		this.name = name;
		this.value = value;
		this.ref = ref;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	@Override
	public String toString() {
		return "Property [name=" + name + ", value=" + value + ", ref=" + ref + "]";
	}
}
