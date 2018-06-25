package listeners;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import dao.UserDao;
import util.DBConnectionPool;

@WebListener
public class ContextLoaderListener implements ServletContextListener{
	//Connection conn;
	DBConnectionPool connPool;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try{
			ServletContext sc = event.getServletContext();
			
			/*
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"), sc.getInitParameter("username"), sc.getInitParameter("password"));
			
			UserDao userDao = new UserDao();
			userDao.setConnection(conn);
			*/
			
			connPool = new DBConnectionPool(
					sc.getInitParameter("driver"),
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			
			UserDao userDao = new UserDao();
			userDao.setDbConnectionPool(connPool);
			
			sc.setAttribute("userDao", userDao);
			System.out.println("userDao 생성");
		}catch(Throwable e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		/*
		try{
			conn.close();
		}catch(Exception e){}	
		*/
		connPool.closeAll();
	}


}
