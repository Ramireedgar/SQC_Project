/*
 * User class represents a Generic user in the system with common attributes and methods.
 * This class is to be extended by specific user types like Admin and Employee.
 * 
 * showMenu() will display a different menus for admin vs employee thus will be implemented in subclasses.
 */

public abstract class User {
    private int empId;
    private String username;
    private String password;

    public User(int empId, String username, String password) {
        this.empId = empId;
        this.username = username;
        this.password = password;
    }

    public abstract void showMenu();

    public int getEmpId() {
        return empId;
    }

    public String getUsername() {
        return username;
    }

}
