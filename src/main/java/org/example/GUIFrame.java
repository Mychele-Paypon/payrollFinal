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
    JLabel IdLabel, nameLabel, positionLabel, dailySalaryLabel, days_present_Label, daysAbsentLabel;
    JTextField idField, nameField, positionField, dailySalaryField, daysPresentField, daysAbsentField;
    PayrollManager payrollManager;
    GridBagLayout layout;
    JButton payslipButton, reportButton;
    JButton addButton, deleteButton, editButton;
    JTable employeeTable;
    EmployeeTableModel tableModel;
    private Employee employeeBeingEdited = null;

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
        tableModel = new EmployeeTableModel();
        employeeTable = new JTable(tableModel);
        payslipButton = new JButton("Payslip");
        reportButton = new JButton("Year-End Report");
        IdLabel = new JLabel("Employee ID: ");
        nameLabel = new JLabel("Name: ");
        positionLabel = new JLabel("Position: ");
        dailySalaryLabel = new JLabel("Daily Rate");
        days_present_Label = new JLabel("Days Present: ");
        daysAbsentLabel = new JLabel("Days Absent: ");
        daysAbsentField = new JTextField(20);
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        editButton = new JButton("Update");


        addToCon(container, IdLabel, 0, 0);
        addToCon(container, idField, 1, 0, 1, 1);

        addToCon(container, daysAbsentLabel, 2, 0);
        addToCon(container, daysAbsentField, 3, 0, 1, 1);

        addToCon(container, nameLabel, 0, 1);
        addToCon(container, nameField, 1, 1, 1, 1);

        addToCon(container, positionLabel, 0, 2);
        addToCon(container, positionField, 1, 2, 1, 1);

        addToCon(container, dailySalaryLabel, 2, 2);
        addToCon(container, dailySalaryField, 3, 2, 1, 1);

        addToCon(container, days_present_Label, 2, 1);
        addToCon(container, daysPresentField, 3, 1, 1, 1);

        addToCon(container, payslipButton, 0, 5);
        addToCon(container, reportButton, 1, 5);
        addToCon(container, addButton, 2, 5, 1, 1);
        addToCon(container, deleteButton, 3, 5, 1, 1);
        addToCon(container, editButton, 4, 5, 1, 1);
        addToCon(container, new JScrollPane(employeeTable), 0, 6, 5, 5);


        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.payrollManager = manager;

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    String id = idField.getText();
                    String position = positionField.getText();
                    double dailySalary = Double.parseDouble(dailySalaryField.getText());
                    double daysPresent = Double.parseDouble(daysPresentField.getText());
                    double daysAbsent = Double.parseDouble(daysAbsentField.getText());

                    if (name.isEmpty() || id.isEmpty() || position.isEmpty()) {
                        throw new IllegalArgumentException("Please fill in all text fields");
                    }

                    Employee employee = new Employee(id, name, position, dailySalary, daysPresent, daysAbsent);
                    Payslip payslip = new Payslip(employee);

                    FireStoreConnection firestore = new FireStoreConnection();
                    firestore.addEmployeeToFirestore(employee, payslip);
                    tableModel.addEmployee(employee);


                    JOptionPane.showMessageDialog(null, "Employee added and uploaded to Firebase!");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to add employee: " + ex.getMessage());
                }
                clearInputFields();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = employeeTable.getSelectedRows();

                if (selectedRows.length == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Please select at least one employee to delete",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    FireStoreConnection firestore = new FireStoreConnection();
                    EmployeeTableModel model = (EmployeeTableModel) employeeTable.getModel();

                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        int modelRow = employeeTable.convertRowIndexToModel(selectedRows[i]);
                        Employee employee = model.getEmployeeAt(modelRow);
                        firestore.deleteEmployeeFromFirestore(employee.getId());
                        model.removeEmployeeAt(modelRow);
                    }

                    JOptionPane.showMessageDialog(null,
                            "Successfully deleted " + selectedRows.length + " employee(s)",
                            "Deletion Complete", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Error deleting employees: " + ex.getMessage(),
                            "Deletion Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editButton.getText().equals("Update")) {
                    int selectedRow = employeeTable.getSelectedRow();

                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(null,
                                "Please select an employee to edit",
                                "No Selection", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int modelRow = employeeTable.convertRowIndexToModel(selectedRow);
                    employeeBeingEdited = tableModel.getEmployeeAt(modelRow);

                    // Pre-fill fields
                    idField.setText(employeeBeingEdited.getId());
                    nameField.setText(employeeBeingEdited.getName());
                    positionField.setText(employeeBeingEdited.getPosition());
                    dailySalaryField.setText(String.valueOf(employeeBeingEdited.getDailySalary()));
                    daysPresentField.setText(String.valueOf(employeeBeingEdited.getDaysPresent()));
                    daysAbsentField.setText(String.valueOf(employeeBeingEdited.getDaysAbsent()));

                    idField.setEnabled(false);
                    editButton.setText("Save");

                } else {
                    try {
                        int selectedRow = employeeTable.getSelectedRow();
                        if (selectedRow == -1) throw new Exception("No employee selected");

                        int modelRow = employeeTable.convertRowIndexToModel(selectedRow);
                        Employee currentEmployee = tableModel.getEmployeeAt(modelRow);

                        Employee updatedEmployee = new Employee(
                                currentEmployee.getId(), // ID remains the same
                                nameField.getText(),
                                positionField.getText(),
                                Double.parseDouble(dailySalaryField.getText()),
                                Double.parseDouble(daysPresentField.getText()),
                                Double.parseDouble(daysAbsentField.getText())
                        );

                        // Update in Firestore
                        FireStoreConnection firestore = new FireStoreConnection();
                        Payslip payslip = new Payslip(updatedEmployee);
                        firestore.updateEmployeeInFirestore(updatedEmployee, payslip);

                        // Update in local table
                        tableModel.updateEmployeeAt(modelRow, updatedEmployee);

                        clearInputFields();
                        editButton.setText("Update");
                        idField.setEnabled(true);

                        JOptionPane.showMessageDialog(null,
                                "Employee updated successfully!",
                                "Success", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                                "Error updating employee: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        payslipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        int modelRow = employeeTable.convertRowIndexToModel(selectedRow);
                        Employee emp = tableModel.getEmployeeAt(modelRow);
                        Payslip payslip = new Payslip(emp);

                        new PayslipViewFrame(emp, payslip).setVisible(true);

                    } catch (IndexOutOfBoundsException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Employee data is incomplete. Please check employee records.",
                                "Data Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "Error generating payslip: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Please select an employee first",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
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

    private void clearInputFields() {
        idField.setText("");
        nameField.setText("");
        dailySalaryField.setText("");
        positionField.setText("");
        daysPresentField.setText("");
        daysAbsentField.setText("");
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
