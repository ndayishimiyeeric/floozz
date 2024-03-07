package Behaviors;

import Agents.Consumer;
import Utils.Energy;
import Utils.EnergyDeserialization;
import Utils.EnergySerialization;
import Utils.MailBox;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Map;

/**
 * @author LIN Tianyuan
 * @version 1.0
 * @date 2024-03-07
 */
public class EnergyRequest extends TickerBehaviour {
    private final MailBox requestEnergyBox;
    private final MailBox bookingBox;
    private final Consumer consumer;

    public EnergyRequest(Consumer consumer, long period){
        super(consumer, period);
        this.consumer = consumer;
        this.requestEnergyBox = consumer.getRequestEnergyBox();
        this.bookingBox = consumer.getBookingBox();
    }


    @Override
    protected void onTick() {
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
