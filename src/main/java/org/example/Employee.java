package org.example;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String id;
    private String name;
    private String position;
    private double dailySalary;
    private double daysPresent;
    private double daysAbsent;

    private List<Payslip> payslips = new ArrayList<>();


    public Employee(String id, String name, String position, double dailySalary, double daysPresent, double daysAbsent) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.dailySalary = dailySalary;
        this.daysPresent = daysPresent;
        this.daysAbsent = daysAbsent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getDailySalary() {
        return dailySalary;
    }

    public void setDailySalary(double dailySalary) {
        this.dailySalary = dailySalary;
    }

    public double getDaysPresent() {
        return daysPresent;
    }

    public void setDaysPresent(double daysPresent) {
        this.daysPresent = daysPresent;
    }

    public double getDaysAbsent() {
        return daysAbsent;
    }

    public void setDaysAbsent(double daysAbsent) {
        this.daysAbsent = daysAbsent;
    }

    public double computeGrossSalary() {
        return dailySalary * daysPresent;
    }

    public double getAnnualGrossSalary() {
        double monthlyGross = this.dailySalary * this.daysPresent;
        return monthlyGross * 12;
    }


    public double getAnnualTax() {
        if (payslips.isEmpty()) {
            Payslip tempPayslip = new Payslip(this);
            return tempPayslip.getIncomeTax() * 12;
        }
        return payslips.stream().mapToDouble(Payslip::getIncomeTax).sum();
    }
}
