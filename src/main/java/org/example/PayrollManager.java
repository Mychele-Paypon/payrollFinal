package org.example;

import java.util.ArrayList;
import java.util.List;

public class PayrollManager {
    public List<Employee> employees;
    public List<Payslip> payslips;

    public PayrollManager() {
        employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }


    public Payslip generatePayslip(Employee employee) {
        // Validate employee exists in the system first
        if (!employees.contains(employee)) {
            throw new IllegalArgumentException("Employee not found in payroll system");
        }

        // Create fresh payslip with current employee data
        return new Payslip(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Payslip> getPayslips() {
        return payslips;
    }
}

