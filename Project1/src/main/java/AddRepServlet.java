import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.sql.*;

@WebServlet("/AddRepServlet")
public class AddRepServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password"); // plaintext for now

        try (Connection conn = com.admin.utils.DBUtil.getConnection()) {
            String sql = "INSERT INTO Users (username, password, email, phone, role) VALUES (?, ?, ?, ?, 'rep')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.executeUpdate();

            response.sendRedirect("manageReps.jsp");
        } catch (Exception e) {
            throw new ServletException("Insert failed", e);
        }
    }
}