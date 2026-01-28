package etsp;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ExpenseManager {
    public static void addExpenseScreen(String username) {
        JFrame frame = new JFrame("Add Expense");
        frame.setSize(400, 280);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(255, 240, 245));

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        categoryLabel.setBounds(30, 30, 100, 25);

        String[] categories = {"Food", "Transport", "Shopping", "Bills", "Other"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        categoryCombo.setBounds(140, 30, 150, 25);

        JLabel amountLabel = new JLabel("Amount (₹):");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        amountLabel.setBounds(30, 80, 100, 25);

        JTextField amountField = new JTextField();
        amountField.setBounds(140, 80, 150, 25);

        JButton addBtn = new JButton("Add Expense");
        addBtn.setBounds(130, 140, 130, 40);
        addBtn.setBackground(new Color(255, 87, 34));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Arial", Font.BOLD, 14));

        frame.add(categoryLabel);
        frame.add(categoryCombo);
        frame.add(amountLabel);
        frame.add(amountField);
        frame.add(addBtn);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        addBtn.addActionListener(e -> {
            String category = ((String) categoryCombo.getSelectedItem()).toLowerCase();
            String amountText = amountField.getText();

            try {
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Amount must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                addExpense(username, category, amount);
                JOptionPane.showMessageDialog(frame, "Expense added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                amountField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving expense.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void addExpense(String username, String category, double amount) throws IOException {
        String fileName = "expense_" + username + ".txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
        bw.write(category + "," + amount);
        bw.newLine();
        bw.close();
    }

    public static double getTotalMonthlyExpense(String username) throws IOException {
        String fileName = "expense_" + username + ".txt";
        File file = new File(fileName);
        if (!file.exists()) return 0;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        double total = 0;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 2) {
                try {
                    total += Double.parseDouble(data[1]);
                } catch (NumberFormatException ignored) {}
            }
        }
        br.close();
        return total;
    }
}
