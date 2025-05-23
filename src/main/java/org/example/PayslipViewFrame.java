package org.example;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.awt.Font.*;

public class PayslipViewFrame extends JFrame {

    JLabel employeeInformation;
    JLabel dateIssued;
    JLabel employeeName;
    JLabel employeeID;
    JLabel position;
    JLabel deductionLabel;
    JLabel sssLabel, philHealth, pagIbig, incomeTax;
    JLabel earningLabel;
    JLabel dailyRate, grossIncome, daysPresent;
    JLabel employer, employee;
    JLabel summaryLabel;

    Container container;
    GridBagLayout gridBagLayout;

    JTextArea deductionArea;
    JTextArea earningArea;
    JTextArea summaryArea;

    public PayslipViewFrame(Employee emp, Payslip payslip) {
        setTitle("Employee Payslip");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //instantiate ang comps
        container = getContentPane();
        gridBagLayout = new GridBagLayout();
        container.setLayout(gridBagLayout);
        employeeInformation = new JLabel("Employee Information ===================================");

        // for date issued
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String currentDate = LocalDate.now().format(dateFormat);
        dateIssued = new JLabel("Date Issued: " + currentDate);
            dateIssued.setFont(new Font(SANS_SERIF, PLAIN, 12));

        employeeName = new JLabel("Employee: " + emp.getName());
            employeeName.setFont(new Font(SANS_SERIF, PLAIN, 12));
        employeeID = new JLabel("ID: " + emp.getId());
            employeeID.setFont(new Font(SANS_SERIF, PLAIN, 12));
        position = new JLabel("Position: " + emp.getPosition());
            position.setFont(new Font(SANS_SERIF, PLAIN, 12));

        deductionLabel = new JLabel("Deductions");
        sssLabel = new JLabel("SSS: ₱" + payslip.getSss());
        philHealth = new JLabel("PhilHealth: ₱" + payslip.getPhilHealth());
        pagIbig = new JLabel("Pag-IBIG: ₱" + payslip.getPagIbig());
        incomeTax = new JLabel("Income Tax: " + payslip.getIncomeTax());
        deductionArea = new JTextArea("", 5,18);
        deductionArea.setEditable(false);

        earningLabel = new JLabel("Earnings");
        dailyRate = new JLabel("Daily Rate: " + emp.getDailySalary());
        grossIncome = new JLabel("Gross Income: " + payslip.getGrossSalary());
        daysPresent = new JLabel("Days Present: " + emp.getDaysPresent());
        earningArea = new JTextArea("", 5,18);
        earningArea.setEditable(false);

        summaryLabel = new JLabel("Summary");
        summaryArea = new JTextArea("", 5,10);

        employer = new JLabel("Employer Signature:___________");
        employee = new JLabel("Employee Signature:___________");


        // layout
        addToContainer(container, employeeInformation, 0,0,2,1);
        addToContainer(container, dateIssued,1,1);
        addToContainer(container, employeeName, 0,1);
        addToContainer(container, employeeID, 0,2);
        addToContainer(container, position, 0,3);

        addToContainer(container, new JLabel("====================================================="),0,4,2,1);

        addToContainer(container, deductionLabel, 0, 5);
        addToContainer(container, new JScrollPane(deductionArea),0,6);
//            deductionArea.append(sssLabel.getText() + "\n");
//            deductionArea.append(philHealth.getText() + "\n");
//            deductionArea.append(pagIbig.getText() + "\n");
            deductionArea.append(String.format("SSS: ₱%.2f\n", payslip.getSss()));
            deductionArea.append(String.format("PhilHealth: ₱%.2f\n", payslip.getPhilHealth()));
            deductionArea.append(String.format("Pag-IBIG: ₱%.2f\n", payslip.getPagIbig()));
            deductionArea.append("\n");
            deductionArea.append(String.format("Income Tax: ₱%.2f", payslip.getIncomeTax()));

        addToContainer(container, earningLabel, 1, 5);
        addToContainer(container, new JScrollPane(earningArea),1,6);
//        earningArea.append(dailyRate.getText() + "\n");
//        earningArea.append(daysPresent.getText() + "\n");
//        earningArea.append(grossIncome.getText());
        earningArea.append(String.format("Daily Rate: ₱%.2f\n", emp.getDailySalary()));
        earningArea.append(String.format("Days Present: %.0f\n", emp.getDaysPresent()));
        earningArea.append(String.format("Gross Income: ₱%.2f\n", payslip.getGrossSalary()));

        addToContainer(container, new JLabel("====================================================="),0,7,2,1);

        addToContainer(container, summaryLabel, 0, 8);
        addToContainer(container, new JScrollPane(summaryArea), 0,9,2,1);
            summaryArea.append(String.format("Total Deductions: ₱%.2f\n", payslip.getTotalDeductions()));
            summaryArea.append(String.format("Net Pay: ₱%.2f\n", payslip.getNetPay()));

        addToContainer(container, employer, 0,10);
        addToContainer(container, employee, 1,10);


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

