<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="jakarta.servlet.http.*,jakarta.servlet.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Scheduler</title>
	</head>
	<body>
		<form method="get" action="LogoutServlet">
  			<button type="submit">Logout</button>
		</form>
		
		<%
	    	String username = (session != null) ? (String) session.getAttribute("username") : null;

	    	if (username == null) {
	        	response.sendRedirect("login.jsp");
	        	return;
	    	}
		%>
		<h2>Welcome, <%= username %>!</h2>
		
		<a href=ReservationSearchServlet>Make Reservation</a><br>
        <a href=ViewReservationsServlet>View My Reservations</a><br>
        
	</body>
</html>