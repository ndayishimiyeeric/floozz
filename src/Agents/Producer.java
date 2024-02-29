package Agents;

import jade.core.Agent;

public class Producer extends Agent {
    private String typeOfEnergy;
    private int price;
    private int quantity;
    private price_table_of_day;

    public Producer(String typeOfEnergy, int price, int quantity) {
        this.typeOfEnergy = typeOfEnergy;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTypeOfEnergy() {
        return typeOfEnergy;
    }

    public String respondToRequest(marketAgent, time, amount, price) {
        return null;
    }

}
