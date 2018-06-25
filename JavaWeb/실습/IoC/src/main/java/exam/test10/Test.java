package exam.test10;

import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args){
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("exam/test10/beans.xml");
		
		Tire t1 = (Tire) ctx.getBean("hankookTire");
		Tire t2 = (Tire) ctx.getBean("kumhoTire");
		Tire t3 = (Tire) ctx.getBean("hankookTire");
		
		System.out.println("t1 --> " + t1.toString());
		System.out.println("t2 --> " + t2.toString());
		System.out.println("t3 --> " + t3.toString());
		
		if(t1 != t2){
			System.out.println("t1 != t2");
		}
		
		if(t1 == t3){
			System.out.println("t1 == t3");
		}
	}
}
