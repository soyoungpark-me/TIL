<%@page import="spms.vo.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="user"
	scope="session"
	class="spms.vo.User"/>
	   
<div style="background-color:#00008b;color:#ffffff;height:20px;padding: 5px;">
	SPMS(Simple Project Management System)
	<span style="float:right">
		<% if (user.getEmail() != null) { %>		
			<%=user.getName() %>
			<a href="<%=request.getContextPath()%>/auth/logout.do" style="color:white">[로그아웃]</a>		
		<% } else { %>
			<a href="<%=request.getContextPath()%>/auth/login.do" style="color:white">[로그인]</a>
		<% } %>
	</span>
</div>