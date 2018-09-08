package servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controls.Controller;
import controls.LoginController;
import controls.LogoutController;
import controls.UserCreateController;
import controls.UserListController;
import controls.UserRemoveController;
import controls.UserUpdateController;
import vo.User;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();
		
		try{
			ServletContext sc = this.getServletContext();
			
			HashMap<String, Object> model = new HashMap<String, Object>();
			//model.put("userDao", sc.getAttribute("userDao"));
			model.put("session", request.getSession());
			
			//String pageControllerPath = null;
			Controller pageController = (Controller) sc.getAttribute(servletPath);
			
			if("/users/list.do".equals(servletPath)){
			}else if("/users/create.do".equals(servletPath)){				
				if(request.getParameter("email") != null){
					model.put("user", new User().setEmail(request.getParameter("email"))
							.setPassword(request.getParameter("password")).setName(request.getParameter("name")));
				}
			}else if("/users/update.do".equals(servletPath)){
				if(request.getParameter("email") != null){
					model.put("user", new User().setId(Integer.parseInt(request.getParameter("no")))
							.setEmail(request.getParameter("email")).setName(request.getParameter("name")));
				}else{
					model.put("no", new Integer(request.getParameter("no")));
				}
			}else if("/users/delete.do".equals(servletPath)){
				model.put("no", new Integer(request.getParameter("no")));
			}else if("/auth/login.do".equals(servletPath)){
				if (request.getParameter("email") != null) {
		          model.put("loginInfo", new User().setEmail(request.getParameter("email"))
		            .setPassword(request.getParameter("password")));
				}
			}else if("/auth/logout.do".equals(servletPath)){
			}
			
			//RequestDispatcher rd = request.getRequestDispatcher(pageControllerPath);
			//rd.include(request, response);
			
			String viewUrl = pageController.execute(model);
			
			for(String key:model.keySet()){
				request.setAttribute(key, model.get(key));
			}
			
			if(viewUrl.startsWith("redirect:")){
				response.sendRedirect(viewUrl.substring(9));
				return;
			}else{
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
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
