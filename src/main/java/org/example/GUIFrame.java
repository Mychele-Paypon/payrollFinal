package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUIFrame extends JFrame {
    JLabel IdLabel, nameLabel, positionLabel, dailysalaryLabel, days_present_Label;
    JTextField idField, nameField, positionField, dailySalaryField, daysPresentField;
    JTextArea outputArea;
    PayrollManager payrollManager;
    GridBagLayout layout;
    JButton computeButton, reportButton, payslipButton;
    JTable employeeTable;
    JScrollPane tableScrollPane;
    public GUIFrame(PayrollManager manager) {
        Container container = new Container();
        layout = new GridBagLayout();
        container = getContentPane();
        container.setLayout(layout);

        idField = new JTextField(20);
        nameField = new JTextField(20);
        positionField = new JTextField(20);
        dailySalaryField = new JTextField(20);
        daysPresentField = new JTextField(20);
        employeeTable = new JTable();
        tableScrollPane = new JScrollPane(employeeTable);
        tableScrollPane.setPreferredSize(new Dimension(1100,300));
        computeButton = new JButton("Compute Payroll");
        reportButton = new JButton("Year-End Report");
        IdLabel = new JLabel("Employee ID: ");
        nameLabel = new JLabel("Name: ");
        positionLabel = new JLabel("Position: ");
        dailysalaryLabel = new JLabel("Daily Salary");
        days_present_Label = new JLabel("Days present: ");
        payslipButton = new JButton("View Selected Payslip");
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);


        addToCon(container, IdLabel, 0, 0);
        addToCon(container, idField, 1, 0,2,1);

        addToCon(container, nameLabel, 0, 1);
        addToCon(container, nameField, 1, 1,2,1);

        addToCon(container, positionLabel, 0, 2);
        addToCon(container, positionField, 1, 2,2,1);

        addToCon(container, dailysalaryLabel, 0, 3);
        addToCon(container, dailySalaryField, 1, 3,2,1);

        addToCon(container, days_present_Label, 0, 4);
        addToCon(container, daysPresentField, 1, 4,2,1);

        addToCon(container, computeButton, 0, 5);
        addToCon(container, reportButton, 1, 5);
        addToCon(container, tableScrollPane, 0, 6, 5, 5);
        addToCon(container, payslipButton, 0, 13, 2, 1);
        addToCon(container, new JScrollPane(outputArea), 0, 6, 2, 3);


        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.payrollManager = manager;


        computeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                computePayroll();
            }
        });

        payslipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Payslip payslip = payrollManager.getPayslips().get(selectedRow);
                    Employee emp = payrollManager.getEmployees().get(selectedRow);

                    StringBuilder sb = new StringBuilder();
                    sb.append("=============================================\n");
                    sb.append("             EMPLOYEE PAYSLIP\n");
                    sb.append("=============================================\n");
                    sb.append("Employee: ").append(emp.getName()).append("\n");
                    sb.append("ID: ").append(emp.getId()).append("\n");
                    sb.append("Position: ").append(emp.getPosition()).append("\n\n");
                    sb.append(String.format("Daily Rate: ₱%,.2f\n", emp.getDailySalary()));
                    sb.append(String.format("Days Present: %.1f\n", emp.getDaysPresent()));
                    sb.append("---------------------------------------------\n");
                    sb.append(String.format("Gross Salary: ₱%,.2f\n", payslip.getGrossSalary()));
                    sb.append(String.format("SSS: ₱%,.2f\n", payslip.getSss()));
                    sb.append(String.format("PhilHealth: ₱%,.2f\n", payslip.getPhilHealth()));
                    sb.append(String.format("Pag-IBIG: ₱%,.2f\n", payslip.getPagIbig()));
                    sb.append(String.format("Withholding Tax: ₱%,.2f\n", payslip.getIncomeTax()));
                    sb.append(String.format("Total Deductions: ₱%,.2f\n",
                            payslip.getSss() + payslip.getPhilHealth() + payslip.getPagIbig() + payslip.getIncomeTax()));
                    sb.append(String.format("Net Pay: ₱%,.2f\n", payslip.getNetPay()));
                    sb.append("=============================================\n");

                    new PayslipViewFrame(sb.toString());  // show in new window
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an employee from the table.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        reportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Payslip> payslips = payrollManager.getPayslips();
                double totalGross = 0, totalPagIbig = 0, totalPhilHealth = 0, totalSSS = 0, totalTax = 0, totalNet = 0;

                for (Payslip slip : payslips) {
                    totalGross += slip.getGrossSalary();
                    totalPagIbig += slip.getPagIbig();
                    totalPhilHealth += slip.getPhilHealth();
                    totalSSS += slip.getSss();
                    totalTax += slip.getIncomeTax();
                    totalNet += slip.getNetPay();
                }

                StringBuilder report = new StringBuilder();
                report.append("=============================================\n");
                report.append("              YEAR-END REPORT\n");
                report.append("=============================================\n");
                report.append(String.format("Total Gross Pay: ₱%,.2f\n", totalGross));
                report.append(String.format("Total Pag-IBIG Contributions: ₱%,.2f\n", totalPagIbig));
                report.append(String.format("Total PhilHealth Contributions: ₱%,.2f\n", totalPhilHealth));
                report.append(String.format("Total SSS Contributions: ₱%,.2f\n", totalSSS));
                report.append(String.format("Total Income Tax: ₱%,.2f\n", totalTax));
                report.append(String.format("Total Net Pay: ₱%,.2f\n", totalNet));
                report.append("=============================================\n");

                new YearEndReportFrame(report.toString()); // display on a new frame
            }
        });

    }


    private void computePayroll() {
        try {
            String id = idField.getText();
            String name = nameField.getText();
            String position = positionField.getText();
            double dailySalary = Double.parseDouble(dailySalaryField.getText());
            double daysPresent = Double.parseDouble(daysPresentField.getText());

            Employee employee = new Employee(id, name, position, dailySalary, daysPresent);
            payrollManager.addEmployee(employee);

            // 🔹 Create payslip
            Payslip payslip = new Payslip(employee);

            // 🔹 Upload to Firestore
            try {
                FireStoreConnection firestore = new FireStoreConnection();
                firestore.addEmployee(employee, payslip); // Save to Firestore

                // 🔹 Now fetch all records again and update the table
                List<Object[]> records = firestore.getPayrollRecords();

                String[] columnNames = {
                        "ID", "Name", "Position", "Daily Salary", "Days Present",
                        "Gross Pay", "Pag-IBIG", "PhilHealth", "SSS", "Income Tax",
                        "Deductions", "Net Pay"
                };

                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                for (Object[] row : records) {
                    model.addRow(row);
                }
                employeeTable.setModel(model);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error uploading or fetching data from Firestore.");
            }

            clearInputFields();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for salary and days present.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearInputFields() {
        idField.setText("");
        nameField.setText("");
        dailySalaryField.setText("");
        positionField.setText("");
        daysPresentField.setText("");
    }

    private void loadPayrollFromFirestore() {
        try {
            FireStoreConnection firestore = new FireStoreConnection();
            List<Object[]> records = firestore.getPayrollRecords();

            String[] columnNames = {
                    "ID", "Name", "Position", "Daily Salary", "Days Present",
                    "Gross Pay", "Pag-IBIG", "PhilHealth", "SSS", "Income Tax",
                    "Deductions", "Net Pay"
            };

            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (Object[] row : records) {
                model.addRow(row);
            }

            employeeTable.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load payroll data from Firestore.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addToCon(Container container, Component component, int gridx, int gridy){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        container.add(component, constraints);
    }
    public void addToCon(Container container, Component component, int gridx, int gridy, int width, int height){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.fill = GridBagConstraints.BOTH;
        container.add(component, constraints);
    }
}
