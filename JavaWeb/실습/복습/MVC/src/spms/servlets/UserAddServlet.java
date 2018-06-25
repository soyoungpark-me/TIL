package spms.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.UserDaoMySql;
import spms.vo.User;

import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet("/users/new")
public class UserAddServlet extends HttpServlet{
	@Override
	protected void doGet(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		request.setAttribute("viewUrl", "/users/UserForm.jsp");
	}
	
	protected void doPost(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
				
		try {
			ServletContext sc = this.getServletContext();
			UserDaoMySql userDao = (UserDaoMySql)sc.getAttribute("userDao");
			
			User user = (User)request.getAttribute("user");
			userDao.insert(user);
						
			request.setAttribute("viewUrl", "redirect:list.do");	
			
		} catch (Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}
