package spms.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.UserDaoMySql;
import spms.vo.User;

@SuppressWarnings("serial")
@WebServlet("/users/update")
public class UserUpdateServlet extends HttpServlet {
	@Override
	protected void doGet(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
				
		try {
			ServletContext sc = this.getServletContext();
			UserDaoMySql userDao = (UserDaoMySql)sc.getAttribute("userDao");
			
			User user = userDao.selectOne(Integer.parseInt(request.getParameter("no")));
			request.setAttribute("user", user);
			request.setAttribute("viewUrl", "/users/UserUpdateForm.jsp");
			
		} catch (Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);			
		}
	}
	
	@Override
	protected void doPost(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		try {
			ServletContext sc = this.getServletContext();
			UserDaoMySql userDao = (UserDaoMySql)sc.getAttribute("userDao");
			
			User user = (User)request.getAttribute("user");
			userDao.update(user);
				
			request.setAttribute("viewUrl", "redirect:list.do");
			
		} catch (Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		}
	}
}
