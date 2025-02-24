package BankCode;

public class Bank {
    private int currentBalance;
    private int amounts[];
    private String accounts[];
    private String methods[] = {"PayPal", "Credit Card"};

    Bank() {
        currentBalance = 0;
        amounts = new int[] {1000, 10000, 100000};
        accounts = new String[] {"Acc1", "Acc2", "Acc3"};
    }

    public int getBalance() {
        return currentBalance;
    }

    public void setBalance(int tmp) {
        currentBalance = tmp;
    }

    public int[] getAmounts() {
        return amounts;
    }

    public String[] getAccounts() {
        return accounts;
    }

    public String[] getMethods() {
        return methods;
    }
}
