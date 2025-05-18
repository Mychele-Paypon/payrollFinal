package org.example;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PayrollTableModel extends AbstractTableModel {

    private final String[] columns = {
            "ID", "Name", "Position", "Daily Rate", "Days Present",
            "Gross Pay", "Pag-IBIG", "PhilHealth", "SSS", "Income Tax", "Deductions", "Net Pay"
    };
    private List<Payslip> payslips;

    public PayrollTableModel(List<Payslip> payslips) {
        this.payslips = payslips;
    }

    @Override
    public int getRowCount() {
        return payslips.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Payslip p = payslips.get(row);
        Employee e = p.getEmployee();
        return switch (col) {
            case 0 -> e.getId();
            case 1 -> e.getName();
            case 2 -> e.getPosition();
            case 3 -> e.getDailySalary();
            case 4 -> e.getDaysPresent();
            case 5 -> p.getGrossSalary();
            case 6 -> p.getPagIbig();
            case 7 -> p.getPhilHealth();
            case 8 -> p.getSss();
            case 9 -> p.getIncomeTax();
            case 10 -> p.getTotalDeductions();
            case 11 -> p.getNetPay();
            default -> null;
        };
    }
}

