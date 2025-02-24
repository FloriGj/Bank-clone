package dbObjects;

public class Invest {
    private String type;
    private String name;
    private Double basePrice;
    private Double currentPrice;
    private Double priceFluctuation;
    private int quantity;

    public Invest(String type, String name, Double basePrice, Double currentPrice, Double priceFluctuation) {
        this.type = type;
        this.name = name;
        this.basePrice = basePrice;
        this.currentPrice = currentPrice;
        this.priceFluctuation = priceFluctuation;
    }
    
    public String getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public Double getPriceFluctuation() {
        return priceFluctuation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setPriceFluctuation(Double priceFluctuation) {
        this.priceFluctuation = priceFluctuation;
    } 

    public String toString() {
        return this.name;
    }
}
