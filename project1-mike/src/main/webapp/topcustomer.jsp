<%@ page import="java.sql.*" %>
<%@ page import="com.cs336.pkg.ApplicationDB" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Best Customer & Top Transit Lines</title>
</head>
<body>
    <h2>Best Customer</h2>
    <table border="1">
        <tr><th>Username</th><th>Total Spent</th></tr>
        <%
        try (Connection con = new ApplicationDB().getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT u.username, SUM(r.fare) AS total_spent " +
                "FROM reservations r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "GROUP BY u.username " +
                "ORDER BY total_spent DESC LIMIT 1"
            );

            if (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("username") %></td>
            <td>$<%= rs.getDouble("total_spent") %></td>
        </tr>
        <%
            } else {
                out.println("<tr><td colspan='2'>No data available</td></tr>");
            }
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
        %>
    </table>

    <hr>

    <h2>Top 5 Transit Lines by Revenue</h2>
    <table border="1">
        <tr><th>Transit Line</th><th>Total Revenue</th></tr>
        <%
        try (Connection con = new ApplicationDB().getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT tl.name AS transit_line, SUM(r.fare) AS revenue " +
                "FROM reservations r " +
                "JOIN schedules s ON r.schedule_id = s.scid " +
                "JOIN trainlines tl ON s.trainline = tl.tlid " +
                "GROUP BY tl.name " +
                "ORDER BY revenue DESC " +
                "LIMIT 5"
            );

            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("transit_line") %></td>
            <td>$<%= rs.getDouble("revenue") %></td>
        </tr>
        <%
            }
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
        %>
    </table>
</body>
</html>