package Behaviors;

import Agents.Consumer;
import Utils.Energy;
import Utils.EnergyDeserialization;
import Utils.EnergySerialization;
import Utils.MailBox;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Map;

/**
 * @author LIN Tianyuan
 * @version 1.0
 * @date 2024-03-07
 */
public class EnergyRequest extends OneShotBehaviour {
    private final MailBox requestEnergyBox;
    private final MailBox bookingBox;
    private final Consumer consumer;

    public EnergyRequest(Consumer consumer){
        super(consumer);
        this.consumer = consumer;
        this.requestEnergyBox = consumer.getRequestEnergyBox();
        this.bookingBox = consumer.getBookingBox();
    }

    @Override
    public void action() {
        // if (!requestEnergyBox.messages.isEmpty()) {
//            ACLMessage message = requestEnergyBox.messages.peek();
//            switch (message.getOntology()) {
//                case "NoAvailableEnergy":
//                    requestEnergyBox.messages.poll();
//                    break;
//                case "AvailableEnergy":
//                    String content = message.getContent();
//                    Map<AID, Energy> energyMap = EnergyDeserialization.deserializeMapEnergyFromJson(content);
//                    System.out.println("Received");
//                    System.out.println(energyMap);
//                    for(Map.Entry<AID, Energy> entry: energyMap.entrySet()){
//                        AID key = entry.getKey();
//                        Energy energy = entry.getValue();
//                        String energyString = EnergySerialization.serializeToJson(energy);
//                        boolean acceptOffer = consumer.evaluateOffer(energyString);
//                        System.out.println(acceptOffer);
//                    }
//                    requestEnergyBox.messages.poll();
//                    break;
//            }
        if(consumer.hasBudget() && bookingBox.messages.isEmpty()) {
            ACLMessage message = new ACLMessage();
            message.setContent(String.valueOf(consumer.getPreferredEnergyType()));
            message.setOntology("EnergyServiceRequest");
            message.setPerformative(ACLMessage.INFORM);
            AID receiver = new AID("NetWorkManager", AID.ISLOCALNAME);
            message.addReceiver(receiver);
            consumer.send(message);
            System.out.println("Message sent.");
        }else{
            block();
        }

    }
}
