package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

//프런트 컨트롤러 적용하기
@SuppressWarnings("serial")
@WebServlet("/users/update")
public class UserUpdateServlet extends HttpServlet {
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Connection conn = null;
		//Statement stmt = null;
		//ResultSet rs = null;
		try {
			ServletContext sc = this.getServletContext();
			//Connection conn = (Connection) sc.getAttribute("conn");
			//UserDao userDao = new UserDao();
			//userDao.setConnection(conn);
			
			UserDao userDao = (UserDao) sc.getAttribute("userDao");
			request.setAttribute("user", userDao.select(Integer.parseInt(request.getParameter("no"))));
			request.setAttribute("viewUrl", "/users/UserUpdate.jsp");
			/*		
			response.setContentType("text/html; charset=UTF-8");
			RequestDispatcher rd = request.getRequestDispatcher(
					"/users/UserUpdate.jsp");
			rd.forward(request, response);
			
			conn = (Connection) sc.getAttribute("conn");   
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
				"SELECT id,email,name,created_at FROM users" + 
				" WHERE id=" + request.getParameter("no"));	
			if (rs.next()) {
				request.setAttribute("user", 
					new User()
						.setId(rs.getInt("id"))
						.setEmail(rs.getString("email"))
						.setName(rs.getString("name"))
						.setCreatedAt(rs.getDate("created_at")));
			
			} else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
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
			//try {if (rs != null) rs.close();} catch(Exception e) {}
			//try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//try {if (conn != null) conn.close();} catch(Exception e) {}
		}
	}
	
	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Connection conn = null;
		//PreparedStatement stmt = null;
		try {
			ServletContext sc = this.getServletContext();
			/*
			Connection conn = (Connection) sc.getAttribute("conn");
			UserDao userDao = new UserDao();
			userDao.setConnection(conn);
			*/
			UserDao userDao = (UserDao) sc.getAttribute("userDao");
			
			User user = (User)request.getAttribute("user");
			userDao.update(user);
			
			request.setAttribute("viewUrl", "redirect:list.do");
			/*
			if(userDao.update(new User().setId(Integer.parseInt(request.getParameter("no")))
					.setEmail(request.getParameter("email")).setName(request.getParameter("name"))) == 1){
				response.sendRedirect("list");
				return;
			}else{
				RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
				rd.forward(request, response);
			}
			
			conn = (Connection) sc.getAttribute("conn");    
			stmt = conn.prepareStatement(
					"UPDATE users SET email=?,name=?,updated_at=?"
					+ " WHERE id=?");
			
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
					format(Calendar.getInstance().getTime());
			
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("name"));
			stmt.setString(3, timeStamp);
			stmt.setInt(4, Integer.parseInt(request.getParameter("no")));
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
