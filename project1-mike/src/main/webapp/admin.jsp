<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="get" action="LogoutServlet">
  		<button type="submit">Logout</button>
	</form>
    <h2>Welcome, Admin</h2>

    <h3>Customer Representative Management</h3>
    <ul>
        <li><a href="ListReps">View All Customer Reps</a></li>
        <li><a href="manageReps.jsp">Add/Edit/Delete Customer Reps</a></li>
    </ul>

    <h3>Reports</h3>
    <ul>
        <li><a href="SalesReportServlet">Monthly Sales Reports</a></li>
        <li><a href="ReservationsReport.jsp">Reservations by Transit Line/Customer</a></li>
        <li><a href="RevenueReport.jsp">Revenue by Transit Line/Customer</a></li>
        <li><a href="topcustomer.jsp">Best Customer and Top 5 Transit Lines</a></li>
    </ul>
</body>
</html>