package org.example;

import java.util.ArrayList;
import java.util.List;

public class PayrollManager {
    public List<Employee> employees;
    public List<Payslip> payslips;

    public PayrollManager() {
        employees = new ArrayList<>();
        payslips = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        Payslip payslip = new Payslip(employee); // This is where calculations happen
        payslips.add(payslip);

    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Payslip> getPayslips() {
        return payslips;
    }
}

