package Agents;

import Behaviors.ReceiveMessage;
import Utils.MailBox;
import jade.core.Agent;


public class NetworkManager extends Agent {

    private final MailBox mailBox;

    public NetworkManager() {
        this.mailBox = new MailBox(this.getAID());
    }

    public MailBox getMailBox() {
        return mailBox;
    }

    protected void setup() {
        System.out.println("Hello! Network Manager "+ getAID().getName()+ " is ready.");
        addBehaviour(new ReceiveMessage(this));
    }
}