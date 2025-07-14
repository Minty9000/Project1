<%@ page import="java.sql.*" %>
<%@ page import="com.admin.utils.DBUtil" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reservations Report</title>
</head>
<body>
    <h2>Reservations by Transit Line</h2>
    <table border="1">
        <tr><th>Transit Line</th><th>Customer</th><th>Train ID</th><th>Reservation #</th><th>Price</th></tr>
        <%
        try (Connection conn = DBUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT t.transit_line, r.name, r.train_id, r.reservation_number, r.price " +
                "FROM CustomerReservation r " +
                "JOIN Train t ON r.train_id = t.train_id " +
                "ORDER BY t.transit_line, r.name");

            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("transit_line") %></td>
            <td><%= rs.getString("name") %></td>
            <td><%= rs.getInt("train_id") %></td>
            <td><%= rs.getInt("reservation_number") %></td>
            <td>$<%= rs.getDouble("price") %></td>
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
        <tr><th>Customer</th><th>Transit Line</th><th>Train ID</th><th>Reservation #</th><th>Price</th></tr>
        <%
        try (Connection conn = DBUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT r.name, t.transit_line, r.train_id, r.reservation_number, r.price " +
                "FROM CustomerReservation r " +
                "JOIN Train t ON r.train_id = t.train_id " +
                "ORDER BY r.name, t.transit_line");

            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("name") %></td>
            <td><%= rs.getString("transit_line") %></td>
            <td><%= rs.getInt("train_id") %></td>
            <td><%= rs.getInt("reservation_number") %></td>
            <td>$<%= rs.getDouble("price") %></td>
        </tr>
        <% }
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
        %>
    </table>
</body>
</html>