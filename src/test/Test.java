package test;


import java.lang.reflect.Field;

import com.wy.bean.factory.BeanFactory;
import com.wy.bean.factory.BeanFactoryImpl;
import com.wy.bean.factory.xml.utils.CommonUtils;
import com.wy.entitys.Money;
import com.wy.entitys.User;

public class Test {
	public static void main(String[] args) throws Exception {
		BeanFactory beanFactory = new BeanFactoryImpl("src/test.xml");
		Money money = (Money) beanFactory.getBean("money");
		System.out.println(money);
		User user = (User) beanFactory.getBean("user");
		System.out.println(user);
		System.out.println("aaaaaaaaaaaaa");
		System.out.println(CommonUtils.lowerFirst(BeanFactoryImpl.class.getSimpleName()));
		Field[] fields = User.class.getDeclaredFields();
		System.out.println(fields.length);
		for (Field field : fields) {
			System.out.println(field.getName());
		}
		
		/*BeanFactory factory = new BeanFactoryImpl("src/test.xml");
		User user = (User) factory.getBean("aa");
		System.out.println(user);*/
	}
}
