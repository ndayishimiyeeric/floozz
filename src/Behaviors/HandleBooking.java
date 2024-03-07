package Behaviors;

import Agents.Consumer;
import Agents.NetworkManager;
import Utils.Energy;
import Utils.EnergyDeserialization;
import Utils.EnergySerialization;
import Utils.MailBox;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LIN Tianyuan
 * @version 1.0
 * @date 2024-03-06
 */
public class HandleBooking extends TickerBehaviour {
    private final MailBox bookingBox;
    private Consumer consumer;


    public HandleBooking(Consumer consumer) {
        super(consumer, 10000);
        this.consumer = consumer;
        this.bookingBox = consumer.getBookingBox();
    }


    @Override
    protected void onTick() {
        if (!bookingBox.messages.isEmpty()) {
            ACLMessage message = bookingBox.messages.peek();
            String content = message.getContent();
            AID sender = message.getSender();
            ACLMessage newMessage = new ACLMessage();
            switch (message.getPerformative()) {
                case ACLMessage.INFORM:
                    if (message.getOntology().equals("AvailableEnergy")) {
                        System.out.println("666" + content);
                        List<Energy> energyList = EnergyDeserialization.deserializeListEnergyFromJson(content);
                        System.out.println(energyList);
                        System.out.println("Received");
                        System.out.println(energyList.size());
                    } else if (message.getOntology().equals("NoAvailableEnergy")) {
                        System.out.println(consumer.getAID()+content);
                    }

//                    System.out.println("666" + content);
//                    Map<AID, Energy> energyMap = EnergyDeserialization.deserializeMapEnergyFromJson(content);
//                    Energy energyMap = EnergyDeserialization.deserializeMapEnergyFromJson(content);
//                    System.out.println(energyMap);
//                    System.out.println("Received");
//                    for (Map.Entry<AID, Energy> entry : energyMap.entrySet()) {
//                        AID key = entry.getKey();
//                        Energy energy = entry.getValue();
//                        String energyString = EnergySerialization.serializeToJson(energy);
//                        boolean acceptOffer = consumer.evaluateOffer(energyString);
//                        System.out.println(acceptOffer);
//                    }
//                    boolean acceptOffer = consumer.evaluateOffer(content);
//                    if (acceptOffer) {
//                        newMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
//                        newMessage.setOntology("ProposalAccepted");
//                        newMessage.setContent("Accepted");
//                        newMessage.addReceiver(sender);
//                        bookingBox.messages.poll();
//                    } else {
//                        newMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
//                        newMessage.setOntology("ProposalRejected");
//                        newMessage.setContent("Rejected");
//                        newMessage.addReceiver(sender);
//                        bookingBox.messages.poll();
//                    }
                    consumer.send(newMessage);
                    break;
                case ACLMessage.REQUEST:
                    break;

            }
        } else {
            block();
        }
    }


}
