package Behaviors;

import Agents.NetworkManager;
import Utils.MailBox;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMessage extends CyclicBehaviour {
    private final MailBox mailBox;

    public ReceiveMessage(NetworkManager networkManager) {
        super(networkManager);
        this.mailBox = networkManager.getMailBox();
    }

    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage message = myAgent.receive(messageTemplate);
        if (message != null) {
            // message processing
            String content = message.getContent();
            System.out.println(myAgent.getAID().getName() + "Received message: " + content);
            mailBox.receiveMessage(message);
        } else {
            block();
        }
    }
}
