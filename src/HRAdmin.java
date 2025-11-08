import java.util.*;

public class HRAdmin extends User {
    private Scanner scanner;

    public HRAdmin(int empId, String username, String password) {
        super(empId, username, password);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showMenu() {
        System.out.println("HR Admin Menu:");
        System.out.println("1. View All Employee Data");
        System.out.println("2. Create Employee");
        System.out.println("3. Delete Employee");
        System.out.println("4. Update Employee");
        System.out.println("5. Search Employee");
        System.out.println("6. Update Salaries Below Threshold");
        System.out.println("7. Logout");
    }

    public void updateEmployeeData() {
        System.out.println("\n=== Update Employee Data ===");
        System.out.print("Enter Employee ID to update: ");
        
        try {
            int empId = Integer.parseInt(scanner.nextLine());
            EmployeeData currentEmployee = EmployeeDAO.getEmployeeById(empId);
            if (currentEmployee == null) {
                System.out.println("Employee not found!");
                return;
            }
            
            System.out.println("\nCurrent Employee Data:");
            System.out.println("First Name: " + currentEmployee.getFirstName());
            System.out.println("Last Name: " + currentEmployee.getLastName());
            System.out.println("Email: " + currentEmployee.getEmail());
            System.out.println("Phone: " + currentEmployee.getPhone());
            System.out.println("Department: " + currentEmployee.getDepartment());
            System.out.println("Position: " + currentEmployee.getPosition());
            System.out.println("Salary: " + currentEmployee.getSalary());
            System.out.println("Hire Date: " + currentEmployee.getHireDate());
            System.out.println("Address: " + currentEmployee.getAddress());
            
            EmployeeData updatedData = new EmployeeData();
            
            System.out.println("\nEnter new values (press Enter to skip):");
            
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            if (!firstName.isEmpty()) updatedData.setFirstName(firstName);
            
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            if (!lastName.isEmpty()) updatedData.setLastName(lastName);
            
            System.out.print("Email: ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) updatedData.setEmail(email);
            
            System.out.print("Phone: ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) updatedData.setPhone(phone);
            
            System.out.print("Department: ");
            String department = scanner.nextLine();
            if (!department.isEmpty()) updatedData.setDepartment(department);
            
            System.out.print("Position: ");
            String position = scanner.nextLine();
            if (!position.isEmpty()) updatedData.setPosition(position);
            
            System.out.print("Salary: ");
            String salaryStr = scanner.nextLine();
            if (!salaryStr.isEmpty()) {
                try {
                    updatedData.setSalary(Double.parseDouble(salaryStr));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid salary format. Skipping salary update.");
                }
            }
            
            System.out.print("Hire Date: ");
            String hireDate = scanner.nextLine();
            if (!hireDate.isEmpty()) updatedData.setHireDate(hireDate);
            
            System.out.print("Address: ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) updatedData.setAddress(address);
            
            boolean success = EmployeeDAO.updateEmployee(empId, updatedData);
            if (success) {
                System.out.println("Employee data updated successfully!");
            } else {
                System.out.println("Failed to update employee data.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid Employee ID format!");
        }
    }

    public void searchEmployee() {
        System.out.println("\n=== Search Employee ===");
        System.out.println("1. Search by Employee ID");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Department");
        System.out.println("4. Search by Email");
        System.out.println("5. Advanced Search (Multiple Criteria)");
        System.out.print("Select search option: ");
        
        try {
            int option = Integer.parseInt(scanner.nextLine());
            List<EmployeeData> results = new ArrayList<>();
            
            switch (option) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    int empId = Integer.parseInt(scanner.nextLine());
                    results = EmployeeDAO.searchByEmployeeId(empId);
                    break;
                    
                case 2:
                    System.out.print("Enter First Name (or press Enter to skip): ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter Last Name (or press Enter to skip): ");
                    String lastName = scanner.nextLine();
                    if (firstName.isEmpty() && lastName.isEmpty()) {
                        System.out.println("At least one name field must be provided.");
                        return;
                    }
                    results = EmployeeDAO.searchByName(
                        firstName.isEmpty() ? null : firstName,
                        lastName.isEmpty() ? null : lastName
                    );
                    break;
                    
                case 3:
                    System.out.print("Enter Department: ");
                    String department = scanner.nextLine();
                    results = EmployeeDAO.searchByDepartment(department);
                    break;
                    
                case 4:
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    results = EmployeeDAO.searchByEmail(email);
                    break;
                    
                case 5:
                    Map<String, Object> criteria = new HashMap<>();
                    System.out.print("Enter Department (or press Enter to skip): ");
                    String dept = scanner.nextLine();
                    if (!dept.isEmpty()) criteria.put("department", dept);
                    System.out.print("Enter Minimum Salary (or press Enter to skip): ");
                    String minSalary = scanner.nextLine();
                    if (!minSalary.isEmpty()) criteria.put("salary_min", Double.parseDouble(minSalary));
                    System.out.print("Enter Maximum Salary (or press Enter to skip): ");
                    String maxSalary = scanner.nextLine();
                    if (!maxSalary.isEmpty()) criteria.put("salary_max", Double.parseDouble(maxSalary));
                    results = EmployeeDAO.searchByMultipleCriteria(criteria);
                    break;
                    
                default:
                    System.out.println("Invalid option!");
                    return;
            }
            
            if (results.isEmpty()) {
                System.out.println("No employees found matching the criteria.");
            } else {
                System.out.println("\nSearch Results (" + results.size() + " employee(s) found):");
                System.out.println("----------------------------------------------------------------------------");
                for (EmployeeData emp : results) {
                    System.out.println("ID: " + emp.getEmpId());
                    System.out.println("Name: " + emp.getFirstName() + " " + emp.getLastName());
                    System.out.println("Email: " + emp.getEmail());
                    System.out.println("Department: " + emp.getDepartment());
                    System.out.println("Position: " + emp.getPosition());
                    System.out.println("Salary: " + emp.getSalary());
                    System.out.println("----------------------------------------------------------------------------");
                }
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format!");
        }
    }

    public void updateSalariesBelowThreshold() {
        System.out.println("\n=== Update Salaries Below Threshold ===");
        
        try {
            System.out.print("Enter salary threshold: $");
            double threshold = Double.parseDouble(scanner.nextLine());
            
            if (threshold < 0) {
                System.out.println("Threshold must be positive!");
                return;
            }
            
            List<EmployeeData> affectedEmployees = EmployeeDAO.getEmployeesBelowThreshold(threshold);
            if (affectedEmployees.isEmpty()) {
                System.out.println("No employees found with salary below $" + threshold);
                return;
            }
            
            System.out.println("\nEmployees that will be affected (" + affectedEmployees.size() + " employee(s)):");
            System.out.println("----------------------------------------------------------------------------");
            for (EmployeeData emp : affectedEmployees) {
                System.out.println("ID: " + emp.getEmpId() + 
                                 " | Name: " + emp.getFirstName() + " " + emp.getLastName() + 
                                 " | Current Salary: $" + emp.getSalary());
            }
            System.out.println("----------------------------------------------------------------------------");
            
            System.out.print("Enter new salary amount: $");
            double newSalary = Double.parseDouble(scanner.nextLine());
            
            if (newSalary < 0) {
                System.out.println("New salary must be positive!");
                return;
            }
            
            System.out.print("Are you sure you want to update " + affectedEmployees.size() + 
                           " employee(s)? (yes/no): ");
            String confirmation = scanner.nextLine();
            
            if (!confirmation.equalsIgnoreCase("yes")) {
                System.out.println("Update cancelled.");
                return;
            }
            
            int updatedCount = EmployeeDAO.updateSalariesBelowThreshold(threshold, newSalary);
            if (updatedCount > 0) {
                System.out.println("Successfully updated " + updatedCount + " employee(s).");
            } else if (updatedCount == 0) {
                System.out.println("No employees were updated.");
            } else {
                System.out.println("Error occurred during update.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format!");
        }
    }
}
