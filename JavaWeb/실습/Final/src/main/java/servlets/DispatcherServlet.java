package servlets;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controls.Controller;
import listeners.ContextLoaderListener;
import bind.DataBinding;
import bind.ServletRequestDataBinder;
import vo.User;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();
		
		try{
			//ServletContext sc = this.getServletContext();
			ApplicationContext ctx = ContextLoaderListener.getApplicationContext();
			
			HashMap<String, Object> model = new HashMap<String, Object>();
			//model.put("userDao", sc.getAttribute("userDao"));
			model.put("session", request.getSession());
			
			//String pageControllerPath = null;
			System.out.println("패스는... : " + servletPath);
			//Controller pageController = (Controller) sc.getAttribute(servletPath);
			Controller pageController = (Controller) ctx.getBean(servletPath);
			
			if(pageController == null){
				throw new Exception("요청한 서비스를 찾을 수 없습니다.");
			}
			
			if(pageController instanceof DataBinding){
				preparedRequestData(request, model, (DataBinding)pageController);
			}
			
			/*
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
			*/
			
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
	
	private void preparedRequestData(HttpServletRequest request, 
			HashMap<String, Object> model, DataBinding dataBinding) throws Exception{
		Object[] dataBinders = dataBinding.getDataBinders();
		String dataName = null;
		Class<?> dataType = null;
		Object dataObj = null;
		
		for(int i=0; i<dataBinders.length; i+=2){
			dataName = (String)dataBinders[i];
			dataType = (Class<?>)dataBinders[i+1];
			dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
			model.put(dataName, dataObj);
		}
	}
}
