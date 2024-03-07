package Utils;

public class Energy {
    private int type; // 1 for renewable, 2 for non-renewable
    private double quantity; // Quantity of energy produced
    private double price; // Selling price of the energy service
    private String season; // Season (e.g., windy, sunny)
    private String period; // Period (e.g., daytime, nighttime)

    public Energy(int type, double quantity, double price, String season, String period) {
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.season = season;
        this.period = period;
    }

    // Getters and setters
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String toString() {
        String type = new String[]{"fossil", "solar", "wind"}[this.type];
        return this.quantity + " Joules of " + type + " energy priced at " + this.price + " euros per Joule";
    }
}
