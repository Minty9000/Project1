<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Train Scheduler</title>
	</head>
	
	<body>

		<% out.println("Train Scheduler"); %> <!-- output the same thing, but using jsp programming -->					  
		<br>
		
		<!-- Error message for unsuccessful login -->
		<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
		
		<!-- Login form -->
		<h2>Login</h2>
    	<form method="post" action="LoginServlet">
        	Username: <input type="text" name="username" /><br/>
        	Password: <input type="password" name="password" /><br/>
        	<input type="submit" value="Login" />
    	</form>
    	
    	<!-- Display error if login unsuccessful -->
    	<% if (errorMessage != null) { %>
    		<p style="color:red;"><%= errorMessage %></p>
		<% } %>
		
	</body>
</html>