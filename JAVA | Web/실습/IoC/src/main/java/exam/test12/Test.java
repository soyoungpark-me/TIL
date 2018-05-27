package exam.test12;

import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args){
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("exam/test12/beans.xml");
		
		System.out.println("[singleton 방식(기본)]------------------------------------");
		Engine e1 = (Engine) ctx.getBean("hyundaiEngine");
		Engine e2 = (Engine) ctx.getBean("hyundaiEngine");
		
		System.out.println("e1 --> " + e1.toString());
		System.out.println("e2 --> " + e2.toString());
		
		if(e1 == e2) System.out.println("e1 == e2");
		
		System.out.println("[prototype 방식]------------------------------------");
		Engine e3 = (Engine) ctx.getBean("kiaEngine");
		Engine e4 = (Engine) ctx.getBean("kiaEngine");
		
		System.out.println("e3 --> " + e3.toString());
		System.out.println("e4 --> " + e4.toString());
		
		if(e3 != e4) System.out.println("e3 != e4");
	}
}

