package Agents;

import Utils.MailBox;
import jade.core.AID;
import jade.core.Agent;


public class NetworkManager extends Agent {
    private final MailBox mailBox;

    public NetworkManager(MailBox mailBox) {
        this.mailBox = new MailBox(this.getAID());
    }

    public void receiveMessage(String content, AID senderId) {
        mailBox.receiveMessage(content, senderId);
    }
}
