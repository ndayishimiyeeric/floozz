package Agents;

import Behaviors.HandleEnergyRequest;
import Behaviors.HandleRegistrationService;
import Behaviors.ReceiveMessage;
import Utils.EnergyTable;
import Utils.MailBox;
import jade.core.Agent;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.List;


public class NetworkManager extends Agent {

    private final MailBox mailBox;
    private final MailBox registrationMailBox;
    private final MailBox bookingsMailBox;
    private final MailBox requestsMailBox;
    private final EnergyTable availableServicesTable;

    public NetworkManager() {
        this.mailBox = new MailBox(this.getAID());
        this.requestsMailBox = new MailBox(this.getAID());
        this.registrationMailBox = new MailBox(this.getAID());
        this.bookingsMailBox = new MailBox(this.getAID());
        this.availableServicesTable = new EnergyTable();
    }

    public MailBox getMailBox() {
        return mailBox;
    }

    public MailBox getRegistrationMailBox() {
        return registrationMailBox;
    }

    public MailBox getRequestsMailBox() {
        return requestsMailBox;
    }

    public MailBox getBookingsMailBox() {
        return bookingsMailBox;
    }

    public EnergyTable getAvailableServicesTable() {
        return availableServicesTable;
    }

    protected void setup() {
        System.out.println("Hello! Network Manager "+ getAID().getName()+ " is ready.");
        addBehaviour(new ReceiveMessage(this)); // March 2 23 // 00:00 latest
        addBehaviour(new HandleRegistrationService(this));
        addBehaviour(new HandleEnergyRequest(this));
    }

}