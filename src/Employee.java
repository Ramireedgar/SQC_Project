/*
 * user subclass for normal employee users to access their own data only
 */

import java.util.Scanner;

// Ensure it says "extends User" here to fix the "Type mismatch" error
public class Employee extends User {

    // This is the Constructor that was missing
    public Employee(int empId, String username, String password) {
        super(empId, username, password);
    }

    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        do {
            System.out.println("\n=== Employee Menu ===");
            System.out.println("1. View Personal Data");
            System.out.println("2. Logout");
            System.out.print("Select an option: ");

            try {
                String input = scanner.nextLine();
                option = Integer.parseInt(input);

                switch (option) {
                    case 1:
                        viewPersonalData();
                        break;
                    case 2:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        } while (option != 2);
    }

    private void viewPersonalData() {
        // Use the ID from the User class (inherited)
        int myId = this.getEmpId();
        
        System.out.println("\n--- My Personal Information ---");
        
        // Use the existing DAO method to fetch data
        EmployeeData emp = EmployeeDAO.getEmployeeById(myId);

        if (emp != null) {
            System.out.println("Employee ID:  " + emp.getEmpId());
            System.out.println("Name:         " + emp.getFirstName() + " " + emp.getLastName());
            System.out.println("Email:        " + emp.getEmail());
            System.out.println("Salary:       $" + emp.getSalary());
            System.out.println("Hire Date:    " + emp.getHireDate());
        } else {
            System.out.println("Error: Could not retrieve data for ID " + myId);
            System.out.println("Please contact HR Admin.");
        }
        System.out.println("-------------------------------");
    }
}