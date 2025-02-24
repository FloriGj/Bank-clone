package BankCode;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankGui extends JFrame implements ActionListener{
    Bank bank;
    BankController bankCon;

    private JLabel accountsMsg;
    private JComboBox<String> accountsBox;
    private JLabel balanceMsg;

    private JLabel payMsg;
    private JComboBox<String> methodsBox;

    private JLabel amountMsg;
    private JTextField amount;
    private JButton confirmBtn;

    private JTextField result;

    public BankGui() {
        bank = new Bank();
        bankCon = new BankController();

        accountsMsg = new JLabel("Current account : ");
        accountsBox = new JComboBox<String>(bank.getAccounts());
        balanceMsg = new JLabel("Balance : 0");

        payMsg = new JLabel("Choose payment menthod : ");
        methodsBox = new JComboBox<String>(bank.getMethods());

        amountMsg = new JLabel("Amount : ");
        amount = new JTextField("0", 12);
        confirmBtn = new JButton("Confirm");

        result = new JTextField(30);
        result.setEditable(false);

        add(accountsMsg);
        add(accountsBox);
        add(balanceMsg);
        accountsBox.addActionListener(this);

        add(payMsg);
        add(methodsBox);
        methodsBox.addActionListener(this);
        
        add(amountMsg);
        add(amount);
        add(confirmBtn);
        confirmBtn.addActionListener(this);

        add(result);
        
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == accountsBox) {
            int current = bankCon.chooseAccount(bank, accountsBox.getSelectedItem().toString());
            balanceMsg.setText("Balanca : " + current);
        } else if (e.getSource() == confirmBtn) {
            if (bankCon.confirmPayment(bank, Integer.parseInt(amount.getText()))) {
                result.setText("Pagesa u konfirmua");
            } else {
                result.setText("Pagesa nuk mund te kryhet.");
            }
        }
    }

    public static void main(String[] args) {
        BankGui bankUI = new BankGui();
        System.out.println("Initializing " + bankUI);
    }
}