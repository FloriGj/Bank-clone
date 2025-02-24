package gui;
import dbObjects.Database;
import dbObjects.Transaction;
import dbObjects.User;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TransactionGui extends MainFrame implements ActionListener{
    private ArrayList<Transaction> tsData;

    public TransactionGui(User user) {
        super("Transactions", user);
    }
    
    protected void addGuiComponents() {
        JPanel transactionsContainer = new JPanel();
        transactionsContainer.setLayout(new BoxLayout(transactionsContainer, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(transactionsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(10, 10, getWidth() - 30, getHeight() - 100);

        tsData = Database.retrieveTransactions(user);
        if (tsData != null) {
            if (tsData.size() > 0) {
                for (int i = 0; i < tsData.size(); i++) {
                    if (60 * tsData.size() < scrollPane.getWidth())
                        scrollPane.setSize(new Dimension(scrollPane.getWidth(), 60 * tsData.size()));

                    JPanel tsContainer = new JPanel();
                    tsContainer.setLayout(new BorderLayout());

                    JLabel type = new JLabel(tsData.get(i).getTsType());
                    type.setFont(new Font("Dialog", Font.BOLD, 16));

                    Double tsAmount = tsData.get(i).getTsAmount();
                    String amountMsg = "$" + Math.abs(tsAmount);
                    if (tsAmount >= 0) amountMsg = "+" + amountMsg;
                    else amountMsg = "-" + amountMsg;
                    JLabel amount = new JLabel(amountMsg);
                    amount.setFont(new Font("Dialog", Font.BOLD, 16));

                    JLabel date = new JLabel(tsData.get(i).getTsDate());
                    date.setFont(new Font("Dialog", Font.BOLD, 16));

                    tsContainer.add(type, BorderLayout.WEST);
                    tsContainer.add(amount, BorderLayout.EAST);
                    tsContainer.add(date, BorderLayout.SOUTH);

                    tsContainer.setBorder(BorderFactory.createLineBorder(Color.black));
                    transactionsContainer.add(tsContainer);
                }
            } else {
                JLabel msg = new JLabel("Transaction history is empty.");
                msg.setFont(new Font("Dialog", Font.BOLD, 16));
                transactionsContainer.add(msg);
            }
        } else {
            JLabel msg = new JLabel("Couldn't load transactions history.");
            msg.setFont(new Font("Dialog", Font.BOLD, 16));
            transactionsContainer.add(msg);
        }

        add(scrollPane);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(310, 520, 80, 30);
        backBtn.setFont(new Font("Dialog", Font.PLAIN, 18));
        backBtn.addActionListener(this);
        add(backBtn);
    }

    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        if (buttonPressed.equals("Back")) {
            dispose();
            new HomeGui(user).setVisible(true);
        }
    }
}
