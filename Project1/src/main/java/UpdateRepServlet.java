

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/UpdateRepServlet")
public class UpdateRepServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        int repId = Integer.parseInt(request.getParameter("rep_id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        try (Connection conn = com.admin.utils.DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE CustomerReps SET name = ?, email = ?, phone = ? WHERE rep_id = ?");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setInt(4, repId);
            stmt.executeUpdate();
            response.sendRedirect("manageReps.jsp");
        } catch (Exception e) {
            throw new ServletException("Update failed", e);
        }
    }
}
