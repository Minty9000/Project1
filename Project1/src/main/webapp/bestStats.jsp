<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Best Stats</title>
</head>
<body>
    <h2>Top 5 Best Customers</h2>
<%
    List<String[]> topCustomers = (List<String[]>) request.getAttribute("topCustomers");
    if (topCustomers != null && !topCustomers.isEmpty()) {
%>
    <table border="1">
        <tr><th>Customer Name</th><th>Total Spent</th></tr>
        <% for (String[] row : topCustomers) { %>
            <tr><td><%= row[0] %></td><td><%= row[1] %></td></tr>
        <% } %>
    </table>
<% } else { %>
    <p>No customer data available.</p>
<% } %>

<h2>Top 5 Most Active Transit Lines</h2>
<%
    List<String[]> topLines = (List<String[]>) request.getAttribute("topLines");
    if (topLines != null && !topLines.isEmpty()) {
%>
    <table border="1">
        <tr><th>Transit Line</th><th>Reservation Count</th></tr>
        <% for (String[] row : topLines) { %>
            <tr><td><%= row[0] %></td><td><%= row[1] %></td></tr>
        <% } %>
    </table>
<% } else { %>
    <p>No transit line data available.</p>
<% } %>
</body>
</html>