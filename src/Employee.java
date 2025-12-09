/*
 * user subclass for normal employee users to access their own data only
 */

import java.util.Scanner;

public class Employee extends User {
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
                        // You will need to implement a 'getById' using this.getEmpId() later
                        System.out.println("Personal Data View: (Feature coming soon...)");
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
}
