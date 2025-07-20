
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cs336.pkg.ApplicationDB" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Manage Customer Representatives</title>
</head>
<body>

<h2>Customer Representative Management</h2>

<form action="admin.jsp" method="get">
    <input type="submit" value="Back">
</form>

<%
    String editIdParam = request.getParameter("edit_id");
    boolean isEditing = (editIdParam != null);
    String username = "", email = "", phone = "";
    int editId = -1;

    if (isEditing) {
    	try (Connection con = new ApplicationDB().getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM Users WHERE user_id = ? AND role = 'rep'");
            stmt.setInt(1, Integer.parseInt(editIdParam));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                editId = rs.getInt("user_id");
                username = rs.getString("username");
                email = rs.getString("email");
                phone = rs.getString("phone");
            }
        } catch (Exception e) {
            out.println("Error loading rep: " + e.getMessage());
        }
    }
%>

<h3><%= isEditing ? "Edit Representative" : "Add New Representative" %></h3>
<form action="<%= isEditing ? "UpdateRepServlet" : "AddRepServlet" %>" method="post">
    <% if (isEditing) { %>
        <input type="hidden" name="user_id" value="<%= editId %>">
    <% } %>
    Username: <input type="text" name="username" value="<%= username %>" required><br>
    Email: <input type="email" name="email" value="<%= email %>" required><br>
    Phone: <input type="text" name="phone" value="<%= phone %>" required><br>
    Password: <input type="password" name="password" <%= isEditing ? "" : "required" %>><br>
    <input type="submit" value="<%= isEditing ? "Update" : "Add" %>">
</form>

<hr>

<h3>Current Representatives</h3>
<table border="1">
<tr><th>ID</th><th>Username</th><th>Email</th><th>Phone</th><th>Actions</th></tr>

<%
try (Connection con = new ApplicationDB().getConnection()) {
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE role = 'rep'");

    while (rs.next()) {
%>
<tr>
    <td><%= rs.getInt("user_id") %></td>
    <td><%= rs.getString("username") %></td>
    <td><%= rs.getString("email") %></td>
    <td><%= rs.getString("phone") %></td>
    <td>
        <form action="manageReps.jsp" method="get" style="display:inline;">
            <input type="hidden" name="edit_id" value="<%= rs.getInt("user_id") %>">
            <input type="submit" value="Edit">
        </form>
        <form action="DeleteRepServlet" method="post" style="display:inline;">
            <input type="hidden" name="user_id" value="<%= rs.getInt("user_id") %>">
            <input type="submit" value="Delete">
        </form>
    </td>
</tr>
<%
    }
} catch (Exception e) {
    out.println("Error loading reps: " + e.getMessage());
}
%>
</table>

</body>
</html>