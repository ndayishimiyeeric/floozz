package Ontology;

public class Offering {
    private String typeOfEnergy;
    private int quantity;
    private double price;
    private int startTime;
    private int endTime;

    public Offering(String typeOfEnergy, int quantity, double price, int startTime, int endTime) {
        this.typeOfEnergy = typeOfEnergy;
        this.quantity = quantity;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters for all member variables (optional)

    @Override
    public String toString() {
        return "Offering [typeOfEnergy=" + typeOfEnergy + ", quantity=" + quantity + ", price=" + price +
                ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }
}
