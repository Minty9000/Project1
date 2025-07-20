<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Scheduler</title>
	</head>
	<body>
	
		<form action="customer-home.jsp" method="get">
    		<input type="submit" value="Back">
		</form><br/>
		
		<h2>Search Train Schedules</h2>
	
		<% List<Station> stations = (List<Station>) request.getAttribute("stations"); %>
		<form method="get" action="SearchByStationsServlet">
  			Origin: 
	    	<select name="origin_id">
	        	<% if (stations != null) {
	               for (Station s : stations) { %>
	                   <option value="<%= s.sid %>"><%= s.name %> (<%= s.city %>, <%= s.state %>)</option>
	        	<%     }
	           } %>
	    	</select><br/>
	
	    	Destination: 
	    	<select name="destination_id">
		        <% if (stations != null) {
		               for (Station s : stations) { %>
		                   <option value="<%= s.sid %>"><%= s.name %> (<%= s.city %>, <%= s.state %>)</option>
		        <%     }
		           } %>
	    	</select><br/>
	    	Date:
    		<input type="date" id="date" name="date" /><br/><br/>
	    	<input type="submit" value="Search Train Schedules" />
		</form>
		
		<h2>Train Schedules</h2>
		<table border="1">
	    <tr>
	        <th>Train ID</th>
	        <th>Train Line</th>
	        <th>Departure Time</th>
	        <th>Arrival Time</th>
	    </tr>
	    <%
	    List<Schedule> schedules = (List<Schedule>) request.getAttribute("schedules");
	    if (schedules != null) {
	        if (!schedules.isEmpty()) {
	            for (Schedule s : schedules) {
		%>
	                <tr>
					    <td><%= s.train %></td>
					    <td><%= s.trainline %></td>
					    <td><%= s.departure_time %></td>
					    <td><%= s.arrival_time %></td>
					    <td>
						    <form method="post" action="ReservationServlet" onsubmit="return confirm('Are you sure you want to reserve a ticket');">
						    	<input type="hidden" name="origin_id" value="<%= request.getAttribute("origin_id") %>" />
								<input type="hidden" name="destination_id" value="<%= request.getAttribute("destination_id") %>"/>
						      	<input type="hidden" name="schedule_id" value="<%= s.scid %>" />
						        <select name="passengerType">
						        	<option value="senior">Senior</option>
						        	<option value="adult">Adult</option>
						        	<option value="child">Child</option>
						        	<option value="disabled">Disabled</option>
						        </select>
						        Round-Trip:
						        <input type="checkbox" name="roundTrip"/>
						        <input type="submit" value="Make Reservation"/>
						    </form>
				    	</td>		    	
					</tr>
		<%
	            }
	        } else {
		%>
	            <tr><td colspan="5">No schedules found.</td></tr>
		<%
	        }
	    }
		%>
		</table><br/>
	</body>
</html>