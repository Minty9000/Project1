<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cs336.pkg.Station" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>welcome rep</p>
	
	<form method="get" action="StationServlet">
  		<button type="submit">Show Stations</button>
	</form>
	
	<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>City</th>
        <th>State</th>
    </tr>
    <%
    List<Station> stations = (List<Station>) request.getAttribute("stations");
    if (stations != null) {
        if (!stations.isEmpty()) {
            for (Station s : stations) {
	%>
                <tr>
	            	<td><%= s.sid %></td>
	                <td><%= s.name %></td>
	                <td><%= s.city %></td>
	                <td><%= s.state %></td>
	       	        <td>
	   	            	<form method="post" action="DeleteStationServlet">
	       					<input type="hidden" name="action" value="delete">
	       					<input type="hidden" name="sid" value="<%= s.sid %>">
	       					<input type="submit" value="Delete">
	   					</form>
                   </td>
	            </tr>
	<%
            }
        } else {
	%>
            <tr><td colspan="5">No stations found.</td></tr>
	<%
        }
    }
	%>
	</table>
	
	<h2>Add Station</h2>
    	<form method="post" action="StationServlet">
        	name: <input type="text" name="name" /><br/>
        	city: <input type="text" name="city" /><br/>
        	state: <input type="text" name="state" /><br/>
        	<input type="submit" value="Add Station" />
    </form>
</body>
</html>