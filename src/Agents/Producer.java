package Agents;

import jade.core.Agent;

public class Producer extends Agent {
    private String typeOfEnergy;
    private price_table_of_day;
    private List<Booking> bookings;
    private List<Offering> offerings;

    public Producer(String typeOfEnergy) {
        this.typeOfEnergy = typeOfEnergy;
        this.offerings = new ArrayList<Offering>();
        this.bookings = new ArrayList<Booking>();
    }

    protected void setup() {
        addBehaviour(new TickerBehaviour(this, 86400000) {
            @Override
            protected void onTick() {
                generateDailyEnergyOffering();
                clearDailyBookings();
                NTH_DAY++;
            }
        });
    }

    public String getTypeOfEnergy() {
        return typeOfEnergy;
    }

    public String respondToRequest(String marketAgent, int time, int amount, double price) {
        return null;
    }

    public void generateDailyEnergyOffering() {


    }

}
