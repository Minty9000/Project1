<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Train Scheduler</title>
	</head>
	
	<body>

		<% out.println("Train Scheduler"); %> <!-- output the same thing, but using jsp programming -->					  
		<br>
		
		<!-- Error message for unsuccessful login -->
		<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
		
		<!-- Login form -->
		<h2>Login</h2>
    	<form method="post" action="LoginServlet">
        	Username: <input type="text" name="username" /><br/>
        	Password: <input type="password" name="password" /><br/>
        	<input type="submit" value="Login" />
    	</form>
    	
    	<!-- Display error if login unsuccessful -->
    	<% if (errorMessage != null) { %>
    		<p style="color:red;"><%= errorMessage %></p>
		<% } %>
		
		<h2>Create Account</h2>
		<form method="post" action="RegisterServlet">
        	Username: <input type="text" name="username" /><br/>
        	Password: <input type="password" name="password" /><br/>
        	Email: <input type="text" name="email" /><br/>
        	Phone: <input type="text" name="phone" /><br/>
        	<input type="submit" value="Register" />
    	</form>
		
	</body>
</html>