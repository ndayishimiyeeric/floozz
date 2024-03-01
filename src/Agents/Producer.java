package Agents;

import jade.core.Agent;

public class Producer extends Agent {
    private String typeOfEnergy;
    private Map<Integer, Double> price_table_of_day; // Use a Map for hourly pricing
    private List<Booking> bookings;
    private List<Offering> offerings;

    public Producer(String typeOfEnergy) {
        this.typeOfEnergy = typeOfEnergy;
        this.offerings = new ArrayList<Offering>();
        this.bookings = new ArrayList<Booking>();
        this.price_table_of_day = new HashMap<>(); // Initialize empty price table
    }

    protected void setup() {
        addBehaviour(new TickerBehaviour(this, 86400000) {
            @Override
            protected void onTick() {
                // Generate new offerings for each hour
                offerings.clear();
                for (int hour = 0; hour < 24; hour++) {
                    // Use default 0 for missing hours
                    double price = price_table_of_day.getOrDefault(hour, 0.0);
                    // Example offering with 100 units
                    offerings.add(new Offering(typeOfEnergy, 100, price, hour, hour + 1));
                }
                clearDailyBookings();
                NTH_DAY++;
            }
        });
    }

    public String getTypeOfEnergy() {
        return typeOfEnergy;
    }

    public String respondToRequest(String marketAgent, int time, int amount, double price) {
        // Example implementation, customize as needed
        for (Offering offering : offerings) {
            if (offering.matchesCriteria(time, amount, price)) {
                // Assuming Booking class exists and it includes details like the marketAgent, time, amount, etc.
                bookings.add(new Booking(marketAgent, time, amount, price));
                return "Accepted";
            }
        }
        return "Rejected";
    }

    private void clearDailyBookings() {
        // Remove bookings from previous day
        bookings.clear();
    }

    public void generateDailyEnergyOffering() {
        // 1. Clear previous offerings:
        offerings.clear();
        // 2. Loop through each hour of the day:
        for (int hour = 0; hour < 24; hour++) {
            // 3. Retrieve the price for this hour:
            double price = price_table_of_day.getOrDefault(hour, 0.0);

            // 4. Create an Offering object with relevant details:
            offerings.add(new Offering(typeOfEnergy,
                    // Adjust quantity based on your scenario,
                    100,
                    price,
                    hour,
                    hour + 1));
        }
    }

}
