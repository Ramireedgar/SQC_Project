# Requirements and Test Cases Documentation

## Overview
This document describes the detailed programming tasks for three key features and their corresponding test cases with pass/fail criteria.

---

## Requirement A: Update Employee Data

### Detailed Programming Task Description

**Objective**: Implement a general-purpose employee data update functionality that allows authorized users (HR Admin) to update any employee data field in the database.

**Technical Requirements**:
1. Create an `EmployeeData` class to represent employee information with fields:
   - Employee ID (int, primary key)
   - First Name (String)
   - Last Name (String)
   - Email (String)
   - Phone (String)
   - Department (String)
   - Position (String)
   - Salary (double)
   - Hire Date (Date/String)
   - Address (String)

2. Create an `EmployeeDAO` (Data Access Object) class with:
   - Database connection management (reuse connection pattern from Authenticator)
   - `updateEmployee(int empId, EmployeeData employeeData)` method that:
     - Validates that the employee exists
     - Updates only the provided fields (partial update support)
     - Uses PreparedStatement to prevent SQL injection
     - Returns boolean indicating success/failure
     - Handles SQL exceptions gracefully

3. Implement `updateEmployee()` method in `HRAdmin` class that:
   - Prompts admin for employee ID
   - Retrieves current employee data
   - Allows admin to update any field
   - Calls EmployeeDAO to persist changes
   - Provides confirmation message

4. Input validation:
   - Employee ID must exist in database
   - Email format validation
   - Salary must be positive
   - Required fields cannot be null/empty

5. Security:
   - Only HR Admin role can update employee data
   - Log all update operations for audit trail

**Database Schema Assumptions**:
- Table: `employees` with columns: empid, first_name, last_name, email, phone, department, position, salary, hire_date, address

---

## Requirement B: Search for Employee (Admin User)

### Detailed Programming Task Description

**Objective**: Implement employee search functionality that allows HR Admin users to search for employees using various criteria.

**Technical Requirements**:
1. Extend `EmployeeDAO` class with search methods:
   - `searchByEmployeeId(int empId)` - Search by exact employee ID
   - `searchByName(String firstName, String lastName)` - Search by name (supports partial matches)
   - `searchByDepartment(String department)` - Search by department
   - `searchByEmail(String email)` - Search by email
   - `searchByMultipleCriteria(Map<String, Object> criteria)` - Advanced search with multiple filters

2. Each search method should:
   - Use PreparedStatement for SQL injection prevention
   - Return List<EmployeeData> with matching employees
   - Handle case-insensitive searches where appropriate
   - Return empty list if no matches found
   - Handle SQL exceptions

3. Implement `searchEmployee()` method in `HRAdmin` class that:
   - Displays search menu options:
     - Search by Employee ID
     - Search by Name
     - Search by Department
     - Search by Email
     - Advanced Search (multiple criteria)
   - Prompts for search criteria
   - Displays search results in formatted table
   - Handles "no results found" scenario

4. Search functionality requirements:
   - Support partial string matching for name and department
   - Case-insensitive search
   - Wildcard support (e.g., "%Smith%")
   - Return all employees if no criteria provided (with limit)

5. Security:
   - Only HR Admin role can search employees
   - Log search operations

**Database Schema Assumptions**:
- Same `employees` table as Requirement A

---

## Requirement C: Update Salary for All Employees Less Than a Particular Amount

### Detailed Programming Task Description

**Objective**: Implement bulk salary update functionality that updates salaries for all employees earning less than a specified threshold amount.

**Technical Requirements**:
1. Extend `EmployeeDAO` class with:
   - `updateSalariesBelowThreshold(double threshold, double newSalary)` method that:
     - Uses PreparedStatement with UPDATE query
     - Updates salary for all employees where current salary < threshold
     - Returns int count of affected rows
     - Uses transaction to ensure atomicity
     - Handles SQL exceptions

2. Alternative implementation options:
   - Option 1: Set all salaries to a fixed new amount
   - Option 2: Apply a percentage increase
   - Option 3: Apply a fixed increment amount
   
   For this requirement, we'll implement Option 1 (set to new salary) with extension points for other options.

3. Implement `updateSalariesBelowThreshold()` method in `HRAdmin` class that:
   - Prompts admin for:
     - Salary threshold amount
     - New salary amount
   - Displays preview of affected employees (count and list)
   - Asks for confirmation before executing update
   - Executes bulk update
   - Displays summary (number of employees updated)

4. Validation:
   - Threshold must be positive
   - New salary must be positive
   - New salary should be greater than threshold (to avoid infinite loops)
   - Display warning if large number of employees will be affected

5. Transaction Management:
   - Use database transactions to ensure all-or-nothing update
   - Rollback on error
   - Commit on success

6. Security:
   - Only HR Admin role can perform bulk updates
   - Log bulk update operations with details (threshold, new salary, affected count)

**Database Schema Assumptions**:
- Same `employees` table with salary column

---

## Test Cases

### Test Cases for Requirement A: Update Employee Data

#### Test Case A1: Update Employee - Valid Update (PASS)
**Description**: Update an existing employee's data with valid information
**Preconditions**: 
- Employee with ID 101 exists in database
- HR Admin user is logged in
- Database connection is available

**Test Steps**:
1. Call `updateEmployee(101, employeeData)` where employeeData contains:
   - first_name: "John"
   - last_name: "Updated"
   - email: "john.updated@company.com"
   - salary: 55000.00
2. Verify method returns true
3. Query database to verify data was updated correctly

**Expected Result**: 
- Method returns true
- Employee data in database matches updated values
- All specified fields are updated correctly

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case A2: Update Employee - Non-Existent Employee ID (FAIL)
**Description**: Attempt to update an employee that doesn't exist
**Preconditions**: 
- Employee with ID 9999 does NOT exist in database
- HR Admin user is logged in

**Test Steps**:
1. Call `updateEmployee(9999, employeeData)` with valid employeeData
2. Verify method returns false
3. Verify no database changes occurred

**Expected Result**: 
- Method returns false
- No database records are modified
- Appropriate error message is logged/returned

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case A3: Update Employee - Invalid Email Format (FAIL)
**Description**: Attempt to update employee with invalid email format
**Preconditions**: 
- Employee with ID 101 exists
- HR Admin user is logged in

**Test Steps**:
1. Call `updateEmployee(101, employeeData)` where employeeData contains:
   - email: "invalid-email-format"
2. Verify method returns false or throws validation exception
3. Verify database data remains unchanged

**Expected Result**: 
- Method returns false or throws ValidationException
- Employee data in database remains unchanged
- Error message indicates invalid email format

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case A4: Update Employee - Negative Salary (FAIL)
**Description**: Attempt to update employee with negative salary
**Preconditions**: 
- Employee with ID 101 exists
- HR Admin user is logged in

**Test Steps**:
1. Call `updateEmployee(101, employeeData)` where employeeData contains:
   - salary: -1000.00
2. Verify method returns false or throws validation exception
3. Verify database data remains unchanged

**Expected Result**: 
- Method returns false or throws ValidationException
- Employee data in database remains unchanged
- Error message indicates invalid salary (must be positive)

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case A5: Update Employee - Partial Update (PASS)
**Description**: Update only specific fields of an employee (partial update)
**Preconditions**: 
- Employee with ID 101 exists with complete data
- HR Admin user is logged in

**Test Steps**:
1. Retrieve current employee data for ID 101
2. Call `updateEmployee(101, employeeData)` where employeeData contains only:
   - first_name: "UpdatedName"
   - department: "Engineering"
3. Verify method returns true
4. Query database and verify:
   - first_name and department are updated
   - Other fields remain unchanged

**Expected Result**: 
- Method returns true
- Only specified fields (first_name, department) are updated
- All other fields retain their original values

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case A6: Update Employee - SQL Injection Prevention (PASS)
**Description**: Verify that PreparedStatement prevents SQL injection attacks
**Preconditions**: 
- Employee with ID 101 exists
- HR Admin user is logged in

**Test Steps**:
1. Call `updateEmployee(101, employeeData)` where employeeData contains:
   - first_name: "John'; DROP TABLE employees; --"
2. Verify method handles input safely
3. Verify no SQL injection occurs (table still exists, no unexpected queries executed)

**Expected Result**: 
- Method returns true or false (based on validation)
- No SQL injection occurs
- Employees table still exists and is accessible
- Malicious input is treated as literal string

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

### Test Cases for Requirement B: Search for Employee

#### Test Case B1: Search by Employee ID - Valid ID (PASS)
**Description**: Search for an employee using a valid employee ID
**Preconditions**: 
- Employee with ID 101 exists in database
- HR Admin user is logged in

**Test Steps**:
1. Call `searchByEmployeeId(101)`
2. Verify method returns List<EmployeeData> with one employee
3. Verify returned employee has ID 101
4. Verify all employee data is correctly populated

**Expected Result**: 
- Method returns List with exactly one EmployeeData object
- EmployeeData.empId == 101
- All employee fields are correctly populated from database

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case B2: Search by Employee ID - Non-Existent ID (PASS)
**Description**: Search for an employee using a non-existent employee ID
**Preconditions**: 
- Employee with ID 9999 does NOT exist
- HR Admin user is logged in

**Test Steps**:
1. Call `searchByEmployeeId(9999)`
2. Verify method returns empty List<EmployeeData>

**Expected Result**: 
- Method returns empty List (size == 0)
- No exceptions are thrown
- Method handles "not found" scenario gracefully

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case B3: Search by Name - Exact Match (PASS)
**Description**: Search for employees by exact name match
**Preconditions**: 
- Employees exist with names "John Smith" and "Jane Smith"
- HR Admin user is logged in

**Test Steps**:
1. Call `searchByName("John", "Smith")`
2. Verify method returns List with employee(s) matching "John Smith"
3. Verify case-insensitive matching works

**Expected Result**: 
- Method returns List containing employee with first_name="John" and last_name="Smith"
- Case-insensitive search works (e.g., "john", "JOHN" both work)
- Exact matches are returned

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case B4: Search by Name - Partial Match (PASS)
**Description**: Search for employees using partial name matching
**Preconditions**: 
- Employees exist with names "John Smith", "Johnny Smith", "Jane Doe"
- HR Admin user is logged in

**Test Steps**:
1. Call `searchByName("John", null)` to search by first name only
2. Verify method returns List with all employees having first name starting with "John"
3. Call `searchByName(null, "Smith")` to search by last name only
4. Verify method returns List with all employees having last name "Smith"

**Expected Result**: 
- Partial name search returns multiple employees when applicable
- Null parameters are handled (searches by available parameters)
- Wildcard matching works correctly

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case B5: Search by Department - Multiple Results (PASS)
**Description**: Search for all employees in a specific department
**Preconditions**: 
- Multiple employees exist in "Engineering" department
- HR Admin user is logged in

**Test Steps**:
1. Call `searchByDepartment("Engineering")`
2. Verify method returns List with all employees in Engineering department
3. Verify case-insensitive matching works

**Expected Result**: 
- Method returns List with all employees where department="Engineering"
- Case-insensitive search works (e.g., "engineering", "ENGINEERING" both work)
- All employees in the department are returned

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case B6: Search by Email - Valid Email (PASS)
**Description**: Search for an employee using email address
**Preconditions**: 
- Employee with email "john.smith@company.com" exists
- HR Admin user is logged in

**Test Steps**:
1. Call `searchByEmail("john.smith@company.com")`
2. Verify method returns List with one employee
3. Verify returned employee has the correct email

**Expected Result**: 
- Method returns List with exactly one EmployeeData object
- EmployeeData.email == "john.smith@company.com"
- Exact email match is found

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case B7: Search by Multiple Criteria - Advanced Search (PASS)
**Description**: Search for employees using multiple criteria simultaneously
**Preconditions**: 
- Employees exist with various combinations of department, salary, etc.
- HR Admin user is logged in

**Test Steps**:
1. Call `searchByMultipleCriteria()` with criteria:
   - department: "Engineering"
   - salary_min: 50000
   - salary_max: 80000
2. Verify method returns List with employees matching all criteria
3. Verify results are filtered correctly

**Expected Result**: 
- Method returns List with employees in Engineering department with salary between 50000 and 80000
- All criteria are applied as AND conditions
- Results are accurately filtered

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case B8: Search - SQL Injection Prevention (PASS)
**Description**: Verify that search methods prevent SQL injection attacks
**Preconditions**: 
- HR Admin user is logged in

**Test Steps**:
1. Call `searchByName("'; DROP TABLE employees; --", "Test")`
2. Verify method handles input safely
3. Verify no SQL injection occurs

**Expected Result**: 
- Method returns empty List or handles input safely
- No SQL injection occurs
- Employees table still exists and is accessible
- Malicious input is treated as literal string

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

### Test Cases for Requirement C: Update Salary for All Employees Below Threshold

#### Test Case C1: Bulk Salary Update - Valid Update (PASS)
**Description**: Update salaries for all employees earning less than threshold amount
**Preconditions**: 
- Employees exist with salaries: 30000, 40000, 50000, 60000, 70000
- HR Admin user is logged in
- Threshold: 55000
- New salary: 55000

**Test Steps**:
1. Call `updateSalariesBelowThreshold(55000, 55000)`
2. Verify method returns count of affected rows (should be 2: employees with 30000 and 40000)
3. Query database to verify:
   - Employees with salary < 55000 now have salary = 55000
   - Employees with salary >= 55000 remain unchanged

**Expected Result**: 
- Method returns 2 (number of employees updated)
- Employees with salaries 30000 and 40000 now have salary 55000
- Employees with salaries 50000, 60000, 70000 remain unchanged
- Transaction commits successfully

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case C2: Bulk Salary Update - No Employees Below Threshold (PASS)
**Description**: Attempt to update when no employees earn less than threshold
**Preconditions**: 
- All employees have salary >= 100000
- HR Admin user is logged in
- Threshold: 50000

**Test Steps**:
1. Call `updateSalariesBelowThreshold(50000, 60000)`
2. Verify method returns 0 (no employees updated)
3. Verify no database changes occurred

**Expected Result**: 
- Method returns 0
- No employee records are modified
- All salaries remain unchanged
- Appropriate message indicating no employees to update

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case C3: Bulk Salary Update - Invalid Threshold (Negative) (FAIL)
**Description**: Attempt to update with negative threshold amount
**Preconditions**: 
- HR Admin user is logged in

**Test Steps**:
1. Call `updateSalariesBelowThreshold(-1000, 50000)`
2. Verify method returns -1 or throws validation exception
3. Verify no database changes occurred

**Expected Result**: 
- Method returns -1 or throws ValidationException
- No employee records are modified
- Error message indicates threshold must be positive

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case C4: Bulk Salary Update - Invalid New Salary (Negative) (FAIL)
**Description**: Attempt to update with negative new salary amount
**Preconditions**: 
- HR Admin user is logged in

**Test Steps**:
1. Call `updateSalariesBelowThreshold(50000, -1000)`
2. Verify method returns -1 or throws validation exception
3. Verify no database changes occurred

**Expected Result**: 
- Method returns -1 or throws ValidationException
- No employee records are modified
- Error message indicates new salary must be positive

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case C5: Bulk Salary Update - New Salary Less Than Threshold (FAIL)
**Description**: Attempt to update where new salary is less than threshold (potential infinite loop scenario)
**Preconditions**: 
- Employees exist with salaries: 30000, 40000, 50000
- HR Admin user is logged in
- Threshold: 50000
- New salary: 40000

**Test Steps**:
1. Call `updateSalariesBelowThreshold(50000, 40000)`
2. Verify method returns -1 or throws validation exception
3. Verify no database changes occurred

**Expected Result**: 
- Method returns -1 or throws ValidationException
- No employee records are modified
- Error message indicates new salary should be >= threshold to avoid issues
- OR: Method handles this scenario by updating only employees currently below threshold (not creating infinite updates)

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case C6: Bulk Salary Update - Transaction Rollback on Error (PASS)
**Description**: Verify that transaction rolls back if an error occurs during bulk update
**Preconditions**: 
- Employees exist with salaries below threshold
- Database constraint that might cause error (e.g., salary cannot exceed certain value)
- HR Admin user is logged in

**Test Steps**:
1. Attempt to update salaries to a value that violates a database constraint
2. Verify transaction rolls back
3. Verify no partial updates occurred
4. Verify all employee salaries remain in original state

**Expected Result**: 
- Transaction rolls back on error
- No partial updates occurred (all-or-nothing)
- All employee salaries remain unchanged
- Error is logged appropriately

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case C7: Bulk Salary Update - Large Number of Employees (PASS)
**Description**: Update salaries for a large number of employees (performance test)
**Preconditions**: 
- 1000+ employees exist with salaries below threshold
- HR Admin user is logged in

**Test Steps**:
1. Call `updateSalariesBelowThreshold(50000, 55000)`
2. Measure execution time
3. Verify all employees are updated correctly
4. Verify transaction completes successfully

**Expected Result**: 
- All employees are updated correctly
- Transaction completes within reasonable time
- No timeouts occur
- Method returns correct count of affected rows

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

#### Test Case C8: Bulk Salary Update - Boundary Condition (Threshold Equals Existing Salary) (PASS)
**Description**: Test boundary condition where threshold equals some employees' current salary
**Preconditions**: 
- Employees exist with salaries: 30000, 50000, 50000, 70000
- HR Admin user is logged in
- Threshold: 50000

**Test Steps**:
1. Call `updateSalariesBelowThreshold(50000, 55000)`
2. Verify only employees with salary < 50000 are updated (salary 30000)
3. Verify employees with salary = 50000 are NOT updated
4. Verify employees with salary > 50000 are NOT updated

**Expected Result**: 
- Only employee with salary 30000 is updated to 55000
- Employees with salary 50000 remain at 50000 (not updated because condition is < not <=)
- Employees with salary 70000 remain at 70000
- Method returns 1 (one employee updated)

**Actual Result**: [To be filled during testing]
**Status**: PASS/FAIL

---

## Summary

### Test Coverage Summary
- **Requirement A (Update Employee Data)**: 6 test cases
- **Requirement B (Search Employee)**: 8 test cases
- **Requirement C (Bulk Salary Update)**: 8 test cases
- **Total Test Cases**: 22 test cases

### Test Execution Plan
1. Set up test database with sample data
2. Execute all test cases in order
3. Document actual results for each test case
4. Mark each test case as PASS or FAIL
5. Fix any failing test cases
6. Re-execute failed test cases until all pass

### Notes
- All test cases assume proper database setup and sample data
- Test cases should be executed in isolation where possible
- Database should be reset/cleaned between test runs if needed
- Consider using a test database separate from production

