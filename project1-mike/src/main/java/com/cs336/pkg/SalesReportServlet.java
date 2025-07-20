package com.cs336.pkg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/SalesReportServlet")
public class SalesReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        List<String[]> sales = new ArrayList<>();

        try (Connection con = new ApplicationDB().getConnection()) {
            if (con == null) {
                request.setAttribute("error", "Database connection failed.");
            } else {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                    "SELECT DATE_FORMAT(reservation_date, '%Y-%m') AS month, " +
                    "SUM(fare) AS total_sales " +
                    "FROM reservations " +
                    "GROUP BY month " +
                    "ORDER BY month DESC");

                while (rs.next()) {
                    String[] row = { rs.getString("month"), rs.getString("total_sales") };
                    sales.add(row);
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("sales", sales);
        request.getRequestDispatcher("salesReport.jsp").forward(request, response);
    }
}