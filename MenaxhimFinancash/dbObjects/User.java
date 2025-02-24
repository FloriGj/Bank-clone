package dbObjects;

public class User {
    private String username;
    private String password;
    private double balance;

    public User(String a, String b, double c) {
        username = a;
        password = b;
        balance = c;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double newBalance) {
        balance = newBalance;
    }
}
