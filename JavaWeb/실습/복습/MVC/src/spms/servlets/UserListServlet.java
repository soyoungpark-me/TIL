package spms.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.UserDaoMySql;

@SuppressWarnings("serial")
@WebServlet("/users/list")
public class UserListServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
				
		try {
			ServletContext sc = this.getServletContext();	
			UserDaoMySql userDao = (UserDaoMySql)sc.getAttribute("userDao");
			
			request.setAttribute("users", userDao.selectList());
			request.setAttribute("viewUrl", "/users/UserList.jsp");
		} catch (Exception e) {
			throw new ServletException(e);	
		}
	}
}