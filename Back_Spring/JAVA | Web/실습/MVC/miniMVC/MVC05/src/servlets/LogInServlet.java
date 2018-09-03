package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vo.User;
import dao.MySqlUserDao;

//프런트 컨트롤러 적용하기
@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//RequestDispatcher rd = request.getRequestDispatcher(
		//		"/auth/LogInForm.jsp");
		//rd.forward(request, response);
		
		request.setAttribute("viewUrl", "/auth/LoginForm.jsp");
	}
	
	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Connection conn = null;
		//PreparedStatement stmt = null;
		//ResultSet rs = null;
		
		try {
			ServletContext sc = this.getServletContext();
			//Connection conn = (Connection) sc.getAttribute("conn");
			//UserDao userDao = new UserDao();
			//userDao.setConnection(conn);
			
			MySqlUserDao userDao = (MySqlUserDao) sc.getAttribute("userDao");
			User user = userDao.exist(request.getParameter("email"), request.getParameter("password"));
			
			if(user != null){
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				
				//response.sendRedirect("../users/list");
				//return;
				request.setAttribute("viewUrl", "redirect:../users/list.do");
			}else{
				//RequestDispatcher rd = request.getRequestDispatcher(
				//		"/auth/LogInFail.jsp");
				//rd.forward(request, response);

				request.setAttribute("viewUrl", "/auth/LoginFail.jsp");
			}
			/*
			conn = (Connection) sc.getAttribute("conn");  
			stmt = conn.prepareStatement(
					"SELECT name,email FROM users"
					+ " WHERE email=? AND password=?");
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("password"));
			rs = stmt.executeQuery();
			if (rs.next()) {
				User user = new User()
						.setEmail(rs.getString("email"))
						.setName(rs.getString("name"));
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				
				response.sendRedirect("../users/list");
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(
						"/auth/LogInFail.jsp");
				rd.forward(request, response);
			}
			*/
		} catch (Exception e) {
			throw new ServletException(e);
			
		} finally {
			//try {if (rs != null) rs.close();} catch (Exception e) {}
			//try {if (stmt != null) stmt.close();} catch (Exception e) {}
		}
	}
}
