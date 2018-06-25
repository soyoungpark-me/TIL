package web04;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users/list")

public class UserListServlet2 extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			/* 컨텍스트 매개변수로 정의
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/mydb",
					"study",
					"1111");
			*/
			
			ServletContext sc = this.getServletContext();

			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"), sc.getInitParameter("username"), sc.getInitParameter("password"));
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users");
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원목록</h1>");
			while(rs.next()){
				out.println(
						rs.getInt("id") + ", " +
						"<a href='update?no=" + rs.getInt("id") + "'>" +
						rs.getString("name") + "<a>, " +
						rs.getString("email") + ", " +
						rs.getDate("created_at") + 
						"<a href='delete?no=" + rs.getInt("id") + "'>[삭제]</a>" + "<br>"
				);
			}
			out.println("<br>");
			out.println("<a href='new'>가입하기</a>");
			out.println("</body></html>");
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{if (rs != null) rs.close();} catch(Exception e){}
			try{if (stmt != null) stmt.close();} catch(Exception e){}
			try{if (conn != null) conn.close();} catch(Exception e){}
		}
	}

}
