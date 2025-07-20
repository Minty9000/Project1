<h2>Register New Customer</h2>
<form method="post" action="RegisterServlet">
    Username: <input type="text" name="username" required><br>
    Password: <input type="password" name="password" required><br>
    <input type="submit" value="Register">
</form>

<% if (request.getParameter("taken") != null) { %>
    <p style="color:red;">Username already taken</p>
<% } %>