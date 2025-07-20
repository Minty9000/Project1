<%@ page import="com.cs336.pkg.ApplicationDB" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reservations Report</title>
</head>
<body>
    <h2>Reservations by Transit Line</h2>
    <table border="1">
        <tr><th>Transit Line</th><th>Customer</th><th>Train ID</th><th>Reservation #</th><th>Fare</th></tr>
        <%
        try (Connection con = new ApplicationDB().getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT tl.name AS transit_line, u.username, s.train AS train_id, r.rid AS reservation_number, r.fare " +
                "FROM reservations r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN schedules s ON r.schedule_id = s.scid " +
                "JOIN trainlines tl ON s.trainline = tl.tlid " +
                "ORDER BY tl.name, u.username"
            );

            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("transit_line") %></td>
            <td><%= rs.getString("username") %></td>
            <td><%= rs.getString("train_id") %></td>
            <td><%= rs.getInt("reservation_number") %></td>
            <td>$<%= rs.getDouble("fare") %></td>
        </tr>
        <% }
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
        %>
    </table>

    <hr>

    <h2>Reservations by Customer Name</h2>
    <table border="1">
        <tr><th>Customer</th><th>Transit Line</th><th>Train ID</th><th>Reservation #</th><th>Fare</th></tr>
        <%
        try (Connection con = new ApplicationDB().getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT u.username, tl.name AS transit_line, s.train AS train_id, r.rid AS reservation_number, r.fare " +
                "FROM reservations r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN schedules s ON r.schedule_id = s.scid " +
                "JOIN trainlines tl ON s.trainline = tl.tlid " +
                "ORDER BY u.username, tl.name"
            );

            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("username") %></td>
            <td><%= rs.getString("transit_line") %></td>
            <td><%= rs.getString("train_id") %></td>
            <td><%= rs.getInt("reservation_number") %></td>
            <td>$<%= rs.getDouble("fare") %></td>
        </tr>
        <% }
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
        %>
    </table>
</body>
</html>