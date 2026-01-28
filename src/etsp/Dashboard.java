package etsp;

import javax.swing.*;
import java.awt.*;

public class Dashboard {
    public static void show(String username) {
        JFrame frame = new JFrame("Budget Dashboard - " + username.toUpperCase());
        frame.setSize(400, 350);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(250, 250, 255));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("Welcome, " + username);
        welcome.setBounds(100, 30, 300, 30);
        welcome.setFont(new Font("Arial", Font.BOLD, 16));

        JButton addIncome = new JButton("Add Income");
        addIncome.setBounds(100, 80, 200, 40);
        addIncome.setBackground(new Color(0, 123, 255));
        addIncome.setForeground(Color.WHITE);

        JButton addExpense = new JButton("Add Expense");
        addExpense.setBounds(100, 140, 200, 40);
        addExpense.setBackground(new Color(220, 53, 69));
        addExpense.setForeground(Color.WHITE);

        JButton viewBalance = new JButton("View Summary");
        viewBalance.setBounds(100, 200, 200, 40);
        viewBalance.setBackground(new Color(40, 167, 69));
        viewBalance.setForeground(Color.WHITE);

        frame.add(welcome);
        frame.add(addIncome);
        frame.add(addExpense);
        frame.add(viewBalance);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        addIncome.addActionListener(e -> IncomeManager.addIncomeScreen(username));
        addExpense.addActionListener(e -> ExpenseManager.addExpenseScreen(username));
        viewBalance.addActionListener(e -> {
            try {
                double income = IncomeManager.getTotalMonthlyIncome(username);
                double expense = ExpenseManager.getTotalMonthlyExpense(username);
                double balance = income - expense;
                JOptionPane.showMessageDialog(frame,
                        "💰 Income: ₹" + income +
                        "\n💸 Expenses: ₹" + expense +
                        "\n📊 Balance: ₹" + balance,
                        "Summary", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error fetching summary.");
            }
        });
    }
}
