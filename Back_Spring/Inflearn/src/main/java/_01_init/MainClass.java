package _01_init;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {
    public static void main(String[] args) {
//        TransportationWalk transportationWalk = new TransportationWalk();
//        transportationWalk.move();

        GenericXmlApplicationContext ctx =
                new GenericXmlApplicationContext("classpath:applicationContext.xml");

        TransportationWalk transportationWalk = ctx.getBean("tWalk", TransportationWalk.class);
        System.out.println(transportationWalk.toString());
        transportationWalk.move();

        TransportationWalk transportationWalk2 = ctx.getBean("tWalk", TransportationWalk.class);
        System.out.println(transportationWalk2.toString());

        ctx.close();
    }
}
