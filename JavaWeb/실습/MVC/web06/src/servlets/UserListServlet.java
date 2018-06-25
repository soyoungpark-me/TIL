package servlets;

import java.io.IOException;
import java.sql.Connection;
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

import vo.User;

// ServletContext에 보관된 Connection 객체 사용  
@WebServlet("/users/list")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn"); 
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT id,name,email,created_at" + 
					" FROM users" +
					" ORDER BY id ASC");
			
			response.setContentType("text/html; charset=UTF-8");
			ArrayList<User> users = new ArrayList<User>();
			
			// 데이터베이스에서 회원 정보를 가져와 Member에 담는다.
			// 그리고 Member객체를 ArrayList에 추가한다.
			while(rs.next()) {
				users.add(new User()
							.setId(rs.getInt("id"))
							.setName(rs.getString("name"))
							.setEmail(rs.getString("email"))
							.setCreatedAt(rs.getDate("created_at"))	);
			}
			
			// request에 회원 목록 데이터 보관한다.
			request.setAttribute("users", users);
			
			// JSP로 출력을 위임한다.
			RequestDispatcher rd = request.getRequestDispatcher(
					"/users/UserList.jsp");
			rd.include(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//try {if (conn != null) conn.close();} catch(Exception e) {}
		}

	}
}
