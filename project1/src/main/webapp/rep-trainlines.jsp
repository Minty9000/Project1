<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cs336.pkg.Trainline" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Train Lines</h2>
	<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>origin</th>
        <th>destination</th>
        <th>travel time</th>
        <th># of stops</th>
        <th>fare</th>
    </tr>
    <%
    List<Trainline> trainlines = (List<Trainline>) request.getAttribute("trainlines");
    if (trainlines != null) {
        if (!trainlines.isEmpty()) {
            for (Trainline t : trainlines) {
	%>
                <tr>
				    <td><%= t.tlid %></td>
				    <td><%= t.name %></td>
				    <td><%= t.origin.name %> (<%= t.origin.city %>, <%= t.origin.state %>)</td>
				    <td><%= t.destination.name %> (<%= t.destination.city %>, <%= t.destination.state %>)</td>
				    <td><%= t.travelTime %></td>
				    <td><%= t.numOfStops %></td>
				    <td><%= t.fare %></td>
				    <td>
				        <a href="EditTrainlineServlet?tlid=<%= t.tlid %>">Edit</a>
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
</body>
</html>