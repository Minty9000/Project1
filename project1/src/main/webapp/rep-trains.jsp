<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cs336.pkg.Train, com.cs336.pkg.Trainline, com.cs336.pkg.Schedule" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Train Management</title>
</head>
<body>

	<form action="rep-home.jsp" method="get">
    	<input type="submit" value="Back">
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
	
	<h2>Trains</h2>
	<table border="1">
    <tr>
        <th>ID</th>
    </tr>
    <%
    List<Trainline> trainlines = (List<Trainline>) request.getAttribute("trainlines");
    List<Train> trains = (List<Train>) request.getAttribute("trains");
    if (trains != null) {
        if (!trains.isEmpty()) {
            for (Train t : trains) {
	%>
                <tr>
				    <td><%= t.tid %></td>
				    <td>
					    <form method="post" action="DeleteTrainServlet" onsubmit="return confirm('Are you sure you want to delete this train line?');">
					        <input type="hidden" name="tid" value="<%= t.tid %>" />
					        <input type="submit" value="Delete" />
					    </form>
				    </td>
				</tr>
	<%
            }
        } else {
	%>
            <tr><td colspan="5">No trains found.</td></tr>
	<%
        }
    }
	%>
	</table>
	<br/>
    
    <h2>Add Train Schedule</h2>
	<form method="post" action="ScheduleServlet">
        	Train: 
        	<select name="train">
	        <% if (trains != null) {
	               for (Train t : trains) { %>
	                   <option value="<%= t.tid %>"><%= t.tid %></option>
	        <%     }
	           } %>
    		</select><br/>
        	Train Line: 
        	<select name="trainline">
	        <% if (trainlines != null) {
	               for (Trainline t : trainlines) { %>
	                   <option value="<%= t.tlid %>"><%= t.name %></option>
	        <%     }
	           } %>
    		</select><br/>
    		Departure Time:
    		<input type="datetime-local" id="departure_time" name="departure_time" /><br/>
        	<input type="submit" value="Add Train" />
    </form><br/>
    
    <h2>Add Train</h2>
	<form method="post" action="TrainServlet">
        	Train ID: <input type="text" name="tid" /><br/>
        	
        	Train Line: 
        	<select name="trainline">
	        <% if (trainlines != null) {
	               for (Trainline t : trainlines) { %>
	                   <option value="<%= t.tlid %>"><%= t.name %></option>
	        <%     }
	           } %>
    		</select><br/><br/>
        	<input type="submit" value="Add Train" />
    </form><br/>
	
</body>
</html>