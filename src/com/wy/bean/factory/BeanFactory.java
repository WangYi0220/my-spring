package com.wy.bean.factory;

public interface BeanFactory {
	
	Object getBean(String beanId) throws Exception;
	
	@SuppressWarnings("rawtypes")
	Object getBean(Class clazz) throws Exception;
	
}
