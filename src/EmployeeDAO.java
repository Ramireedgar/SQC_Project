import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeDAO {
    private static final String url = "jdbc:mysql://localhost:3306/employeeData";
    private static final String user = "root";
    private static final String password = "password";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private static boolean employeeExists(int empId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM employees WHERE empid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private static EmployeeData mapResultSetToEmployeeData(ResultSet rs) throws SQLException {
        EmployeeData emp = new EmployeeData();
        emp.setEmpId(rs.getInt("empid"));
        emp.setFirstName(rs.getString("first_name"));
        emp.setLastName(rs.getString("last_name"));
        emp.setEmail(rs.getString("email"));
        emp.setPhone(rs.getString("phone"));
        emp.setDepartment(rs.getString("department"));
        emp.setPosition(rs.getString("position"));
        emp.setSalary(rs.getDouble("salary"));
        emp.setHireDate(rs.getString("hire_date"));
        emp.setAddress(rs.getString("address"));
        return emp;
    }

    public static EmployeeData getEmployeeById(int empId) {
        String sql = "SELECT * FROM employees WHERE empid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToEmployeeData(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving employee: " + e.getMessage());
        }
        return null;
    }
}

