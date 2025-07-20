<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cs336.pkg.Trainline, com.cs336.pkg.Station, com.cs336.pkg.Stop" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Trainline</title>
</head>
<body>
	<form action="TrainlineServlet" method="get">
    	<input type="submit" value="Back">
	</form>
	
	<%
		Trainline t = (Trainline) request.getAttribute("trainline");
		List<Station> stations = (List<Station>) request.getAttribute("stations");
		List<Stop> stops = (List<Stop>) request.getAttribute("stops");
		
		if (t != null) {
	%>
		<h2>Edit Train Line Details</h2>
		<form method="post" action="UpdateTrainlineServlet">
		    <input type="hidden" name="tlid" value="<%= t.tlid %>" />
		
		    <label><strong>Name:</strong></label>
		    <input type="text" name="name" value="<%= t.name %>" required><br/>
		
		    <label><strong>Origin:</strong></label>
		    <select name="origin" required>
		        <% if (stations != null) {
		            for (Station s : stations) { 
		                String selected = (t.origin.sid == s.sid) ? "selected" : "";
		        %>
		            <option value="<%= s.sid %>" <%= selected %>><%= s.name %> (<%= s.city %>, <%= s.state %>)</option>
		        <% }} %>
		    </select><br/>
		
		    <label><strong>Destination:</strong></label>
		    <select name="destination" required>
		        <% if (stations != null) {
		            for (Station s : stations) { 
		                String selected = (t.destination.sid == s.sid) ? "selected" : "";
		        %>
		            <option value="<%= s.sid %>" <%= selected %>><%= s.name %> (<%= s.city %>, <%= s.state %>)</option>
		        <% }} %>
		    </select><br/>
		
		    <label><strong>Travel Time (min):</strong></label>
		    <input type="number" name="travel_time" value="<%= t.travel_time %>" min="0" required><br/>
		
		    <label><strong>Fare ($):</strong></label>
		    <input type="number" step="0.01" name="fare" value="<%= t.fare %>" min="0" required><br/><br/>
		
		    <input type="submit" value="Update Train Line">
		</form>
	    
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