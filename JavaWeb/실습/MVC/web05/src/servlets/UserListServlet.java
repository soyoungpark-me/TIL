package servlets;

import vo.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/users/list")
public class UserListServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			ServletContext sc = this.getServletContext();
			//Class.forName(sc.getInitParameter("driver"));
			//conn = DriverManager.getConnection(
			//		sc.getInitParameter("url"), sc.getInitParameter("username"), sc.getInitParameter("password"));
			
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id,name,email,created_at FROM users ORDER BY id ASC");
			
			response.setContentType("text/html; charset=UTF-8");
			
			ArrayList<User> users = new ArrayList<User>();
			
			// 데이터베이스에서 회원 정보를 담아와 User에 담고, User 객체를 ArrayList에 추가
			while(rs.next()){
				users.add(new User().setId(rs.getInt("id")).setName(rs.getString("name"))
					  .setEmail(rs.getString("email")).setCreatedAt(rs.getDate("created_at")));
			}
			
			// request에 ArrayList 보관
			request.setAttribute("users", users);
			
			// JSP에 출력 위임
			RequestDispatcher rd = request.getRequestDispatcher("/users/UserList.jsp");
			rd.include(request, response);
			
		}catch(Exception e){
			//throw new ServletException(e);
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}finally{
			try{if(rs != null) rs.close();} catch(Exception e){}
			try{if(stmt != null) stmt.close();} catch(Exception e){}
			//try{if(conn != null) conn.close();} catch(Exception e){}
		}
	}
}
