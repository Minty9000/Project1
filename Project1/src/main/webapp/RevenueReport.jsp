<%@ page import="java.sql.*" %>
<%@ page import="com.admin.utils.DBUtil" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Revenue Report</title>
</head>
<body>
    <h2>Revenue by Transit Line</h2>
    <table border="1">
        <tr><th>Transit Line</th><th>Total Revenue</th></tr>
        <%
        try (Connection conn = DBUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT t.transit_line, SUM(r.price) AS revenue " +
                "FROM CustomerReservation r " +
                "JOIN Train t ON r.train_id = t.train_id " +
                "GROUP BY t.transit_line " +
                "ORDER BY revenue DESC");

            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("transit_line") %></td>
            <td>$<%= rs.getDouble("revenue") %></td>
        </tr>
        <% }
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
        %>
    </table>

    <hr>

    <h2>Revenue by Customer</h2>
    <table border="1">
        <tr><th>Customer</th><th>Total Revenue</th></tr>
        <%
        try (Connection conn = DBUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT name, SUM(price) AS revenue " +
                "FROM CustomerReservation " +
                "GROUP BY name " +
                "ORDER BY revenue DESC");

            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("name") %></td>
            <td>$<%= rs.getDouble("revenue") %></td>
        </tr>
        <% }
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
        %>
    </table>
</body>
</html>