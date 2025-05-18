package org.example;

public class Payslip {
    private Employee employee;
    private double grossSalary, pagIbig, philHealth, sss, incomeTax, netPay;

    public Payslip(Employee employee) {
        this.employee = employee;
        grossSalary = employee.computeGrossSalary();

        pagIbig = Deductions.computePagIbig();
        philHealth = Deductions.computePhilHealth(grossSalary);
        sss = Deductions.computeSSS(grossSalary);
        incomeTax = Deductions.computeIncomeTax(grossSalary, pagIbig, philHealth, sss);

        double totalDeduction = pagIbig + philHealth + sss + incomeTax;
        netPay = grossSalary - totalDeduction;
    }

    public double getTotalDeductions() {
        return sss + philHealth + pagIbig + incomeTax;
    }

    // Getters
    public Employee getEmployee() { return employee; }
    public double getGrossSalary() { return grossSalary; }
    public double getPagIbig() { return pagIbig; }
    public double getPhilHealth() { return philHealth; }
    public double getSss() { return sss; }
    public double getIncomeTax() { return incomeTax; }
    public double getNetPay() { return netPay; }
}
