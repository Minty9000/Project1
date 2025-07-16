import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/BestStatsServlet")
public class BestStatsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String[]> topCustomers = new ArrayList<>();
        List<String[]> topLines = new ArrayList<>();

        try (Connection conn = com.admin.utils.DBUtil.getConnection()) {
            // Top 5 customers by total spent
            String customerQuery = "SELECT name, SUM(price) AS total_spent " +
                                   "FROM CustomerReservation " +
                                   "GROUP BY name ORDER BY total_spent DESC LIMIT 5";

            PreparedStatement custStmt = conn.prepareStatement(customerQuery);
            ResultSet custRs = custStmt.executeQuery();
            while (custRs.next()) {
                String[] row = {
                    custRs.getString("name"),
                    custRs.getString("total_spent")
                };
                topCustomers.add(row);
            }

            // Top 5 most active transit lines
            String lineQuery = "SELECT T.transit_line, COUNT(*) AS reservation_count " +
                               "FROM CustomerReservation R " +
                               "JOIN Train T ON R.train_id = T.train_id " +
                               "GROUP BY T.transit_line " +
                               "ORDER BY reservation_count DESC LIMIT 5";

            PreparedStatement lineStmt = conn.prepareStatement(lineQuery);
            ResultSet lineRs = lineStmt.executeQuery();
            while (lineRs.next()) {
                String[] row = {
                    lineRs.getString("transit_line"),
                    lineRs.getString("reservation_count")
                };
                topLines.add(row);
            }

        } catch (Exception e) {
            throw new ServletException("Error retrieving top stats", e);
        }

        request.setAttribute("topCustomers", topCustomers);
        request.setAttribute("topLines", topLines);
        request.getRequestDispatcher("bestStats.jsp").forward(request, response);
    }
}