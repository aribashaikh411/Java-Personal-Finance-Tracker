package etsp;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class LoginManager {
    private static Set<String> registeredUsers = new HashSet<>();

    public static void showLoginScreen() {
        JFrame frame = new JFrame("Login");
        frame.setSize(350, 250);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(230, 245, 255));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 80, 25);
        JTextField userField = new JTextField();
        userField.setBounds(140, 50, 150, 25);

        ((AbstractDocument) userField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) {
                    super.replace(fb, offset, length, text, attrs);
                    return;
                }
                StringBuilder builder = new StringBuilder();
                for (char c : text.toCharArray()) {
                    if (Character.isLetter(c)) { // Only allow letters
                        builder.append(c);
                    }
                }
                super.replace(fb, offset, length, builder.toString(), attrs);
            }

            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) {
                    super.insertString(fb, offset, string, attr);
                    return;
                }
                StringBuilder builder = new StringBuilder();
                for (char c : string.toCharArray()) {
                    if (Character.isLetter(c)) { // Only allow letters
                        builder.append(c);
                    }
                }
                super.insertString(fb, offset, builder.toString(), attr);
            }
        });

        JButton loginBtn = new JButton("Login / Sign Up");
        loginBtn.setBounds(100, 100, 130, 40);
        loginBtn.setBackground(new Color(76, 175, 80));
        loginBtn.setForeground(Color.WHITE);

        frame.add(userLabel);
        frame.add(userField);
        frame.add(loginBtn);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim().toLowerCase();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a username.");
                return;
            }
            if (!username.matches("[a-zA-Z]+")) {
                 JOptionPane.showMessageDialog(frame, "Username must contain only letters.", "Invalid Username", JOptionPane.WARNING_MESSAGE);
                 return;
            }

            saveUser(username);
             Dashboard.show(username);
            JOptionPane.showMessageDialog(frame, "Welcome, " + username + "!", "Login/Sign Up Successful", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private static void saveUser(String username) {
        File file = new File("users.txt");
        try {
            if (!file.exists()) {
                file.createNewFile(); 
            }
          
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean exists = false;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(username)) {
                    exists = true;
                    break;
                }
            }
            reader.close();

            if (!exists) {
              
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true)); 
                writer.write(username);
                writer.newLine();
                writer.close();
                JOptionPane.showMessageDialog(null, "User '" + username + "' registered successfully!");
            } else {
                
                JOptionPane.showMessageDialog(null, "User '" + username + "' already exists. Logging in.");
            }
        } catch (IOException ex) {
            ex.printStackTrace(); 
            JOptionPane.showMessageDialog(null, "Error saving user data: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
