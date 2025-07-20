<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.cs336.pkg.Reservation" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Reservations</title>
</head>
<body>
    <h2>Current Reservations</h2>
    <%
        List<Reservation> currentReservations = (List<Reservation>) request.getAttribute("reservations");
        if (currentReservations == null || currentReservations.isEmpty()) {
    %>
        <p>No current reservations found.</p>
    <%
        } else {
    %>
        <table border="1">
            <tr>
                <th>Train Name</th>
                <th>Origin</th>
                <th>Destination</th>
                <th>Departure</th>
                <th>Arrival</th>
                <th>Passenger Type</th>
                <th>Round Trip</th>
                <th>Fare</th>
                <th>Status</th>
            </tr>
            <% for (Reservation res : currentReservations) { %>
            <tr>
                <td><%= res.rid %></td>
                <td><%= res.train %></td>
                <td><%= res.trainline %></td>
                <td><%= res.origin %></td>
                <td><%= res.destination %></td>
                <td><%= res.departure_time %></td>
                <td><%= res.arrival_time %></td>
                <td><%= res.fare %></td>
                <td><%= res.reservation_date %></td>
            </tr>
            <% } %>
        </table>
    <%
        }
    %>
</body>
</html>

