package org.example;

import java.util.ArrayList;
import java.util.List;

public class PayrollManager {
    public List<Employee> employees;
    public List<Payslip> payslips;

    public PayrollManager() {
        employees = new ArrayList<>();
//        payslips = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
//        printPayslip(employee);
    }
//    public void updatePayslip(Employee updatedEmployee) {
//        int index = employees.indexOf(updatedEmployee);
//        if (index >= 0) {
//            payslips.set(index, new Payslip(updatedEmployee));
//        }
//    }
//    public Payslip getPayslipForEmployee(Employee employee) {
//        int index = employees.indexOf(employee);
//        return (index >= 0 && index < payslips.size()) ? payslips.get(index) : null;
//    }

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

