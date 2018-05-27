package spms.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.dao.UserDaoMySql;
import spms.vo.User;

@SuppressWarnings("serial")
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		request.setAttribute("viewUrl", "/auth/LoginForm.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
				
		try {
			ServletContext sc = this.getServletContext();
			UserDaoMySql userDao = (UserDaoMySql)sc.getAttribute("userDao");
			
			User user = userDao.exist(request.getParameter("email"), 
				request.getParameter("password")); 
			
			if (user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				
				request.setAttribute("viewUrl", "redirect:../users/list.do");
			} else {
				request.setAttribute("viewUrl", "/auth/LoginFail.jsp");
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
