package BankCode;
public class BankController {
    public int chooseAccount(Bank bank, String emri) {
        String[] accounts = bank.getAccounts();
        int[] balance = bank.getAmounts();

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].equals(emri)) {
                bank.setBalance(balance[i]);
                return balance[i];
            }
        }

        return 0;
    }

    public boolean confirmPayment(Bank bank, int amount) {
        return bank.getBalance() >= amount;
    }
}
