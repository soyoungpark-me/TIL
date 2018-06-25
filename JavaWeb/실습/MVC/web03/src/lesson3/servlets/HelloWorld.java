package lesson3.servlets;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloWorld implements Servlet{
	ServletConfig config;
	
	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init() 호출됨.");
		this.config = config;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy() 호출됨.");
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		System.out.println("getServletConfig() 호출됨.");
		return this.config;
	}

	@Override
	public String getServletInfo() {
		System.out.println("getServiceInfo() 호출됨");
		return "version = 1.0; author = soyoung;";
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("service() 호출됨.");
	}
	
}
