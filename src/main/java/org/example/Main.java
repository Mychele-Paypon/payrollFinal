package org.example;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        PayrollManager manager = new PayrollManager();
        GUIFrame frame = new GUIFrame(manager);
        frame.setTitle("Payroll System");

        try {
            FireStoreConnection firestore = new FireStoreConnection();
            List<Employee> employees = firestore.getAllEmployeesFromFirestore();
            frame.tableModel.addAllEmployees(employees);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame,
                    "Error loading initial data: " + e.getMessage(),
                    "Firestore Error", JOptionPane.ERROR_MESSAGE);
        }

        //ma gana man ni ayhan bisan indi ko ma access ang firebase?
        //..what
        //PLEASE LANG PLEASE
        //wait lang i test tanan!!!!!!


    }
}