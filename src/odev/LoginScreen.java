package odev;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    
    public LoginScreen() {
        setTitle("YTÜ Airlines - Entry System");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ana giriş ekranı kapanınca her şey kapansın
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Airline Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton passengerEntryBtn = new JButton("Passenger Access");
        JButton adminEntryBtn = new JButton("Staff / Admin Access");

        passengerEntryBtn.addActionListener(e -> showPassengerOptions());
        adminEntryBtn.addActionListener(e -> {
            String password = JOptionPane.showInputDialog(this, "Enter Admin Password:");
            if ("1234".equals(password)) openMainWindow(null, "Admin");
            else if (password != null) JOptionPane.showMessageDialog(this, "Wrong Password!");
        });

        centerPanel.add(passengerEntryBtn);
        centerPanel.add(adminEntryBtn);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void showPassengerOptions() {
        String[] options = {"Login", "Register", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this, "Select Action", 
                "Passenger Access", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        PassengerManager pm = new PassengerManager();

        if (choice == 1) { // REGISTER
            JTextField fn = new JTextField();
            JTextField ln = new JTextField();
            JTextField cn = new JTextField();
            JPasswordField ps = new JPasswordField();
            Object[] fields = {"First Name:", fn, "Last Name:", ln, "Contact:", cn, "Password:", ps};
            
            if (JOptionPane.showConfirmDialog(this, fields, "Create Account", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    Passenger p = new Passenger("P-"+System.currentTimeMillis(), fn.getText(), ln.getText(), cn.getText(), new String(ps.getPassword()));
                    pm.register(p);
                    JOptionPane.showMessageDialog(this, "Success! You can now login.");
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
            }
        } else if (choice == 0) { // LOGIN
            JTextField fn = new JTextField();
            JPasswordField ps = new JPasswordField();
            Object[] fields = {"First Name:", fn, "Password:", ps};

            if (JOptionPane.showConfirmDialog(this, fields, "Passenger Login", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                Passenger loggedInUser = pm.login(fn.getText(), new String(ps.getPassword()));
                if (loggedInUser != null) openMainWindow(loggedInUser, "Passenger");
                else JOptionPane.showMessageDialog(this, "Invalid Credentials!");
            }
        }
    }

    private void openMainWindow(Passenger user, String type) {
        // giriş ekranı arkada açık kalacak
        SwingUtilities.invokeLater(() -> {
            MainWindow mainGui = new MainWindow(type, user);
            mainGui.setVisible(true);
        });
  
    }
}