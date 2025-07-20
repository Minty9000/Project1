package com.cs336.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


/**
 * Servlet implementation class SalesReportServlet
 */
@WebServlet("/SalesReportServlet")
public class SalesReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        List<String[]> sales = new ArrayList<>();
        try (Connection con = new ApplicationDB().getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT DATE_FORMAT(reservation_date, '%Y-%m') AS month, SUM(price) AS total_sales " +
                "FROM CustomerReservation GROUP BY month ORDER BY month DESC");

            while (rs.next()) {
                String[] row = { rs.getString("month"), rs.getString("total_sales") };
                sales.add(row);
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.setAttribute("sales", sales);
        request.getRequestDispatcher("salesReport.jsp").forward(request, response);
    }
}
