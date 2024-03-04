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
        MessageTemplate messageTemplate = MessageTemplate.or(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
        );

        ACLMessage message = myAgent.receive(messageTemplate);
        if (message != null) {
            // message processing
            String content = message.getContent();
            System.out.println(myAgent.getAID().getName() + "Received message: " + content + " Ontology is " + message.getOntology());
            // Route the message based on its ontology
            switch (message.getOntology()) {
                case "TimerTimeOfDay":
                    mailBox.receiveMessage(message);
                    break;
                case "EnergyServiceRegistration":
                    registrationMailBox.receiveMessage(message);
                    break;
                case "EnergyServiceBooking":
                    bookingsMailBox.receiveMessage(message);
                    break;
                case "EnergyServiceRequest":
                    requestsMailBox.receiveMessage(message);
                    break;
            }
        } else {
            block();
        }
    }
}
