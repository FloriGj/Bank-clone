package gui;
import dbObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InvestGui extends MainFrame implements ActionListener{
    private boolean guiReady = false;
    private ArrayList<Invest> investData;
    private JComboBox<String> typeBox;
    private JLabel ventureLabel;
    private JComboBox<Invest> ventureBox;
    private JLabel baseLabel;
    private JLabel persentageLabel;
    private JLabel flunctuationLabel;
    private JLabel purchasedLabel;
    private JLabel quantityLabel;
    private JTextField quantityInput;
    private JButton buyButton;
    private JButton sellButton;
    private JButton refreshButton;

    public InvestGui(User user) {
        super("Invest", user);
        investData = InvestController.retrieveInvestVentures(user);
    }
    
    protected void addGuiComponents() {
        String options[] = {"Stocks", "Crypto"}; 
        typeBox = new JComboBox<String>(options);
        typeBox.setBounds(20, 20, super.getWidth() - 50, 40);
        typeBox.setFont(new Font("Dialog", Font.PLAIN, 20));
        typeBox.addActionListener(this);
        add(typeBox);

        ventureLabel = new JLabel("Pick one of the options above.");
        ventureLabel.setBounds(20, 80, super.getWidth() - 50, 24);
        ventureLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(ventureLabel);

        ventureBox = new JComboBox<Invest>();
        ventureBox.setBounds(20, 120, super.getWidth() - 50, 40);
        ventureBox.setFont(new Font("Dialog", Font.PLAIN, 20));
        ventureBox.setVisible(false);
        ventureBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDetails();
            }
        });
        add(ventureBox);

        baseLabel = new JLabel("Base price : $1000");
        baseLabel.setBounds(20, 180, super.getWidth() - 50, 24);
        baseLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        baseLabel.setVisible(false);
        add(baseLabel);

        persentageLabel = new JLabel("Current value : $1200 (20%)");
        persentageLabel.setBounds(20, 220, super.getWidth() - 50, 24);
        persentageLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        persentageLabel.setVisible(false);
        add(persentageLabel);

        flunctuationLabel = new JLabel("Flunctuation : High");
        flunctuationLabel.setBounds(20, 260, super.getWidth() - 50, 24);
        flunctuationLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        flunctuationLabel.setVisible(false);
        add(flunctuationLabel);

        purchasedLabel = new JLabel("Bought : 0");
        purchasedLabel.setBounds(20, 300, super.getWidth() - 50, 24);
        purchasedLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        purchasedLabel.setVisible(false);
        add(purchasedLabel);

        quantityLabel = new JLabel("Quantity :");
        quantityLabel.setBounds(20, 360, super.getWidth() - 50, 24);
        quantityLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        quantityLabel.setVisible(false);
        add(quantityLabel);

        quantityInput = new JTextField();
        quantityInput.setBounds(20, 400, super.getWidth() - 50, 40);
        quantityInput.setFont(new Font("Dialog", Font.PLAIN, 20));
        quantityInput.setVisible(false);
        add(quantityInput);

        buyButton = new JButton("Buy");
        buyButton.setBounds(20, 460, 120, 40);
        buyButton.setFont(new Font("Dialog", Font.BOLD, 20));
        buyButton.addActionListener(this);
        buyButton.setVisible(false);
        add(buyButton);

        sellButton = new JButton("Sell");
        sellButton.setBounds(140, 460, 120, 40);
        sellButton.setFont(new Font("Dialog", Font.BOLD, 20));
        sellButton.addActionListener(this);
        sellButton.setVisible(false);
        add(sellButton);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(260, 460, 120, 40);
        refreshButton.setFont(new Font("Dialog", Font.BOLD, 20));
        refreshButton.addActionListener(this);
        refreshButton.setVisible(false);
        add(refreshButton);

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
        } else if (buttonPressed.equals("Buy") || buttonPressed.equals("Sell")) {
            int quantity;

            try {
                quantity = Integer.parseInt(quantityInput.getText());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(InvestGui.this, "Quantity entered is empty or not numeric.");
                return;
            }
            
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(InvestGui.this, "Cannot enter 0 or negative quantities.");
                return;
            }

            if (buttonPressed.equals("Buy")) {
                if (user.getBalance() < quantity * ((Invest) ventureBox.getSelectedItem()).getCurrentPrice()) {
                    JOptionPane.showMessageDialog(InvestGui.this, "Not enough balance.");
                    return;
                }
            } else {
                if (quantity > ((Invest) ventureBox.getSelectedItem()).getQuantity()) {
                    JOptionPane.showMessageDialog(InvestGui.this, "Quantity is higher then currently owned.");
                    return;
                }

                quantity *= -1;
            }

            if (InvestController.makeInvestment((Invest) ventureBox.getSelectedItem(), user, quantity)) {
                JOptionPane.showMessageDialog(InvestGui.this, "Invesment Successful!");
                updateDetails();
            } else {
                JOptionPane.showMessageDialog(InvestGui.this, "Investment Failed.");
            }
        } else if (buttonPressed.equals("Refresh")) {
            InvestController.refresh();
            investData = InvestController.retrieveInvestVentures(user);
            updateOptions();
        } else {
            updateOptions();

            if (!guiReady) {
                prepareGui(true);
                guiReady = true;
            }
        }
    }

    private void updateOptions() {
        String typeChoosed = (String) typeBox.getSelectedItem();
        ventureBox.removeAllItems();

        for (Invest invest : investData) {
            if (invest.getType().equals(typeChoosed)) {
                ventureBox.addItem(invest);
            }
        }

        if (ventureBox.getItemCount() > 0) {
            ventureBox.setSelectedIndex(0);
            prepareGui(true);
            updateDetails();
        } else {
            prepareGui(false);
        }
    }

    private void updateDetails() {
        Invest currentVenture = (Invest) ventureBox.getSelectedItem();
        if (currentVenture != null) {
            baseLabel.setText("Base price : $" + currentVenture.getBasePrice());

            double percentageDiff = (currentVenture.getCurrentPrice()/currentVenture.getBasePrice()) * 100 - 100;
            persentageLabel.setText("Current value : $" + currentVenture.getCurrentPrice() + " (" + percentageDiff +"%)");
            
            double fluncValue = Math.abs(currentVenture.getPriceFluctuation());
            if (fluncValue > 50) {
                flunctuationLabel.setText("Flunctuation : High");
            } else if (fluncValue > 15) {
                flunctuationLabel.setText("Flunctuation : Medium");
            } else {
                flunctuationLabel.setText("Flunctuation : Low");
            }

            purchasedLabel.setText("Bought : " + currentVenture.getQuantity());
        }
    }

    private void prepareGui(boolean show) {
        if (show) ventureLabel.setText("Venture :");
        else ventureLabel.setText("There is no investment to show.");
        ventureBox.setVisible(show);
        baseLabel.setVisible(show);
        persentageLabel.setVisible(show);
        flunctuationLabel.setVisible(show);
        purchasedLabel.setVisible(show);
        quantityLabel.setVisible(show);
        quantityInput.setVisible(show);
        buyButton.setVisible(show);
        sellButton.setVisible(show);
        refreshButton.setVisible(show);
    }
}