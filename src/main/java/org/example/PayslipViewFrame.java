package org.example;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PayslipViewFrame extends JFrame {
    public PayslipViewFrame(Employee emp, Payslip payslip) {
        setTitle("Employee Payslip");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header panel
        JPanel headerPanel = createHeaderPanel(emp);

        // Details panel
        JPanel detailsPanel = createDetailsPanel(emp, payslip);

        // Deductions panel
        JPanel deductionsPanel = createDeductionsPanel(payslip);

        // Summary panel
        JPanel summaryPanel = createSummaryPanel(payslip);

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(deductionsPanel, BorderLayout.WEST);
        mainPanel.add(summaryPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
    private JPanel createHeaderPanel(Employee emp) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Employee Information"));

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String currentDate = LocalDate.now().format(dateFormat);

        panel.add(new JLabel("Date: " + currentDate));
        panel.add(new JLabel("Employee: " + emp.getName()));
        panel.add(new JLabel("ID: " + emp.getId()));
        panel.add(new JLabel("Position: " + emp.getPosition()));

        return panel;
    }

    private JPanel createDetailsPanel(Employee emp, Payslip payslip) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Earnings"));

        panel.add(new JLabel("Daily Rate:"));
        panel.add(new JLabel(String.format("₱%,.2f", emp.getDailySalary())));

        panel.add(new JLabel("Days Present:"));
        panel.add(new JLabel(String.format("%.1f", emp.getDaysPresent())));

        panel.add(new JLabel("Gross Salary:"));
        panel.add(new JLabel(String.format("₱%,.2f", payslip.getGrossSalary())));

        return panel;
    }

    private JPanel createDeductionsPanel(Payslip payslip) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Deductions"));

        panel.add(new JLabel("SSS:"));
        panel.add(new JLabel(String.format("₱%,.2f", payslip.getSss())));

        panel.add(new JLabel("PhilHealth:"));
        panel.add(new JLabel(String.format("₱%,.2f", payslip.getPhilHealth())));

        panel.add(new JLabel("Pag-IBIG:"));
        panel.add(new JLabel(String.format("₱%,.2f", payslip.getPagIbig())));

        panel.add(new JLabel("Withholding Tax:"));
        panel.add(new JLabel(String.format("₱%,.2f", payslip.getIncomeTax())));

        return panel;
    }

    private JPanel createSummaryPanel(Payslip payslip) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Summary"));

        double totalDeductions = payslip.getSss() + payslip.getPhilHealth()
                + payslip.getPagIbig() + payslip.getIncomeTax();

        panel.add(new JLabel("Total Deductions:"));
        panel.add(new JLabel(String.format("₱%,.2f", totalDeductions)));

        panel.add(new JLabel("Net Pay:"));
        panel.add(new JLabel(String.format("₱%,.2f", payslip.getNetPay())));

        return panel;
    }

}

