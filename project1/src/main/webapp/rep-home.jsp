<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
	<a href="StationServlet">Go to Station Management Page</a><br/>
	<a href="TrainlineServlet">Go to Train Line Management Page</a>
</body>
</html>