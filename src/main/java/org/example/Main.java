package org.example;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

            PayrollManager manager = new PayrollManager();
            GUIFrame frame = new GUIFrame(manager);
            frame.setTitle("Payroll System");

        try {
             new FireStoreConnection(); // One-time init
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}