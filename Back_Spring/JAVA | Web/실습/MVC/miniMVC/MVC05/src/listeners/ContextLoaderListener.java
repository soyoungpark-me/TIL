package listeners;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

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
			ServletContext sc = event.getServletContext();
			
			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
			applicationContext = new ApplicationContext(propertiesPath);
			/*
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/mydb");
			
			
			ds = new BasicDataSource();
			ds.setDriverClassName(sc.getInitParameter("driver"));
			ds.setUrl(sc.getInitParameter("url"));
			ds.setUsername(sc.getInitParameter("username"));
			ds.setPassword(sc.getInitParameter("password"));
			
			
			MySqlUserDao userDao = new MySqlUserDao();
			userDao.setDataSource(ds);
			
			//sc.setAttribute("userDao", userDao);
			System.out.println("userDao 생성" + userDao);
			
			sc.setAttribute("/auth/logout.do", new LogoutController());
			sc.setAttribute("/auth/login.do", new LoginController().setUserDao(userDao));
			sc.setAttribute("/users/list.do", new UserListController().setUserDao(userDao));
			sc.setAttribute("/users/create.do", new UserCreateController().setUserDao(userDao));
			sc.setAttribute("/users/update.do", new UserUpdateController().setUserDao(userDao));
			sc.setAttribute("/users/delete.do", new UserRemoveController().setUserDao(userDao));
			System.out.println("userDao 주입");
			*/
		}catch(Throwable e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//try{if(ds != null) ds.close();}catch(Exception e){}
	}


}
