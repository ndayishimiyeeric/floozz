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

import java.util.*;

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
                        List<Energy> energyList = EnergyDeserialization.deserializeListEnergyFromJson(content);
                        for(int i = 0; i < energyList.size(); i++) {
                            boolean acceptOffer = consumer.evaluateOffer(energyList.get(i));
                            if (acceptOffer) {
                                String acceptedEnergy = "Accepted Energy: " + energyList.get(i);
                                newMessage.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                                newMessage.setOntology("EnergyServiceBooking");
                                newMessage.setContent(acceptedEnergy);
                                newMessage.addReceiver(sender);
                                bookingBox.messages.poll();
                            } else {
                                String rejectedEnergy = "Rejected Energy: " + energyList.get(i);
                                newMessage.setPerformative(ACLMessage.REJECT_PROPOSAL);
                                newMessage.setOntology("ProposalRejected");
                                newMessage.setContent("Rejected");
                                newMessage.addReceiver(sender);
                                bookingBox.messages.poll();
                            }
                        }
                    } else if (message.getOntology().equals("NoAvailableEnergy")) {
                        System.out.println(consumer.getAID()+ " Reply: " + content);
                    }
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
