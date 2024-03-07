package Behaviors;

import Agents.Consumer;
import Utils.MailBox;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author LIN Tianyuan
 * @version 1.0
 * @date 2024-03-06
 */
public class ReceiveMessageForConsumer extends CyclicBehaviour {
    private final MailBox mailBox;
    private final MailBox bookingBox;
    private final MailBox requestEnergyBox;

    public ReceiveMessageForConsumer(Consumer consumer){
        super(consumer);
        this.mailBox = consumer.getMailBox();
        this.bookingBox = consumer.getBookingBox();
        this.requestEnergyBox = consumer.getRequestEnergyBox();
    }

    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.or(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchPerformative(ACLMessage.PROPOSE)
        );
        ACLMessage message = myAgent.receive(messageTemplate);
        if (message != null){
            String content = message.getContent();
            System.out.println(myAgent.getAID().getName() + "Received message: " + content + " Ontology is " + message.getOntology());
            switch (message.getOntology()){
                case "NoAvailableEnergy":
                    bookingBox.receiveMessage(message);
                    break;
                case "AvailableEnergy":
                    bookingBox.receiveMessage(message);
                    break;
            }
        }else{
            block();
        }
    }
}
