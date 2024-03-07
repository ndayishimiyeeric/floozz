package Behaviors;

import Agents.Producer;
import Utils.Energy;
import Utils.EnergySerialization;
import jade.core.behaviours.TickerBehaviour;
import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

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
        Producer producer = (Producer) this.myAgent;
        Energy offering = new Energy(producer.getEnergyType(), producer.getEnergyProduction(), producer.getEnergyPrice(), "", "");
        message.setContent(EnergySerialization.serializeToJson(offering));
        // Send the message
        this.myAgent.send(message);
    }
}
