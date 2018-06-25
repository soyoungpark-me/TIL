package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;

import vo.User;

@WebServlet("/users/update")
@SuppressWarnings("serial")
public class UserUpdateServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			ServletContext sc = this.getServletContext();
			
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select * from users where id = " + Integer.parseInt(request.getParameter("no")));
			if(rs.next()){
				User user = new User().setId(rs.getInt("id")).setEmail(rs.getString("email"))
						.setName(rs.getString("name")).setCreatedAt(rs.getDate("created_at"));
				
				request.setAttribute("user", user);

				// JSP에 출력 위임
				RequestDispatcher rd = request.getRequestDispatcher("/users/UserUpdate.jsp");
				rd.include(request, response);
			}
			/*
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원정보</title></head>");
			out.println("<body><h1>회원정보</h1>");
			out.println("<form action='update' method='post'>");
			out.println("번호: <input type='text' name='no' value='" +
				request.getParameter("no") + "' readonly><br>");
			out.println("이름: <input type='text' name='name'" +
				" value='" + rs.getString("name")  + "'><br>");
			out.println("이메일: <input type='text' name='email'" +
				" value='" + rs.getString("email")  + "'><br>");
			out.println("가입일: " + rs.getDate("created_at") + "<br>");
			out.println("<input type='submit' value='저장'>");
			out.println("<input type='button' value='취소'" + 
				" onclick='location.href=\"list\"'>");
			out.println("<input type='button' value='삭제'" + 
					" onclick='location.href=\"delete?no=" + request.getParameter("no") + "\"'>");
			out.println("</form>");
			out.println("</body></html>");
			
			*/
			
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{if(rs != null) rs.close();} catch(Exception e){}
			try{if(rs != null) stmt.close();} catch(Exception e){}
			try{if(rs != null) conn.close();} catch(Exception e){}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		// request.setCharacterEncoding("UTF-8");
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try{
			ServletContext sc = this.getServletContext();
			
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(sc.getInitParameter("url"),
					sc.getInitParameter("username"), sc.getInitParameter("password"));
			stmt = (PreparedStatement) conn.prepareStatement("UPDATE users SET email=?, name=?, updated_at=? WHERE id = ?");
			
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
					format(Calendar.getInstance().getTime());
			
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("name"));
			stmt.setString(3, timeStamp);
			stmt.setInt(4, Integer.parseInt(request.getParameter("no")));
			
			stmt.executeUpdate();
			response.sendRedirect("list");
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{if(stmt != null) stmt.close();} catch(Exception e){}
			try{if(conn != null) conn.close();} catch(Exception e){}
		}
	}
}