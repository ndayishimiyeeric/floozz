package Agents;

import Utils.MailBox;
import jade.core.AID;
import jade.core.Agent;


public class NetworkManager extends Agent {
<<<<<<< HEAD
    protected void setup() {
        // Register the book-selling service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("market-agent");
        sd.setName("market-agent");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

//        // Add the behaviour serving queries from buyer agents
//        addBehaviour(new OfferRequestsServer());

    private final MailBox mailBox;

    public NetworkManager(MailBox mailBox) {
        this.mailBox = new MailBox(this.getAID());
    }

    public void receiveMessage(String content, AID senderId) {
        mailBox.receiveMessage(content, senderId);
    }
}
