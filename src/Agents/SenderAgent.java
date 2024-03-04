package Agents;

import Behaviors.ReceiveTempMessage;
import Utils.Energy;
import Utils.EnergySerialization;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SenderAgent extends Agent {

    protected void setup() {
        addBehaviour(new ReceiveTempMessage());
        // Create an ACLMessage with the INFORM performative
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setOntology("EnergyServiceRequest");
        // Energy energy = new Energy(1, 22, 222, "sunny", "night");
        // String jsonEnergy = EnergySerialization.serializeToJson(energy);
        message.setContent("2");

        // Add the receiver (NetworkManager agent)
        AID receiver = new AID("NetworkManager", AID.ISLOCALNAME);
        message.addReceiver(receiver);

        // Send the message
        send(message);

        // Terminate the sender agent
        // doDelete();
    }
}

