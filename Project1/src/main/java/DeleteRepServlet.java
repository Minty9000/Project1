import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/DeleteRepServlet")
public class DeleteRepServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("user_id"));

        try (Connection conn = com.admin.utils.DBUtil.getConnection()) {
            // Only delete if the user is a rep
            PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM Users WHERE user_id = ? AND role = 'rep'");
            stmt.setInt(1, userId);
            stmt.executeUpdate();

            response.sendRedirect("manageReps.jsp");
        } catch (Exception e) {
            throw new ServletException("Delete failed", e);
        }
    }
}