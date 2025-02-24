package gui;
import dbObjects.Database;
import dbObjects.Transaction;
import dbObjects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogGui extends JDialog implements ActionListener{
    private User user;
    private HomeGui home;
    private JLabel balanceLabel;
    private JTextField amountInput, userInput;

    public DialogGui(HomeGui home, User user) {
        setSize(400, 400);
        setModal(true); //cant interact with anything until dialog closed
        setLocationRelativeTo(home); //center of home page
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        this.home = home;
        this.user = user;
    }

    public void addDialogComponents(String buttonPressed) {
        balanceLabel = new JLabel("Balance : $" + user.getBalance());
        balanceLabel.setBounds(0, 10, super.getWidth(), 20);
        balanceLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setBounds(0, 60, super.getWidth(), 20);
        amountLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(amountLabel);

        amountInput = new JTextField();
        amountInput.setBounds(20, 100, super.getWidth() - 50, 40);
        amountInput.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(amountInput);

        JButton actioButton = new JButton(buttonPressed);
        actioButton.setBounds(20, 300, super.getWidth() - 50, 50);
        actioButton.setFont(new Font("Dialog", Font.BOLD, 20));
        actioButton.addActionListener(this);
        add(actioButton);
    }

    public void addUserComponent() {
        JLabel userLabel = new JLabel("Enter User:");
        userLabel.setBounds(0, 160, super.getWidth(), 20);
        userLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(userLabel);

        userInput = new JTextField();
        userInput.setBounds(20, 200, super.getWidth() - 50, 40);
        userInput.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(userInput);
    }

    public void actionPerformed(ActionEvent e) {
        boolean tsSuccessful = false;
        String buttonPressed = e.getActionCommand();
        Double amount;

        try {
            amount = Double.parseDouble(amountInput.getText());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(DialogGui.this, "Amount entered is empty or not numeric.");
            return;
        }

        if (amount <= 0) {
            JOptionPane.showMessageDialog(DialogGui.this, "Cannot enter 0 or negative amounts.");
            return;
        }
        
        if (buttonPressed.equals("Deposit")) {
            tsSuccessful = Database.addTransaction(new Transaction(user, "Deposit", amount));
        } else if (buttonPressed.equals("Withdraw")) {
            if (amount > user.getBalance()) {
                JOptionPane.showMessageDialog(DialogGui.this, "Not enough balance.");
                return;
            }
            tsSuccessful = Database.addTransaction(new Transaction(user, "Withdraw", -amount));
        } else {
            if (amount > user.getBalance()) {
                JOptionPane.showMessageDialog(DialogGui.this, "Not enough balance.");
                return;
            } else if (userInput.getText().equals(user.getUsername())) {
                JOptionPane.showMessageDialog(DialogGui.this, "Can not enter the current user.");
                return;
            } else if (userInput.getText().equals("") || !Database.checkUser(userInput.getText())) {
                JOptionPane.showMessageDialog(DialogGui.this, "User does not exist.");
                return;
            }

            Transaction tmp = new Transaction(user, "Transfer", -amount);
            tmp.setTsUserToTransfer(userInput.getText());
            tsSuccessful = Database.addTransaction(tmp);
        }

        if (tsSuccessful) {
            JOptionPane.showConfirmDialog(DialogGui.this, "Transaction successful! Do you want to recieve a receipt sent to you email?");

            balanceLabel.setText("Balance : $" + user.getBalance());
            amountInput.setText("");
            if (userInput != null) userInput.setText("");
            home.updateBalanceLabel();
        } else {
            JOptionPane.showMessageDialog(DialogGui.this, "Transaction failed.");
        }
    }
}
