package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;

//프런트 컨트롤러 적용하기
@WebServlet("/users/delete")
public class UserRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Connection conn = null;
		//Statement stmt = null;

		try {
			ServletContext sc = this.getServletContext();
			/*
			conn = (Connection) sc.getAttribute("conn");   
			stmt = conn.createStatement();
			stmt.executeUpdate(
					"DELETE FROM users WHERE id=" + 
					request.getParameter("no"));
			*/
			//Connection conn = (Connection) sc.getAttribute("conn");
			//UserDao userDao = new UserDao();
			//userDao.setConnection(conn);
			
			UserDao userDao = (UserDao) sc.getAttribute("userDao");
			userDao.delete(Integer.parseInt(request.getParameter("no")));
					
			request.setAttribute("viewUrl", "redirect:list.do");
			/*
			if(userDao.delete(Integer.parseInt(request.getParameter("no"))) == 1){
				response.sendRedirect("list");
			}else{
				RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
				rd.forward(request, response);
			}
			*/
			
			
		} catch (Exception e) {
			throw new ServletException(e);
			/*
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			*/
			
		} finally {
			//try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//try {if (conn != null) conn.close();} catch(Exception e) {}
		}

	}
}
