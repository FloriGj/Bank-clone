package gui;

import javax.swing.*;

import dbObjects.Database;

import java.awt.*;
import java.awt.event.*;

public class RegisterGui extends MainFrame{
    public RegisterGui() {
        super("Register");
    }
    
    protected void addGuiComponents() {
        JLabel bankLabel = new JLabel("Name Pending");
        bankLabel.setBounds(0, 20, super.getWidth(), 40);
        bankLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        bankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(bankLabel);

        JLabel usernameLabel = new JLabel("Username :");
        usernameLabel.setBounds(20, 120, super.getWidth() - 50, 24);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);
        
        JTextField usernameInput = new JTextField();
        usernameInput.setBounds(20, 160, super.getWidth() - 50, 40);
        usernameInput.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameInput);

        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(20, 220, super.getWidth() - 50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        JPasswordField passwordInput = new JPasswordField();
        passwordInput.setBounds(20, 260, super.getWidth() - 50, 40);
        passwordInput.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordInput);

        JLabel repasswordLabel = new JLabel("Re-Type Password :");
        repasswordLabel.setBounds(20, 320, super.getWidth() - 50, 24);
        repasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(repasswordLabel);
        
        JPasswordField repasswordInput = new JPasswordField();
        repasswordInput.setBounds(20, 360, super.getWidth() - 50, 40);
        repasswordInput.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(repasswordInput);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(super.getWidth()/2 - 60, 460, 120, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameInput.getText();
                String password = String.valueOf(passwordInput.getPassword());
                String repassword = String.valueOf(repasswordInput.getPassword());

                if (validateInputs(username, password, repassword)) {
                    if (Database.registerUser(username, password)) {
                        dispose();
                        LoginGui login = new LoginGui();
                        login.setVisible(true);
                        JOptionPane.showMessageDialog(login, "Account Created!");
                    } else {
                        JOptionPane.showMessageDialog(RegisterGui.this, "Failed to create account.");
                    }
                } else {
                    JOptionPane.showMessageDialog(RegisterGui.this, "Credentials are not correct.");
                }
            }
        });
        add(registerButton);

        JLabel loginLabel = new JLabel("<html><a href=\"#\">Have an account? Login Here</a></html>");
        loginLabel.setBounds(0, 510, super.getWidth(), 40);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginGui().setVisible(true);
            }
        });
        add(loginLabel);
    }

    private boolean validateInputs(String username, String pass, String repass) {
        return (username.length() > 0 && pass.length() >= 8 && pass.equals(repass));
    }
}
