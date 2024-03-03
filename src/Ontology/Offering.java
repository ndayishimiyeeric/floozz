package Ontology;

public class Offering {
    private final String typeOfEnergy;
    private final int quantity;
    private final double price;
    private final int startTime;
    private final int endTime;

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

    public boolean matchesCriteria(int time, int amount, double price) {
        return false; // Eric: I added the return to fix compile errors you can continue your impl
    }
}
