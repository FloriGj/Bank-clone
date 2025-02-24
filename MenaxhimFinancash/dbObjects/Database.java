package dbObjects;
import java.io.*;
import java.util.*;

public class Database {
    private static File tsData = new File("MenaxhimFinancash\\dbObjects\\tsData.txt");
    private static File userData = new File("MenaxhimFinancash\\dbObjects\\userData.txt");

    public static User validateLogin(String username, String password) {
        try {
            Scanner sc = new Scanner(userData);

            while (sc.hasNextLine()) {
                String details[] = sc.nextLine().split(" ");

                if (details[0].equals(username) && details[1].equals(password)) {
                    sc.close();
                    return new User(username, password, Double.parseDouble(details[2]));
                }
            }

            sc.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean registerUser(String username, String password) {
        if (checkUser(username)) return false;
        
        try {
            Scanner sc = new Scanner(userData);

            String newData = "";
            while (sc.hasNextLine()) {
                newData += sc.nextLine() + "\n";
            }
            newData += (username + " " + password + " 0");

            PrintWriter printer = new PrintWriter(userData);
            printer.println(newData);
            printer.close();
            sc.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addTransaction(Transaction ts) {
        try {
            User current = ts.getUser();
            Scanner userSc = new Scanner(userData);
            Scanner transactionSc = new Scanner(tsData);

            //Finding user and updating balance
            String newData = "";
            while (userSc.hasNextLine()) {
                String line = userSc.nextLine();
                String details[] = line.split(" ");

                if (details[0].equals(current.getUsername())) {
                    newData += (details[0] + " " + details[1] + " " + (current.getBalance() + ts.getTsAmount()));
                } else if (ts.getTsType().equals("Transfer") && details[0].equals(ts.getTsUserToTransfer())) {
                    newData += (details[0] + " " + details[1] + " " + (Double.parseDouble(details[2]) - ts.getTsAmount()));
                } else {
                    newData += line;
                }

                if (userSc.hasNextLine()) newData += "\n";
            }

            //Updating transaction history
            String newTsData = "";
            while (transactionSc.hasNextLine()) {
                newTsData += transactionSc.nextLine() + "\n";
            }
            newTsData += current.getUsername() + " " + ts.getTsType() + " " + ts.getTsAmount() + " " + ts.getTsDate();
            if (ts.getTsType().equals("Transfer"))
                newTsData += " " + ts.getTsUserToTransfer() + "\n" + 
                    ts.getTsUserToTransfer() + " " + ts.getTsType() + " " + 
                    (-ts.getTsAmount()) + " " + ts.getTsDate() + " " + 
                    current.getUsername();

            //Saving the new changes in text files
            PrintWriter printer = new PrintWriter(userData);
            printer.println(newData);
            printer.close();

            printer = new PrintWriter(tsData);
            printer.println(newTsData);
            printer.close();

            userSc.close();
            transactionSc.close();
            
            //Updating the current user balance
            current.setBalance(current.getBalance() + ts.getTsAmount());
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkUser(String username) {
        try {
            Scanner sc = new Scanner(userData);

            while (sc.hasNextLine()) {
                String details[] = sc.nextLine().split(" ");

                if (details[0].equals(username)) {
                    sc.close();
                    return true;
                }
            }

            sc.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Transaction> retrieveTransactions(User user) {
        try {
            Scanner sc = new Scanner(tsData);
            ArrayList<Transaction> tsList = new ArrayList<Transaction>();

            while (sc.hasNextLine()) {
                String details[] = sc.nextLine().split(" ");

                if (details[0].equals(user.getUsername())) {
                    tsList.add(new Transaction(user, details[1], Double.parseDouble(details[2]), details[3]));
                }
            }

            sc.close();
            return tsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
