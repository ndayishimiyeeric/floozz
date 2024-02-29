package Agents;

import jade.core.Agent;

public class NetworkManager extends Agent {
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
    }
}
