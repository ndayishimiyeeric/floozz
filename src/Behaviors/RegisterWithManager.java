package Behaviors;

import Agents.Producer;
import Utils.Energy;
import Utils.EnergySerialization;
import jade.core.behaviours.TickerBehaviour;
import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class RegisterWithManager extends TickerBehaviour {

    public RegisterWithManager(Agent agent, int period) {
        super(agent, period);
    }

    @Override
    protected void onTick() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setOntology("EnergyServiceRegistration");
        message.setContent(this.myAgent.getName());

        // Add the receiver (NetworkManager agent)
        AID receiver = new AID("NetworkManager", AID.ISLOCALNAME);
        message.addReceiver(receiver);
        Energy offering = getOffering();
        message.setContent(EnergySerialization.serializeToJson(offering));
        System.out.println(myAgent.getLocalName() + " is offering " + offering + ".");
        // Send the message
        this.myAgent.send(message);
    }

    private Energy getOffering() {
        Producer producer = (Producer) this.myAgent;
        Random random = new Random();
        String period = random.nextDouble() < 0.5 ? "night" : "day";
        String season;
        if (producer.getEnergyType() == 1) {
            int sunIntensity = producer.getWeatherParams()[0];
            season = sunIntensity > 50 ? "sunny" : "not sunny";
        } else {
            int windForce = producer.getWeatherParams()[1];
            season = windForce > 50 ? "windy" : "not windy";
        }
        return new Energy(producer.getEnergyType(), producer.getEnergyProduction(), producer.getEnergyPrice(), season, period);
    }
}
