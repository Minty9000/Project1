<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.ApplicationDB" %><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Monthly Sales Report</title>
</head>
<body>
    <h2>Monthly Sales Report</h2>

    <table border="1">
        <tr><th>Month</th><th>Total Sales</th></tr>
        <%
        try (Connection con = new ApplicationDB().getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT DATE_FORMAT(reservation_date, '%Y-%m') AS month, SUM(price) AS total_sales " +
                "FROM CustomerReservation " +
                "GROUP BY month " +
                "ORDER BY month DESC");

            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("month") %></td>
            <td>$<%= rs.getDouble("total_sales") %></td>
        </tr>
        <% }
        } catch (Exception e) {
            out.println("Error loading sales report: " + e.getMessage());
        }
        %>
    </table>
</body>
</html>