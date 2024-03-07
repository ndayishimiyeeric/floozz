package Agents;

import Behaviors.CheckWeatherBehavior;
import Behaviors.GenerateDailyEnergyOffering;
import Behaviors.RespondToRequestBehavior;
import Utils.MailBox;
import jade.core.Agent;
import Behaviors.RegisterWithManager;

public class Producer extends Agent {

    private int energyType; // 0: "fossil",  1: "solar",  2: "wind"
    private float energyPrice;
    private int energyProductionCapacity;
    private final MailBox mailBox;

    private int[] weatherParams;

    public Producer() {
        this.mailBox = new MailBox(this.getAID());
    }

    protected void setup() {
        // Initialization of energy type
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            energyType = Integer.parseInt(""+args[0]);
            energyProductionCapacity = Integer.parseInt(""+args[1]);
            energyPrice = Integer.parseInt(""+args[2]);
        }
        addBehaviour(new RegisterWithManager(this, Weather.ABSTRACT_DAY_LENGTH));
        addBehaviour(new GenerateDailyEnergyOffering(this, Weather.ABSTRACT_DAY_LENGTH));
        addBehaviour(new RespondToRequestBehavior(this));
        // If renewable energy, then we check weather regularly
        if (this.energyType != 0) {
            addBehaviour(new CheckWeatherBehavior(this, Weather.ABSTRACT_DAY_LENGTH));
        }
    }

    public MailBox getRequestsMailBox() {
        return this.mailBox;
    }

    public int getEnergyType() {
        return energyType;
    }

    public float getEnergyPrice() {
        return energyPrice;
    }

    public int getEnergyProduction() {
        if(this.weatherParams == null) {
            return -1;
        }
        int intensity = 0;
        if (energyType == 1) {
            intensity = this.weatherParams[0];
        } else {
            intensity = this.weatherParams[1];
        }
        return energyProductionCapacity * intensity;
    }

    public void setWeatherParams(int[] weatherParams) {
        this.weatherParams = weatherParams;
    }

    protected void takeDown() {
        // Agent cleanup
        System.out.println("EnergyProducerAgent " + getAID().getName() + " terminating.");
    }
}
