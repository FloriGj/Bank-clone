

import javax.swing.*;

import java.awt.*;

// GUI class to display product information
public class ProductGUI extends JFrame {
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JButton displayButton;

    private ProductController controller;

    public ProductGUI(ProductController controller) {
        super("Product Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.controller = controller;

        nameLabel = new JLabel("Product Name:");
        priceLabel = new JLabel("Product Price:");
        displayButton = new JButton("Display");
        displayButton.addActionListener(e -> displayProduct());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(nameLabel);
        panel.add(priceLabel);
        panel.add(displayButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void displayProduct() {
        // Retrieve product details from the controller
        ViewProduct product = controller.getProduct();

        // Update GUI labels with product information
        nameLabel.setText("Product Name: " + product.getName());
        priceLabel.setText("Product Price: $" + product.getPrice());
    }

    public static void main(String[] args) {
        // Create an instance of ProductController
        ProductController controller = new ProductController();

        // Create an instance of ProductGUI with the controller
        SwingUtilities.invokeLater(() -> new ProductGUI(controller));
    }
}
