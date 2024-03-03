package Utils;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.LinkedList;
import java.util.Queue;

public class MailBox {
    public Queue<ACLMessage> messages;
    private final AID ownerId;

    public MailBox (AID ownerId) {
        this.ownerId   = ownerId;
        this.messages = new LinkedList<>();
    }


    public ACLMessage readMessage() {
        return messages.peek();
    }


    public void deleteMessage() {
        messages.poll();
    }


    public void receiveMessage(ACLMessage message) {
        messages.offer(message);
    }
}
