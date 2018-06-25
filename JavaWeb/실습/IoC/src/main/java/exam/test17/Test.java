package exam.test17;

import java.text.SimpleDateFormat;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args){
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("exam/test17/beans.xml");
		
		Car car1 = (Car) ctx.getBean("car1");		
		System.out.println(car1);
	}
}

