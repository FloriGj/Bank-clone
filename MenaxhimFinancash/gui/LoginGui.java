package gui;
import dbObjects.Database;
import dbObjects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGui extends MainFrame{
    public LoginGui() {
        super("Login");
    }
    
    protected void addGuiComponents() {
        JLabel bankLabel = new JLabel("Name Pending");
        bankLabel.setBounds(0, 20, super.getWidth(), 40);
        bankLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        bankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(bankLabel);

        JLabel usernameLabel = new JLabel("Username :");
        usernameLabel.setBounds(20, 160, super.getWidth() - 50, 24);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);
        
        JTextField usernameInput = new JTextField();
        usernameInput.setBounds(20, 200, super.getWidth() - 50, 40);
        usernameInput.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameInput);

        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(20, 280, super.getWidth() - 50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);
        
        JPasswordField passwordInput = new JPasswordField();
        passwordInput.setBounds(20, 320, super.getWidth() - 50, 40);
        passwordInput.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordInput);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(super.getWidth()/2 - 60, 460, 120, 40);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameInput.getText();
                String password = String.valueOf(passwordInput.getPassword());

                if (validateInputs(username, password)) {
                    User user = Database.validateLogin(username, password);
                    if (user != null) {
                        dispose();
                        HomeGui home = new HomeGui(user);
                        home.setVisible(true);
                        JOptionPane.showMessageDialog(home, "Login Successfully!");
                    } else {
                        JOptionPane.showMessageDialog(LoginGui.this, "Login failed. Check credentials and try again.");
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginGui.this, "Credentials are not correct.");
                }
            }
        });
        add(loginButton);

        JLabel registerLabel = new JLabel("<html><a href=\"#\">Don't have an account? Register Here</a></html>");
        registerLabel.setBounds(0, 510, super.getWidth(), 40);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new RegisterGui().setVisible(true);
            }
        });
        add(registerLabel);
    }

    private boolean validateInputs(String username, String pass) {
        return (username.length() > 0 && pass.length() >= 8);
    }
}
