package org.example;

public class Payslip {
    private Employee employee;
    private double grossSalary, pagIbig, philHealth, sss, incomeTax, netPay;

    public Payslip(Employee employee) {
        this.employee = employee;
        this.grossSalary = getGrossSalary();
        this.pagIbig = getPagIbig();
        this.philHealth = getPhilHealth();
        this.sss = getSss();
        this.incomeTax = getIncomeTax();
        this.netPay = getNetPay();
    }

//    public Payslip(Employee employee) {
//        this.employee = employee;
//        grossSalary = employee.computeGrossSalary();
//
//        pagIbig = Deductions.computePagIbig();
//        philHealth = Deductions.computePhilHealth(grossSalary);
//        sss = Deductions.computeSSS(grossSalary);
//        incomeTax = Deductions.computeIncomeTax(grossSalary, pagIbig, philHealth, sss);
//
//        double totalDeduction = pagIbig + philHealth + sss + incomeTax;
//        netPay = grossSalary - totalDeduction;
//    }

    public double getTotalDeductions() {
        return sss + philHealth + pagIbig + incomeTax;
    }

    public Employee getEmployee() {
        return employee; }
    public double getGrossSalary() {
        return employee.getDailySalary() * employee.getDaysPresent();
    }
    public double getPagIbig() {
        return Deductions.computePagIbig();
    }
    public double getPhilHealth() {
        return Deductions.computePhilHealth(grossSalary);
    }
    public double getSss() {
        return Deductions.computeSSS(grossSalary);
    }
    public double getIncomeTax() {
        return Deductions.computeIncomeTax(grossSalary, pagIbig, philHealth, sss);
    }
    public double getNetPay() {
        return grossSalary - (sss + philHealth + pagIbig + incomeTax);
    }
}
