package Behaviors;

import Agents.Producer;
import Utils.MailBox;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Map;

public class RespondToRequestBehavior extends CyclicBehaviour {
    private final MailBox requestsMailBox;
    private final Producer producer;

    public RespondToRequestBehavior(Producer producer) {
        super(producer);
        this.requestsMailBox = producer.getRequestsMailBox();
        this.producer = producer;
    }
    @Override
    public void action() {
        if (!requestsMailBox.messages.isEmpty()) {
            ACLMessage message = requestsMailBox.messages.peek();
            AID sender = message.getSender();
            String content = message.getContent();
            ACLMessage newMessage = new ACLMessage();

            switch (message.getPerformative()) {
                case ACLMessage.REQUEST:
                    // Ask which energy
                    newMessage.setPerformative(ACLMessage.REQUEST);
                    newMessage.setOntology("WhichEnergyType");
                    newMessage.setContent("0,1,2");
                    newMessage.addReplyTo(sender);
                    newMessage.addReceiver(sender);
                    producer.send(newMessage);
                    requestsMailBox.messages.poll();
                    break;
                case ACLMessage.INFORM:
                    if (content.isEmpty())  {
                        // TODO
                        return;
                    }
                    break;

                default:
                    break;
            }
        } else {
            block();
        }
    }
}
