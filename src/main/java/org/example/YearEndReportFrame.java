package org.example;

import javax.swing.*;
import java.awt.*;

public class YearEndReportFrame extends JFrame {
    public YearEndReportFrame(String reportText) {
        setTitle("Year-End Report");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea(reportText);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        setVisible(true);
    }
}

