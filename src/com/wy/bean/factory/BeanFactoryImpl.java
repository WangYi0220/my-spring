package com.wy.bean.factory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.wy.bean.factory.annotation.JavaBean;
import com.wy.bean.factory.xml.Bean;
import com.wy.bean.factory.xml.Property;
import com.wy.bean.factory.xml.XmlLoad;
import com.wy.bean.factory.xml.utils.CommonUtils;
import com.wy.bean.factory.xml.utils.PackageUtils;
/**
 * 容器工厂实现类
 * @author 百逸同学
 *
 */
public class BeanFactoryImpl implements BeanFactory {

	private String url; // xml文件路径
	/**
	 * 存放对象
	 * Map<对象名，对象>
	 * 必须为static
	 */
	private static Map<String, Object> beanInstanceMap = new HashMap<String, Object>();

	public BeanFactoryImpl() {
		super();
	}

	public BeanFactoryImpl(String url) {
		super();
		this.url = url;
		/*
		 * 在构造方法调用实例化方法，实例当前类时就实例容器中的对象
		 * 就像使用spring时, new ClassPathXmlApplicationContext("applicationContext.xml");
		 */
		instance();

	}
	
	/**
	 * 从容器中取出对象
	 */
	@Override
	public Object getBean(String beanId) throws Exception {
		Object object = null;
		if (beanInstanceMap.containsKey(beanId)) {
			object = beanInstanceMap.get(beanId);
		} else {
			throw new Exception("找不到类");
		}
		return object;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getBean(Class clazz) throws Exception {
		Object object = null;
		if (beanInstanceMap.containsKey(clazz.getSimpleName().toLowerCase())) {
			object = beanInstanceMap.get(clazz.getSimpleName().toLowerCase());
		} else {
			throw new Exception("找不到类");
		}
		return object;
	}
	
	/**
	 * 实例化对象，该方法在构造方法中调用（构造方法有说明）
	 * 分两种方式
	 * 1.使用注解
	 * 2.使用xml配置
	 */
	@SuppressWarnings({ "rawtypes" })
	private void instance() {
		try {
			/*1.使用注解 */
			String packageName = XmlLoad.getPackageNameByXML(this.url);//获取xml文件中要扫描的包名
			if (packageName != null) {
				List<Class<?>> classes = PackageUtils.getClasses(packageName);//得到指定包中所有类的类类型集合
				classHandler(classes);//调用类处理方法，处理得到的类集合
			}
			/*2.使用xml配置*/
			Map<String, Bean> beanMap = XmlLoad.getBeanMap(this.url);//得到xml文件中配置的bean
			beanMap.forEach((className, bean) -> {//开始循环
				try {
					Class clazz = Class.forName(bean.getClassName());
					Object object = clazz.newInstance();//先实例当前bean，以便接下来操纵对象
					List<Property> propertyList = bean.getPropertyList();//得到当前bean的property
					/*property分为1.普通类型2.对象类型*/
					if (propertyList != null) {
						Map<String, String> proMap = new HashMap<String, String>();//存放普通类型属性的属性名和值
						for (Property property : propertyList) {
							if (property.getValue() != null) {//当property为普通类型
								proMap.put(property.getName(), property.getValue());
							}
							if (property.getRef() != null) {//当property为对象类型
								if (beanInstanceMap.get(property.getRef()) != null) {
									Object diBean = beanInstanceMap.get(property.getRef());//从容器中获取要注入的对象
									PropertyDescriptor pd = new PropertyDescriptor(property.getName(),//通过Java内省机制注入
											object.getClass());
									pd.getWriteMethod().invoke(object, diBean);
								}
							}
						}
						CommonUtils.toBean(proMap, object);//用工具类把普通类型的属性Map(proMap)注入到对象中
					}
					beanInstanceMap.put(bean.getId(), object);//最后把处理过的对象添加到容器
				} catch (Exception e) {
					e.printStackTrace();
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 类处理方法
	 * 当使用注解时，处理指定包下的带有JavaBean(自定义)注解的类
	 * 以及该类下面带有Resource(JDK)注解的属性
	 * @param classes
	 */
	public void classHandler(List<Class<?>> classes) {
		try {
			Object object = null;
			for (Class<?> class1 : classes) {
				JavaBean annotation = class1.getAnnotation(JavaBean.class);
				if (annotation != null) {
					object = class1.newInstance();
					Field[] fields = class1.getDeclaredFields();
					for (Field field : fields) {
						Resource annotation1 = field.getAnnotation(Resource.class);
						/*如果当前字段有Resource注解，则通过属性名来获取容器中的对象	*/
						if (annotation1 != null) {
							Object diBean = beanInstanceMap.get(field.getName());
							if (diBean != null) {
								PropertyDescriptor pd = new PropertyDescriptor(field.getName(), object.getClass());
								pd.getWriteMethod().invoke(object, diBean);
							}
						}
					}

				}
				if (!annotation.value().trim().equals("")) {//自定义注解JavaBean有Value可以自定义类的名称，如果有则按自定义
					beanInstanceMap.put(annotation.value(), object);
					return;
				}
				beanInstanceMap.put(CommonUtils.lowerFirst(class1.getSimpleName()), object);//没有自定义名称则类名首字母转小写
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
