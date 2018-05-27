package exam.test15;

import java.text.SimpleDateFormat;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args){
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("exam/test15/beans.xml");
		
		Car car1 = (Car) ctx.getBean("car1");
		Car car2 = (Car) ctx.getBean("car2");
		
		System.out.println(car1);
		System.out.println(car2);
	}
}

