import java.util.*;

public class HRAdmin extends User {
    private Scanner scanner;

    public HRAdmin(int empId, String username, String password) {
        super(empId, username, password);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showMenu() {
        int option = 0;
        do {
            System.out.println("\n=== HR Admin Menu ===");
            System.out.println("1. Create Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Salaries in range (min-max)");
            System.out.println("5. Report: Pay by Division");
            System.out.println("6. Report: Employees Hired by Date Range");
            System.out.println("7. Logout");
            System.out.print("Select an option: ");

            try {
                String input = scanner.nextLine();
                option = Integer.parseInt(input);

                switch (option) {
                    case 1:
                        createEmployee();
                        break;
                    case 2:
                        updateEmployeeData();
                        break;
                    case 3:
                        searchEmployee();
                        break;
                    case 4:
                        updateSalariesByRange();
                        break;
                    case 5:
                        generatePayReportByDivision();
                        break;
                    case 6:
                        generateEmployeesHiredReport();  //TESTING METHOD DEC 09 2025 IN PROG...
                        break;
                    case 7:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                option = 0; // Reset to ensure loop continues
            }
        } while (option != 7);
    }

    public void createEmployee() {
        System.out.println("\n=== Create New Employee ===");
        try {
            EmployeeData newEmp = new EmployeeData();

            System.out.print("Employee ID: ");
            newEmp.setEmpId(Integer.parseInt(scanner.nextLine()));
            
            System.out.print("First Name: ");
            newEmp.setFirstName(scanner.nextLine());

            System.out.print("Last Name: ");
            newEmp.setLastName(scanner.nextLine());

            System.out.print("Email: ");
            newEmp.setEmail(scanner.nextLine());

            System.out.print("Salary: ");
            newEmp.setSalary(Double.parseDouble(scanner.nextLine()));

            System.out.print("Hire Date (YYYY-MM-DD): ");
            newEmp.setHireDate(scanner.nextLine());
            
            // Assuming simplified integer input for these based on your EmployeeData class
            System.out.print("DOB (DDMMYYYY): "); 
            newEmp.setDOB(Integer.parseInt(scanner.nextLine()));

            System.out.print("SSN (No dashes): ");
            newEmp.setSSN(Integer.parseInt(scanner.nextLine()));

            // TESTING IN PROG STILL SENSITIVE
            System.out.print("Department: ");
            newEmp.setDepartment(scanner.nextLine());

            System.out.print("Position: ");
            newEmp.setPosition(scanner.nextLine());
            //end of test code !!!...

            boolean success = EmployeeDAO.createEmployee(newEmp);
            
            if (success) {
                System.out.println("Employee created successfully!");
            } else {
                System.out.println("Failed to create employee.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Employee creation cancelled.");
        }
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
            System.out.println("Salary: " + currentEmployee.getSalary());
            System.out.println("Hire Date: " + currentEmployee.getHireDate());
            System.err.println("Position: " + currentEmployee.getPosition());
            
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

            System.out.print("Position: ");
            String pos = scanner.nextLine();
            if (!pos.isEmpty()) updatedData.setPosition(pos);
            
            
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
        System.out.println("3. Search by DOB");
        System.out.println("4. Search by SSN");
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
                    System.out.print("Enter DOB (format: ddmmyyyy, e.g., 15011990): ");
                    String dobStr = scanner.nextLine();
                    try {
                        int dob = Integer.parseInt(dobStr);
                        results = EmployeeDAO.searchByDOB(dob);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid DOB format! Please use ddmmyyyy format (e.g., 15011990).");
                        return;
                    }
                    break;
                    
                case 4:
                    System.out.print("Enter SSN (format: numbers only, no dashes, e.g., 123456789): ");
                    String ssnStr = scanner.nextLine();
                    try {
                        int ssn = Integer.parseInt(ssnStr);
                        results = EmployeeDAO.searchBySSN(ssn);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid SSN format! Please use numbers only (no dashes).");
                        return;
                    }
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

    public void updateSalariesByRange() {
        System.out.println("\n=== Bulk Update Salaries (By Range) ===");
        try {
            System.out.print("Enter Minimum Salary: ");
            double min = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Maximum Salary: ");
            double max = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Percentage Increase (e.g., 10 for 10%, -5 for -5%): ");
            double percent = Double.parseDouble(scanner.nextLine());

            // Confirmation
            System.out.println("This will update ALL salaries between $" + min + " and $" + max + " by " + percent + "%.");
            System.out.print("Are you sure? (yes/no): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                int count = EmployeeDAO.updateSalariesInRange(min, max, percent);
                
                if (count >= 0) {
                    System.out.println("Success! Updated " + count + " employee(s).");
                } else {
                    System.out.println("An error occurred during the update.");
                }
            } else {
                System.out.println("Operation cancelled.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Please enter valid numbers.");
        }
    }

    public void generatePayReportByDivision() {
        System.out.println("\n=== Pay Report by Division ===");
        
        try {
            System.out.print("Enter Year (e.g., 2024): ");
            int year = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter Month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());
            
            if (month < 1 || month > 12) {
                System.out.println("Invalid month! Please enter a value between 1 and 12.");
                return;
            }
            
            Map<String, Map<String, Object>> report = EmployeeDAO.getPayReportByDivision(year, month);
            
            if (report.isEmpty()) {
                System.out.println("No data found for the specified period.");
                return;
            }
            
            System.out.println("\n==========================================");
            System.out.println("PAY REPORT BY DIVISION");
            System.out.println("Period: " + getMonthName(month) + " " + year);
            System.out.println("==========================================");
            System.out.printf("%-30s %-10s %-20s %-20s%n", "Division", "Count", "Total Monthly Pay", "Average Monthly Pay");
            System.out.println("----------------------------------------------------------------------------");
            
            double grandTotalMonthly = 0;
            int grandTotalCount = 0;
            
            for (Map.Entry<String, Map<String, Object>> entry : report.entrySet()) {
                String division = entry.getKey();
                Map<String, Object> data = entry.getValue();
                int count = (Integer) data.get("count");
                double totalMonthlyPay = (Double) data.get("totalMonthlyPay");
                double avgMonthlyPay = (Double) data.get("averageMonthlyPay");
                
                System.out.printf("%-30s %-10d $%-19.2f $%-19.2f%n", 
                    division, count, totalMonthlyPay, avgMonthlyPay);
                
                grandTotalMonthly += totalMonthlyPay;
                grandTotalCount += count;
            }
            
            System.out.println("----------------------------------------------------------------------------");
            System.out.printf("%-30s %-10d $%-19.2f%n", "TOTAL", grandTotalCount, grandTotalMonthly);
            System.out.println("==========================================\n");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format! Please enter valid numbers.");
        }
    }

    private String getMonthName(int month) {
        String[] monthNames = {
            "", "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        return monthNames[month];
    }
    
public void generateEmployeesHiredReport() {
        System.out.println("\n=== Employees Hired by Date Range ===");

        try {
            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();

            System.out.print("Enter End Date (YYYY-MM-DD): ");
            String endDate = scanner.nextLine();

            List<EmployeeData> employees = EmployeeDAO.getEmployeesHiredByDateRange(startDate, endDate);

            if (employees.isEmpty()) {
                System.out.println("No employees found hired between " + startDate + " and " + endDate);
                return;
            }

            System.out.println("\n==========================================");
            System.out.println("EMPLOYEES HIRED BY DATE RANGE");
            System.out.println("Date Range: " + startDate + " to " + endDate);
            System.out.println("Total Employees: " + employees.size());
            System.out.println("==========================================");
            System.out.printf("%-10s %-25s %-30s %-20s %-15s%n", 
                "Emp ID", "Name", "Department", "Position", "Hire Date");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");

            for (EmployeeData emp : employees) {
                System.out.printf("%-10d %-25s %-30s %-20s %-15s%n",
                    emp.getEmpId(),
                    emp.getFirstName() + " " + emp.getLastName(),
                    emp.getDepartment() != null ? emp.getDepartment() : "N/A",
                    emp.getPosition() != null ? emp.getPosition() : "N/A",
                    emp.getHireDate() != null ? emp.getHireDate() : "N/A");
            }

            System.out.println("==========================================\n");

        } catch (Exception e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

}
