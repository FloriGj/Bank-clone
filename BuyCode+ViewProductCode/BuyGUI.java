import javax.swing.*;
import java.awt.*;
import java.util.List;

// GUI class for buying products
public class BuyGUI extends JFrame {
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel basketLabel;
    private JButton addButton;
    private JButton removeButton;
    private JButton clearButton;
    private JButton checkBasketButton;
    private JButton checkTotalButton;

    private BuyController controller;

    public BuyGUI(BuyController controller) {
        super("Buy Products");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.controller = controller;

        nameLabel = new JLabel("Product Name:");
        priceLabel = new JLabel("Product Price:");
        basketLabel = new JLabel();

        addButton = new JButton("Add to Basket");
        addButton.addActionListener(e -> addToBasket());

        removeButton = new JButton("Remove from Basket");
        removeButton.addActionListener(e -> removeFromBasket());

        clearButton = new JButton("Clear Basket");
        clearButton.addActionListener(e -> clearBasket());

        checkBasketButton = new JButton("Check Basket");
        checkBasketButton.addActionListener(e -> checkBasket());

        checkTotalButton = new JButton("Check Total");
        checkTotalButton.addActionListener(e -> checkTotal());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));
        panel.add(nameLabel);
        panel.add(priceLabel);
        panel.add(basketLabel);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(clearButton);
        panel.add(checkBasketButton);
        panel.add(checkTotalButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void updateBasketLabel() {
        List<ViewProduct> basket = controller.getBasket();
        if (basket.isEmpty()) {
            basketLabel.setText("Your basket is empty.");
        } else {
            StringBuilder basketText = new StringBuilder("Items in your basket:\n");
            for (ViewProduct product : basket) {
                basketText.append(product.getName()).append(" - $").append(product.getPrice()).append("\n");
            }
            basketLabel.setText(basketText.toString());
        }
    }
    

    private void addToBasket() {
        // For demonstration, let's add a sample product
        ViewProduct product = new ViewProduct("Example Product", 10.99);
        controller.addToBasket(product);
        updateBasketLabel();
    }

    private void removeFromBasket() {
        // For demonstration, let's remove a sample product
        ViewProduct product = new ViewProduct("Example Product", 10.99);
        controller.removeFromBasket(product);
        updateBasketLabel();
    }

    private void clearBasket() {
        controller.clearBasket();
        updateBasketLabel();
    }

    private void checkBasket() {
        List<ViewProduct> basket = controller.getBasket();
        if (basket.isEmpty()) {
            basketLabel.setText("Your basket is empty.");
        } else {
            StringBuilder basketText = new StringBuilder("Items in your basket:\n");
            for (ViewProduct product : basket) {
                basketText.append(product.getName()).append(" - $").append(product.getPrice()).append("\n");
            }
            basketLabel.setText(basketText.toString());
        }
    }

    private void checkTotal() {
        double total = controller.checkTotal();
        JOptionPane.showMessageDialog(this, "Total Price: $" + total, "Total", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // Create an instance of BuyController
        BuyController controller = new BuyController();

        // Create an instance of BuyGUI with the controller
        SwingUtilities.invokeLater(() -> new BuyGUI(controller));
    }
}
