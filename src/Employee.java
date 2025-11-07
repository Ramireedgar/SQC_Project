/*
 * user subclass for normal employee users to access their own data only
 */

public class Employee extends User {
    public Employee(int empId, String username, String password) {
        super(empId, username, password);
    }

    @Override
    public void showMenu() {
        System.out.println("Employee Menu:");
        System.out.println("1. View Personal Data");
        System.out.println("2. Logout");
    }

}
