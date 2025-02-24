


import java.util.ArrayList;
import java.util.List;

public class Buy {
    private List<ViewProduct> basket;

    public Buy() {
        basket = new ArrayList<>();
    }

    // Add a product to the basket
    public void addToBasket(ViewProduct product) {
        basket.add(product);
    }

    // Remove a product from the basket
    public void removeFromBasket(ViewProduct product) {
        basket.remove(product);
    }

    // Clear the basket
    public void clearBasket() {
        basket.clear();
    }

    // Check the items in the basket
    public void checkBasket() {
        if (basket.isEmpty()) {
            System.out.println("Your basket is empty.");
        } else {
            System.out.println("Items in your basket:");
            for (ViewProduct product : basket) {
                System.out.println(product.getName() + " - $" + product.getPrice());
            }
        }
    }

    // Calculate the total price of the items in the basket
    public double checkTotal() {
        double total = 0;
        for (ViewProduct product : basket) {
            total += product.getPrice();
        }
        return total;
    }

    public static void main(String[] args) {
        // Create some sample products
        ViewProduct product1 = new ViewProduct("Product 1", 10.99);
        ViewProduct product2 = new ViewProduct("Product 2", 20.99);
        ViewProduct product3 = new ViewProduct("Product 3", 5.99);

        // Create a Buy instance
        Buy buy = new Buy();

        // Add products to the basket
        buy.addToBasket(product1);
        buy.addToBasket(product2);
        buy.addToBasket(product3);

        // Check the basket
        buy.checkBasket();

        // Check the total
        double totalPrice = buy.checkTotal();
        System.out.println("Total Price: $" + totalPrice);
    }
}
