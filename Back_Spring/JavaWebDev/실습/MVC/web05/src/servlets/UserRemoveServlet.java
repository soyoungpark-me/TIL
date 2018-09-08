package servlets;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
@WebServlet("/users/delete")

public class UserRemoveServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		
		try{
			ServletContext sc = this.getServletContext();

			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.createStatement();
			stmt.executeUpdate("DELETE from users where id = " + Integer.parseInt(request.getParameter("no")));
			
			response.sendRedirect("list");
		
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{if(stmt != null) stmt.close();} catch(Exception e){}
		}
		
	}
}