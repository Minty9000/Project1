import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ListReps")
public class CustomerRepServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        try (Connection conn = com.admin.utils.DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE role = 'rep'");
             ResultSet rs = stmt.executeQuery()) {

            response.getWriter().println("<h2>Customer Representatives:</h2><ul>");
            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                response.getWriter().println("<li>" + username + " - " + email + "</li>");
            }
            response.getWriter().println("</ul>");

        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}