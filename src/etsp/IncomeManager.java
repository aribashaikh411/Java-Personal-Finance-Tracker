package etsp;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class IncomeManager {

    public static void addIncomeScreen(String username) {
        JFrame frame = new JFrame("Add Income");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 280);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(245, 245, 255));  // subtle light background

        JLabel typeLabel = new JLabel("Income Type:");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        typeLabel.setBounds(30, 30, 100, 25);

        String[] types = {"Monthly", "Weekly", "Yearly"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        typeCombo.setBounds(140, 30, 150, 25);

        JLabel amountLabel = new JLabel("Amount (₹):");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        amountLabel.setBounds(30, 80, 100, 25);

        JTextField amountField = new JTextField();
        amountField.setBounds(140, 80, 150, 25);

        JButton addBtn = new JButton("Add Income");
        addBtn.setBounds(130, 140, 130, 40);
        addBtn.setBackground(new Color(0, 123, 255));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Arial", Font.BOLD, 14));
        addBtn.setFocusPainted(false);

        // Optional: use a default icon, or remove if you don't have a custom one
        Icon plusIcon = UIManager.getIcon("FileView.fileIcon"); 
        addBtn.setIcon(plusIcon);
        addBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
        addBtn.setIconTextGap(10);

        frame.add(typeLabel);
        frame.add(typeCombo);
        frame.add(amountLabel);
        frame.add(amountField);
        frame.add(addBtn);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        addBtn.addActionListener(e -> {
            String type = ((String) typeCombo.getSelectedItem()).toLowerCase();
            String amountText = amountField.getText().trim();

            try {
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Amount must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                addIncome(username, type, amount);

                JOptionPane.showMessageDialog(frame, "Income added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                amountField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error writing to file.", "File Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void addIncome(String username, String type, double amount) throws IOException {
        String fileName = "income_" + username + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(type + "," + amount);
            bw.newLine();
        }
    }

    public static double getTotalMonthlyIncome(String username) throws IOException {
        String fileName = "income_" + username + ".txt";
        File file = new File(fileName);
        if (!file.exists()) {
            return 0;  // No income recorded yet
        }

        double total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 2) continue;  // skip malformed lines

                String type = data[0].trim();
                double amount;
                try {
                    amount = Double.parseDouble(data[1].trim());
                } catch (NumberFormatException e) {
                    continue; // skip invalid amount lines
                }

                switch (type.toLowerCase()) {
                    case "yearly":
                        total += amount / 12;  // convert yearly to monthly
                        break;
                    case "weekly":
                        total += amount * 4;   // approximate 4 weeks per month
                        break;
                    case "monthly":
                        total += amount;
                        break;
                }
            }
        }
        return total;
    }
}
