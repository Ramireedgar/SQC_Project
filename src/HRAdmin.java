/*\
 * HRAdmin.java
 */

public class HRAdmin extends User {
    public HRAdmin(int empId, String username, String password) {
        super(empId, username, password);
    }

    @Override
    public void showMenu() {
        System.out.println("HR Admin Menu:");
        System.out.println("1. View All Employee Data");
        System.out.println("2. Create Employee");
        System.out.println("3. Delete Employee");
        System.out.println("4. Update Employee");
        System.out.println("5. Logout");
    }

}
