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

    public static boolean updateEmployee(int empId, EmployeeData employeeData) {
        try {
            if (!employeeExists(empId)) {
                System.out.println("Error: Employee with ID " + empId + " does not exist.");
                return false;
            }

            if (employeeData.getEmail() != null && !employeeData.getEmail().isEmpty()) {
                if (!isValidEmail(employeeData.getEmail())) {
                    System.out.println("Error: Invalid email format.");
                    return false;
                }
            }

            if (employeeData.getSalary() < 0) {
                System.out.println("Error: Salary must be positive.");
                return false;
            }

            List<String> updateFields = new ArrayList<>();
            List<Object> updateValues = new ArrayList<>();

            if (employeeData.getFirstName() != null && !employeeData.getFirstName().isEmpty()) {
                updateFields.add("first_name = ?");
                updateValues.add(employeeData.getFirstName());
            }
            if (employeeData.getLastName() != null && !employeeData.getLastName().isEmpty()) {
                updateFields.add("last_name = ?");
                updateValues.add(employeeData.getLastName());
            }
            if (employeeData.getEmail() != null && !employeeData.getEmail().isEmpty()) {
                updateFields.add("email = ?");
                updateValues.add(employeeData.getEmail());
            }
            if (employeeData.getPhone() != null && !employeeData.getPhone().isEmpty()) {
                updateFields.add("phone = ?");
                updateValues.add(employeeData.getPhone());
            }
            if (employeeData.getDepartment() != null && !employeeData.getDepartment().isEmpty()) {
                updateFields.add("department = ?");
                updateValues.add(employeeData.getDepartment());
            }
            if (employeeData.getPosition() != null && !employeeData.getPosition().isEmpty()) {
                updateFields.add("position = ?");
                updateValues.add(employeeData.getPosition());
            }
            if (employeeData.getSalary() > 0) {
                updateFields.add("salary = ?");
                updateValues.add(employeeData.getSalary());
            }
            if (employeeData.getHireDate() != null && !employeeData.getHireDate().isEmpty()) {
                updateFields.add("hire_date = ?");
                updateValues.add(employeeData.getHireDate());
            }
            if (employeeData.getAddress() != null && !employeeData.getAddress().isEmpty()) {
                updateFields.add("address = ?");
                updateValues.add(employeeData.getAddress());
            }

            if (updateFields.isEmpty()) {
                System.out.println("Error: No fields to update.");
                return false;
            }

            String sql = "UPDATE employees SET " + String.join(", ", updateFields) + " WHERE empid = ?";
            updateValues.add(empId);

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                for (int i = 0; i < updateValues.size(); i++) {
                    Object value = updateValues.get(i);
                    if (value instanceof String) {
                        pstmt.setString(i + 1, (String) value);
                    } else if (value instanceof Double) {
                        pstmt.setDouble(i + 1, (Double) value);
                    } else if (value instanceof Integer) {
                        pstmt.setInt(i + 1, (Integer) value);
                    }
                }

                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error updating employee: " + e.getMessage());
            return false;
        }
    }
}

