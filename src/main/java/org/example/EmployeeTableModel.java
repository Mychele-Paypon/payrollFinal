package org.example;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {
    private final String[] columnNames = {
            "ID", "Name", "Position", "Daily Rate", "Days Present", "Days Absent"
    };

    private final List<Employee> employees;
    private final List<Payslip> payslips;

    public EmployeeTableModel() {
        this.employees = new ArrayList<>();
        this.payslips = new ArrayList<>();
    }

    public List<Employee> getEmployees(){
        return new ArrayList<>(employees);
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee emp = employees.get(rowIndex);
//        Payslip slip = payslips.get(rowIndex);

        switch (columnIndex) {
            case 0: return emp.getId();
            case 1: return emp.getName();
            case 2: return emp.getPosition();
            case 3: return emp.getDailySalary();
            case 4: return emp.getDaysPresent();
            case 5: return emp.getDaysAbsent();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
        fireTableDataChanged();
    }
    public void addAllEmployees(List<Employee> employees) {
        this.employees.addAll(employees);
        fireTableDataChanged();
    }

    public Employee getEmployeeAt(int rowIndex) {
        return employees.get(rowIndex);
    }

    public void removeEmployeeAt(int rowIndex) {
        employees.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    public void updateEmployeeAt(int rowIndex, Employee updatedEmployee) {
        employees.set(rowIndex, updatedEmployee);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
}

