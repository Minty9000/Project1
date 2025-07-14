

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class SalesReportServlet
 */
@WebServlet("/SalesReportServlet")
public class SalesReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        List<String[]> sales = new ArrayList<>();
        try (Connection conn = com.admin.utils.DBUtil.getConnection()) {
            Statement stmt = conn.createStatement();
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
