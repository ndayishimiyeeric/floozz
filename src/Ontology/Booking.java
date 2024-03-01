package Ontology;

public class Booking {
    // Name of the consumer who booked
    private String consumerName;
    // Name of the producer offering the energy
    private String producerName;
    // Type of energy being booked
    private String typeOfEnergy;
    // Quantity of energy booked
    private int quantity;
    // Price per unit of energy
    private double price;
    // Starting time of the booking
    private int startTime;
    // Ending time of the booking
    private int endTime;

    public Booking(String consumerName, String producerName, String typeOfEnergy, int quantity, double price, int startTime, int endTime) {
        this.consumerName = consumerName;
        this.producerName = producerName;
        this.typeOfEnergy = typeOfEnergy;
        this.quantity = quantity;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Booking [consumerName=" + consumerName + ", producerName=" + producerName + ", typeOfEnergy=" + typeOfEnergy +
                ", quantity=" + quantity + ", price=" + price + ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }
}
