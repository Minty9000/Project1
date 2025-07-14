<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cs336.pkg.Trainline, com.cs336.pkg.Station, com.cs336.pkg.Stop" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		Trainline t = (Trainline) request.getAttribute("trainline");
		List<Station> stations = (List<Station>) request.getAttribute("stations");
		List<Stop> stops = (List<Stop>) request.getAttribute("stops");
		
		if (t != null) {
	%>
		<h2>Train Line Details</h2>
	    <p><strong>Name:</strong> <%= t.name %></p>
	    <p><strong>Origin:</strong> <%= t.origin.name %>, <%= t.origin.city %>, <%= t.origin.state %></p>
	    <p><strong>Destination:</strong> <%= t.destination.name %>, <%= t.destination.city %>, <%= t.destination.state %></p>
	    <p><strong>Travel Time:</strong> <%= t.travelTime %> minutes</p>
	    <p><strong>Number of Stops:</strong> <%= t.numOfStops %></p>
	    <p><strong>Fare:</strong> $<%= String.format("%.2f", t.fare) %></p>
	    
	    <h2>Stops</h2>
		<% if (stops != null && !stops.isEmpty()) { %>
		    <ul>
		    <% for (Stop stop : stops) {
		           Station station = stop.station;
		    %>
		        <li><strong><%= station.name %></strong> (<%= station.city %>, <%= station.state %>)
		        	<form method="post" action="RemoveStopServlet" style="display:inline;">
		                <input type="hidden" name="tlid" value="<%= t.tlid %>" />
		                <input type="hidden" name="sid" value="<%= station.sid %>" />
		                <input type="submit" value="Remove" onclick="return confirm('Remove this stop?');" />
		            </form>
		        </li>
		    <% } %>
		    </ul>
		<% } else { %>
		    <p>No stops available.</p>
		<% } %>
	    
	    <h2>Add Stop</h2>
	    <form method="post" action="AddStopServlet">
	    	<input type="hidden" name="tlid" value="<%= t.tlid %>" />
	    	<label for="stop">Select Station:</label>
	    	<select name="sid" id="stop">
	        <% if (stations != null) {
	               for (Station s : stations) { %>
	                   <option value="<%= s.sid %>"><%= s.name %> (<%= s.city %>, <%= s.state %>)</option>
	        <%     }
	           } %>
    		</select><br/>
    		<input type="submit" value="Add Stop">
	    </form>
	<% } else { %>
	    <p>Train line not found.</p>
	<% } %>
</body>
</html>