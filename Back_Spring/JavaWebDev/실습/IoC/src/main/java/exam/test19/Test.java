package exam.test19;

import java.text.SimpleDateFormat;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args){
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("exam/test19/beans.xml");
		
		Car car = (Car) ctx.getBean("car");
		
		Engine engine = (Engine) ctx.getBean("engine");
		engine.setMaker("Hyundai");
		engine.setCc(1997);
		
		if(car != null){
			System.out.println("car != null");
		}
		
		if(engine != null){
			System.out.println("engine != null");
		}
		
		System.out.println(car);
	}
}

