package org.example;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class YearEndReportFrame extends JFrame {
    JTextArea reportArea;
    JLabel totalTaxLabel;
    public YearEndReportFrame(List<Employee> employees) {
        setTitle("Year-End Tax Report");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        generateReport(employees);
        setVisible(true);

    }
    private void initComponents() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // Initialize reportArea BEFORE using it
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(reportArea);
        container.add(scrollPane, BorderLayout.CENTER);

        // Initialize totalTaxLabel
        totalTaxLabel = new JLabel("", JLabel.RIGHT);
        totalTaxLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        container.add(totalTaxLabel, BorderLayout.SOUTH);
    }
    private void generateReport(List<Employee> employees) {
        // Now reportArea is guaranteed to be initialized
        if (employees == null || employees.isEmpty()) {
            reportArea.setText("No employee data available.");
            return;
        }

        StringBuilder report = new StringBuilder();
        report.append("Year-End Tax Report\n\n");

        double totalGrossAll = 0;
        double totalTaxableAll = 0;
        double totalTaxAll = 0;

        for (Employee emp : employees) {
            double grossIncome = emp.getAnnualGrossSalary();
            double deductions = new Payslip(emp).getTotalDeductions() - new Payslip(emp).getIncomeTax(); // Exclude tax from deductions
            double taxableIncome = grossIncome - deductions;
            double annualTax = emp.getAnnualTax();

            totalGrossAll += grossIncome;
            totalTaxableAll += taxableIncome;
            totalTaxAll += annualTax;

            report.append(String.format("Employee: %s (ID: %s)\n", emp.getName(), emp.getId()));
            report.append(String.format("Gross Income: P%,.2f\n", grossIncome));
            report.append(String.format("Taxable Income: P%,.2f\n", taxableIncome));
            report.append(String.format("Total Annual Tax: P%,.2f\n\n", annualTax));
        }

        // Add summary section
        report.append("\nSUMMARY\n");
        report.append("----------------------------\n");
        report.append(String.format("Total Gross Income (All Employees): P%,.2f\n", totalGrossAll));
        report.append(String.format("Total Taxable Income: P%,.2f\n", totalTaxableAll));
        report.append(String.format("Total Tax Liability: P%,.2f\n", totalTaxAll));

        reportArea.setText(report.toString());
        totalTaxLabel.setText(String.format("Company Total Tax: P%,.2f", totalTaxAll));
    }
}
