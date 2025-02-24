package dbObjects;

import java.io.*;
import java.util.*;

public class InvestController {
    private static File investData = new File("MenaxhimFinancash\\dbObjects\\investData.txt");

    public static ArrayList<Invest> retrieveInvestVentures(User user) {
        try {
            ArrayList<Invest> investVentures = new ArrayList<Invest>();
            Invest current = null;
            Scanner sc = new Scanner(investData);

            while (sc.hasNextLine()) {
                String details[] = sc.nextLine().split(" ");

                if (details.length > 1) {
                    if (current != null) {
                        if (details[0].equals(user.getUsername()))
                            current.setQuantity(Integer.parseInt(details[1]));
                    } else {
                        current = new Invest(
                            details[0], details[1], 
                            Double.parseDouble(details[2]), Double.parseDouble(details[3]), 
                            Double.parseDouble(details[4])
                        );
                    }
                } else {
                    if (current != null) {
                        investVentures.add(current);
                        current = null;
                    }
                }
            }

            sc.close();
            return investVentures;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean makeInvestment(Invest investMade, User user, int quantity) {
        try {
            String newData = "";
            Scanner sc = new Scanner(investData);
            boolean investFound = false;

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String details[] = line.split(" ");

                if (details.length > 1) {
                    if (investFound == true) {
                        if (details[0].equals(user.getUsername())) {
                            if (quantity + Integer.parseInt(details[1]) > 0) {
                                newData += details[0] + " " + (quantity + Integer.parseInt(details[1])) + "\n";
                            }
                            investFound = false;
                            continue;
                        }
                    } else {
                        if (details[0].equals(investMade.getType()) && details[1].equals(investMade.getName())) {
                            investFound = true;
                        }
                    }
                } else {
                    if (investFound == true) {
                        newData += user.getUsername() + " " + quantity + "\n";
                        investFound = false;
                    }
                }

                newData += line;
                if (sc.hasNextLine()) newData += "\n";
            }
            sc.close();

            PrintWriter printer = new PrintWriter(investData);
            printer.println(newData);
            printer.close();

            investMade.setQuantity(investMade.getQuantity() + quantity);
            Database.addTransaction(new Transaction(user, "Invest", -investMade.getCurrentPrice() * quantity));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void refresh() {
        try {
            String newData = "";
            Scanner sc = new Scanner(investData);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String details[] = line.split(" ");

                if (details.length > 2) {
                    double newPrice = Math.round(Double.parseDouble(details[2]) * (1 + Double.parseDouble(details[4])/100));
                    double nextChange = Double.parseDouble(details[4]);

                    if (new Random().nextInt(101) > 75) nextChange = new Random().nextInt(-100, 100);

                    newData += details[0] + " " + details[1] + " " + details[2] + " " + newPrice + " " + nextChange; 
                } else {
                    newData += line;
                }

                if (sc.hasNextLine()) newData += "\n";
            }
            sc.close();

            PrintWriter printer = new PrintWriter(investData);
            printer.println(newData);
            printer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
