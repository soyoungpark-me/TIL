<%@ page import="vo.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="user" scope="session" class="vo.User"/>
<%
	//User user = (User)session.getAttribute("user");
%>
<div style="background-color: #00008b; color: #ffffff; height: 20px; padding: 5px;">
	SPMS(Simple Project Management System)
	<% if(user.getEmail() != null) {%>
	<span style="float:right"><%=user.getName()%>
		<a style="color:white;"href="<%=request.getContextPath()%>/auth/logout">로그아웃</a>
	</span>
	<% }else{ %>
	<span style="float:right">
		<a style="color:white;"href="<%=request.getContextPath()%>/auth/login">로그인</a>
	</span>
	<% }  %>
</div>
