package listeners;

import java.io.InputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebListener
public class ContextLoaderListener implements ServletContextListener{
	//BasicDataSource ds;
	static ClassPathXmlApplicationContext applicationContext;
	
	public static ClassPathXmlApplicationContext getApplicationContext(){
		return applicationContext;
	}
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try{
			applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		}catch(Throwable e){
			e.printStackTrace();
		}		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//try{if(ds != null) ds.close();}catch(Exception e){}
	}
}
