import java.sql.*;
/*
 * Authenticator class handles user authentication
 * it checks if the user is a hr/admin or regular employee in the database 
 * then creates the adminuser or employeeuser object accordingly.
 * and returns the object allowing access to either admin or employee menu.
 */
public class Authenticator {
    private static final String url = "jdbc:mysql://localhost:3306/employeeData";
    private static final String user = "root";
    private static final String password = "password";  // Change here

    public static User login(String username, String passwordInput){
        
        String sqlcommand = "SELECT empid, username, password, role FROM user_accounts " +
                     "WHERE username = '" + username + "' " +
                     "AND password = '" + passwordInput + "'";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlcommand)) {

            StringBuilder output = new StringBuilder();

            if (rs.next()) {
                int empid = rs.getInt("empid");
                String role = rs.getString("role");

                output.append("Login successful for user: ").append(username)
                      .append(" (Role: ").append(role).append(")");
                System.out.println(output.toString());

                if (role.equalsIgnoreCase("HR")) {
                    return new HRAdmin(empid, username, passwordInput);
                } else {
                    return new Employee(empid, username, passwordInput);
                }
            } else {
                output.append("Invalid username or password for user: ").append(username);
                System.out.println(output.toString());
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }

        return null;
    }
}
