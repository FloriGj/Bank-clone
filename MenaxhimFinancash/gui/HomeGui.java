package gui;
import dbObjects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeGui extends MainFrame implements ActionListener{
    private JTextField balanceField;

    public HomeGui(User user) {
        super("Home", user);
    }

    protected void addGuiComponents() {
        String message = "<html><body style='text-align:center'>" +
                        "<b>Hello, "+ user.getUsername() +"</b><br>" +
                        "What would you like to do today?</body></html>";
                        
        JLabel welcomeLabel = new JLabel(message);
        welcomeLabel.setBounds(0, 20, super.getWidth(), 50);
        welcomeLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        JLabel balanceLabel = new JLabel("Current Balance");
        balanceLabel.setBounds(0, 90, super.getWidth(), 40);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        balanceField = new JTextField("$"+user.getBalance());
        balanceField.setBounds(20, 140, super.getWidth() - 50, 40);
        balanceField.setFont(new Font("Dialog", Font.PLAIN, 20));
        balanceField.setHorizontalAlignment(SwingConstants.RIGHT);
        balanceField.setEditable(false);
        add(balanceField);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(20, 200, super.getWidth()/2 - 30, 50);
        depositButton.setFont(new Font("Dialog", Font.BOLD, 20));
        depositButton.addActionListener(this);
        add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(super.getWidth()/2, 200, super.getWidth()/2 - 30, 50);
        withdrawButton.setFont(new Font("Dialog", Font.BOLD, 20));
        withdrawButton.addActionListener(this);
        add(withdrawButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(20, 260, super.getWidth() - 50, 50);
        transferButton.setFont(new Font("Dialog", Font.BOLD, 20));
        transferButton.addActionListener(this);
        add(transferButton);

        JButton transactionsButton = new JButton("Transactions");
        transactionsButton.setBounds(20, 320, super.getWidth() - 50, 50);
        transactionsButton.setFont(new Font("Dialog", Font.BOLD, 20));
        transactionsButton.addActionListener(this);
        add(transactionsButton);

        JButton investButton = new JButton("Invest");
        investButton.setBounds(20, 380, super.getWidth() - 50, 50);
        investButton.setFont(new Font("Dialog", Font.BOLD, 20));
        investButton.addActionListener(this);
        add(investButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(20, 500, super.getWidth() - 50, 50);
        logoutButton.setFont(new Font("Dialog", Font.BOLD, 20));
        logoutButton.addActionListener(this);
        add(logoutButton);
    }

    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        if (
            buttonPressed.equals("Deposit") || 
            buttonPressed.equals("Withdraw") ||
            buttonPressed.equals("Transfer")
        ) {
            DialogGui dialog = new DialogGui(this, user);
            dialog.setTitle(buttonPressed);

            dialog.addDialogComponents(buttonPressed);
            if (buttonPressed.equals("Transfer")) dialog.addUserComponent();

            dialog.setVisible(true);
        } else if (buttonPressed.equals("Transactions")) {
            dispose();
            new TransactionGui(user).setVisible(true);
        } else if (buttonPressed.equals("Invest")) {
            dispose();
            new InvestGui(user).setVisible(true);
        } else {
            dispose();
            new LoginGui().setVisible(true);
        }
    }

    public void updateBalanceLabel() {
        balanceField.setText("$"+user.getBalance());
    }
}
