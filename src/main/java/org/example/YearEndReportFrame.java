package org.example;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.awt.Font.PLAIN;
import static java.awt.Font.SANS_SERIF;


public class YearEndReportFrame extends JFrame {

    JLabel yearEndReportLabel;
    JTextArea yearEndReportArea;
    JLabel summaryLabel;
    JTextArea summaryArea;
    JLabel dateIssued;
    JLabel empListLabel;
    JLabel totalTaxLabel;
    String lineDivider;


    Container container;
    GridBagLayout gridBagLayout;

    public YearEndReportFrame(List<Employee> employees) {
        setTitle("Year-End Tax Report");
        setSize(425, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // initialize compos
        container = getContentPane();
        gridBagLayout = new GridBagLayout();
        container.setLayout(gridBagLayout);

        lineDivider = new String("--------------------------------------------------------------------------------------------");

        yearEndReportLabel = new JLabel("Year-End Report =======================================");
        yearEndReportArea = new JTextArea("", 15,18);
            yearEndReportArea.setEditable(false);

        summaryLabel = new JLabel("Summary");
        summaryArea = new JTextArea("", 5,18);
            summaryArea.setEditable(false);

        totalTaxLabel = new JLabel("", JLabel.RIGHT);
        empListLabel = new JLabel("Employee List:");
            empListLabel.setFont(new Font(SANS_SERIF, PLAIN, 12));

        // for date issued
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String currentDate = LocalDate.now().format(dateFormat);
        dateIssued = new JLabel("Date Issued: " + currentDate, JLabel.RIGHT);
        dateIssued.setFont(new Font(SANS_SERIF, PLAIN, 12));

        // Layout ang GUI

        // year-end report area
        addToContainer(container, yearEndReportLabel, 0, 0,3,1);
        addToContainer(container, empListLabel, 0,1);
        addToContainer(container, dateIssued, 2,1);
        addToContainer(container, new JScrollPane(yearEndReportArea), 0, 2,3,15);

        // check if may unod ang database
        if (employees == null || employees.isEmpty()) {
            yearEndReportArea.setText("No employee data available.");
            return;
        }

        double grossTotalAllEmp = 0;
        double incomeTaxAllEmp = 0;
        double taxTotalAllEmp = 0;
        int employeeNum = 1;

        // for end report area
        for (int i = 0; i < employees.size(); i++){
            Payslip payslip = new Payslip(employees.get(i));

            double grossIncome = employees.get(i).getAnnualGrossSalary();
            double deductions = payslip.getTotalDeductions() - payslip.getIncomeTax();
            double incomeTax = grossIncome - deductions;
            double annualTax = employees.get(i).getAnnualTax();

            yearEndReportArea.append("#" + employeeNum + "\n");
            yearEndReportArea.append("Employee: " + employees.get(i).getName() + "\n");
            yearEndReportArea.append("ID: " + employees.get(i).getId() + "\n");
            yearEndReportArea.append(String.format("Gross Income: ₱%,.2f\n", grossIncome));
            yearEndReportArea.append(String.format("Income Tax: ₱%,.2f\n", incomeTax));
            yearEndReportArea.append(String.format("Annual Tax: ₱%,.2f\n", annualTax));
            yearEndReportArea.append("\n");
            yearEndReportArea.append(lineDivider + "\n");
            yearEndReportArea.append("\n");

            employeeNum++;
            grossTotalAllEmp += grossIncome;
            incomeTaxAllEmp += incomeTax;
            taxTotalAllEmp += annualTax;
        }

        // for summary area
        addToContainer(container, new JLabel("====================================================="), 0,17,3,1);
        addToContainer(container, summaryLabel, 0,18);
        addToContainer(container, summaryArea, 0,19,3,4);
            summaryArea.append(String.format("Total Gross Income: ₱%,.2f\n", grossTotalAllEmp));
            summaryArea.append(String.format("Total Income Tax: ₱%,.2f\n", incomeTaxAllEmp));
            summaryArea.append(String.format("Total Tax Liability: ₱%,.2f\n", taxTotalAllEmp));

        addToContainer(container, totalTaxLabel, 2,23);
        totalTaxLabel.setText(String.format("Company Total Tax: P%,.2f", taxTotalAllEmp));


        setVisible(true);

    }

    public void addToContainer(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = GridBagConstraints.BOTH;

        add(component, constraints);

    }

    public void addToContainer(Container container, Component component, int gridx, int gridy){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;

        add(component, constraints);

    }
}
