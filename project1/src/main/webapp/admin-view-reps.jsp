<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cs336.pkg.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Station Management</title>
</head>
<body>
	
	<form action="admin.jsp" method="get">
    	<input type="submit" value="Back">
	</form>
	
	<h2>Customer Representatives</h2>
	<table border="1">
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Password</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Role</th>
    </tr>
    <%
    List<User> reps = (List<User>) request.getAttribute("reps");
    if (reps != null) {
        if (!reps.isEmpty()) {
            for (User u : reps) {
	%>
                <tr>
	            	<td><%= u.user_id %></td>
	                <td><%= u.username %></td>
	                <td><%= u.password %></td>
	                <td><%= u.email %></td>
	                <td><%= u.phone %></td>
	                <td><%= u.role %></td>
	            </tr>
	<%
            }
        } else {
	%>
            <tr><td colspan="5">No reps found.</td></tr>
	<%
        }
    }
	%>
	</table>
</body>
</html>