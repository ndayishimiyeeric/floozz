package Agents;

import Behaviors.ReceiveTempMessage;
import Utils.Energy;
import Utils.EnergySerialization;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.HashMap;
import java.util.Map;

public class SenderAgent extends Agent {

    protected void setup() {
        addBehaviour(new ReceiveTempMessage());
        // Create an ACLMessage with the INFORM performative
        // ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
        // message.setOntology("EnergyServiceRequest");
        message.setOntology("BookingOffer");
        // Energy energy = new Energy(1, 22, 222, "sunny", "night");
        // String jsonEnergy = EnergySerialization.serializeToJson(energy);
        AID aid = new AID("123");
        Energy energy = new Energy(1, 22, 222, "sunny", "night");
        Map map = new HashMap<AID, Energy>();
        map.put(aid, energy);
        String jsonEnergy = EnergySerialization.serializeToJson(map);
        message.setContent(jsonEnergy);

        // Add the receiver (NetworkManager agent)
        AID receiver = new AID("Consumer", AID.ISLOCALNAME);
        message.addReceiver(receiver);

        // Send the message
        send(message);

        // Terminate the sender agent
        // doDelete();
    }
}

