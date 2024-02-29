package Utils;

import jade.core.AID;

import java.util.LinkedList;
import java.util.Queue;

public class MailBox {
    public Queue<Message> messages;
    private final AID ownerId;

    public MailBox (AID ownerId) {
        this.ownerId = ownerId;
        this.messages = new LinkedList<>();
    }


    public Message readMessage() {
        return messages.peek();
    }


    public void deleteMessage() {
        messages.poll();
    }


    public void receiveMessage(String content, AID senderId) {
        Message message = new Message(content, senderId, ownerId);
        messages.offer(message);
    }
}
