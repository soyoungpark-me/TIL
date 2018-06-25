package listeners;

import java.io.InputStream;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import context.ApplicationContext;
import controls.*;
import dao.MySqlUserDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener{
	//BasicDataSource ds;
	static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try{
			applicationContext = new ApplicationContext();
			
			String resource = "dao/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			applicationContext.addBean("sqlSessionFactory", sqlSessionFactory);
			
			ServletContext sc = event.getServletContext();
			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
			
			applicationContext.prepareObjectsByProperties(propertiesPath);
			applicationContext.prepareObjectsByAnnotation("");
			applicationContext.injectDependency();
			
		}catch(Throwable e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//try{if(ds != null) ds.close();}catch(Exception e){}
	}


}
