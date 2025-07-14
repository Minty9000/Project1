import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = com.admin.utils.DBUtil.getConnection()) {
            String checkSql = "SELECT * FROM Users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                response.sendRedirect("register.jsp?taken=true");
                return;
            }

            String insertSql = "INSERT INTO Users (username, password, role) VALUES (?, ?, 'customer')";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password); // NOTE: hash in real apps!
            insertStmt.executeUpdate();

            response.sendRedirect("login.jsp");
        } catch (SQLException e) {
            throw new ServletException("DB registration error", e);
        }
    }
}