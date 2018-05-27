package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.User;
import dao.UserDao;

// JSP 적용
// - 입력폼 및 오류 처리 
@WebServlet("/users/create")
public class UserCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(
				"/users/UserCreate.jsp");
		rd.forward(request, response);
	}
	
	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Connection conn = null;
		//PreparedStatement stmt = null;

		try {
			ServletContext sc = this.getServletContext();
			
			Connection conn = (Connection) sc.getAttribute("conn");
			UserDao userDao = new UserDao();
			userDao.setConnection(conn);
			
			if(userDao.insert(new User()
			        .setEmail(request.getParameter("email"))
			        .setPassword(request.getParameter("password"))
			        .setName(request.getParameter("name"))) == 1){
				response.sendRedirect("list");
				return;
			}else{
				RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
				rd.forward(request, response);
			}
			/*
			conn = (Connection) sc.getAttribute("conn");  
			stmt = conn.prepareStatement(
					"INSERT INTO users(email,password,name,created_at,updated_at)"
					+ " VALUES (?,?,?,?,?)");
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
					format(Calendar.getInstance().getTime());
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("password"));
			stmt.setString(3, request.getParameter("name"));
			stmt.setString(4, timeStamp);
			stmt.setString(5, timeStamp);
			stmt.executeUpdate();
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		} finally {
			//try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//try {if (conn != null) conn.close();} catch(Exception e) {}
		}
	}
}
