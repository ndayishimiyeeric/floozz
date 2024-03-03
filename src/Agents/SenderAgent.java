package Agents;

import Utils.Energy;
import Utils.EnergySerialization;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class SenderAgent extends Agent {

    protected void setup() {
        // Create an ACLMessage with the INFORM performative
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setOntology("EnergyServiceRegistration");
        Energy energy = new Energy(1, 22, 222, "sunny", "night");
        String jsonEnergy = EnergySerialization.serializeToJson(energy);
        message.setContent(jsonEnergy);

        // Add the receiver (NetworkManager agent)
        AID receiver = new AID("NetworkManager", AID.ISLOCALNAME);
        message.addReceiver(receiver);

        // Send the message
        send(message);

        // Terminate the sender agent
        doDelete();
    }
}

