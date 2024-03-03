package Behaviors;

import Agents.NetworkManager;
import Utils.MailBox;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMessage extends CyclicBehaviour {
    private final MailBox mailBox;
    private final MailBox registrationMailBox;
    private final MailBox bookingsMailBox;
    private final MailBox requestsMailBox;

    public ReceiveMessage(NetworkManager networkManager) {
        super(networkManager);
        this.mailBox = networkManager.getMailBox();
        this.registrationMailBox = networkManager.getRegistrationMailBox();
        this.requestsMailBox = networkManager.getRequestsMailBox();
        this.bookingsMailBox = networkManager.getBookingsMailBox();
    }

    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage message = myAgent.receive(messageTemplate);
        if (message != null) {
            // message processing
            String content = message.getContent();
            System.out.println(myAgent.getAID().getName() + "Received message: " + content + " Ontology is " + message.getOntology());
            if (message.getOntology().equals("TimerTimeOfDay")) {
                mailBox.receiveMessage(message);
            } else if (message.getOntology().equals("EnergyServiceRegistration")) {
                registrationMailBox.receiveMessage(message);
            } else if (message.getOntology().equals("EnergyServiceBooking")) {
                bookingsMailBox.receiveMessage(message);
            } else if (message.getOntology().equals("EnergyServiceRequest")) {
                requestsMailBox.receiveMessage(message);
            }

        } else {
            block();
        }
    }
}
