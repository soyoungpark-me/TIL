package exam.test07;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args){
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("exam/test07/beans.xml");
		
		Car car1 = (Car) ctx.getBean("car1");
		for(Tire tire : car1.getTires()){
			System.out.println(tire);
		}
		
		Car car2 = (Car) ctx.getBean("car2");
		System.out.println(car2);
		
		System.out.println(car1);
		System.out.println(car2);
	}
}
