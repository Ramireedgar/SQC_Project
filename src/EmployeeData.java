/**
 * EmployeeData class represents employee information in the system.
 * This class is used to transfer employee data between the application and database.
 */
public class EmployeeData {
    private int empId;
    private String firstName;
    private String lastName;
    private String email;
    private double salary;
    private String hireDate;
    private int DOB; //style: ddmmyyyy
    private int SSN;
    private String department;
    private String position;

    // Constructor
    public EmployeeData() {
    }

    public EmployeeData(int empId, String firstName, String lastName, String email, 
                       double salary, String hireDate, int DOB, int SSN, String department) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.hireDate = hireDate;
        this.DOB = DOB;
        this.SSN = SSN;
        this.department = department;
    }

    // Getters and Setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public int getDOB(){
        return DOB;
    }

    public void setDOB(int DOB){
        this.DOB = DOB;
    }

    public int getSSN(){
        return SSN;
    }

    public void setSSN(int SSN){
        this.SSN = SSN;
    }

    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public String getPosition(){
        return position;
    }

    public void setPosition(String position){
        this.position = position;
    }

    @Override
    public String toString() {
        return "EmployeeData{" +
                "empId=" + empId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", hireDate='" + hireDate + '\'' +
                ", DOB='" + DOB + '\'' +
                ", SSN='" + SSN + '\'' +
                ", Department='" + department + '\'' +
                ", Position='" + position + '\'' +
                '}';
    }
}
