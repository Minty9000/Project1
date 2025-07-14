<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cs336.pkg.Station" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transit Line Management</title>
</head>
<body>
	
	
	<h2>Train Stations</h2>
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
	
	<h2>Add Train Line</h2>
    <form method="post" action="TrainlineServlet">
    	Name: <input type="text" name="name" /><br/>
    	
    	Origin: 
    	<select name="origin">
        	<% if (stations != null) {
               for (Station s : stations) { %>
                   <option value="<%= s.sid %>"><%= s.name %> (<%= s.city %>, <%= s.state %>)</option>
        	<%     }
           } %>
    	</select><br/>

    	Destination: 
    	<select name="destination">
	        <% if (stations != null) {
	               for (Station s : stations) { %>
	                   <option value="<%= s.sid %>"><%= s.name %> (<%= s.city %>, <%= s.state %>)</option>
	        <%     }
	           } %>
    	</select><br/>
    	
    	Total Travel Time:
    	<input type="number" name="travelTime">
    	<br/>
    	
    	Total Fare:
    	<input type="number" name="fare">
    	<br/>
    	

    	<input type="submit" value="Add Train Line" />
	</form>
	
	<h2>Add Station</h2>
    	<form method="post" action="StationServlet">
        	name: <input type="text" name="name" /><br/>
        	city: <input type="text" name="city" /><br/>
        	state: <input type="text" name="state" /><br/>
        	<input type="submit" value="Add Station" />
    </form>
</body>
</html>