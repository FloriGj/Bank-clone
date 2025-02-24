import java.util.ArrayList;
import java.util.List;

// Controller class for managing the buying process
public class BuyController {
    private List<ViewProduct> basket;

    public BuyController() {
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
    public List<ViewProduct> getBasket() {
        return new ArrayList<>(basket);
    }

    // Calculate the total price of the items in the basket
    public double checkTotal() {
        double total = 0;
        for (ViewProduct product : basket) {
            total += product.getPrice();
        }
        return total;
    }
}
