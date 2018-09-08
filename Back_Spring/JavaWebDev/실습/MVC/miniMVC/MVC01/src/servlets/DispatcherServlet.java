package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.User;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();
		
		try{
			String pageControllerPath = null;
			
			if("/users/list.do".equals(servletPath)){
				pageControllerPath = "/users/list";
			}else if("/users/create.do".equals(servletPath)){
				pageControllerPath = "/users/create";
				
				if(request.getParameter("email") != null){
					request.setAttribute("user", new User().setEmail(request.getParameter("email"))
							.setPassword(request.getParameter("password")).setName(request.getParameter("name")));
				}
			}else if("/users/update.do".equals(servletPath)){
				pageControllerPath = "/users/update";
				
				if(request.getParameter("email") != null){
					request.setAttribute("user", new User().setId(Integer.parseInt(request.getParameter("no")))
							.setEmail(request.getParameter("email")).setName(request.getParameter("name")));
				}
			}else if("/users/delete.do".equals(servletPath)){
				pageControllerPath = "/users/delete";
			}else if("/auth/login.do".equals(servletPath)){
				pageControllerPath = "/auth/login";
			}else if("/auth/logout.do".equals(servletPath)){
				pageControllerPath = "/auth/logout";
			}
			
			RequestDispatcher rd = request.getRequestDispatcher(pageControllerPath);
			rd.include(request, response);
			
			String viewUrl = (String) request.getAttribute("viewUrl");
			if(viewUrl.startsWith("redirect:")){
				response.sendRedirect(viewUrl.substring(9));
				return;
			}else{
				rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}
