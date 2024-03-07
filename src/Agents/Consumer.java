package Agents;

import Behaviors.EnergyRequest;
import Behaviors.HandleBooking;
import Behaviors.ReceiveMessageForConsumer;
import Behaviors.ReceiveTempMessage;
import Ontology.EnergyNeed;
import Utils.Energy;
import Utils.EnergyDeserialization;
import Utils.MailBox;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.List;
import java.util.Random;

public class Consumer extends Agent {
    private final String MARKET_AGENT = "MarketAgent";
    private int NTH_DAY = 1;
    private List<EnergyNeed> persistentNeeds;
    private List<EnergyNeed> flexibleNeeds;
    private int preferredEnergyType = 1;
    private double budget = 20;
    private int sendingTime = 20;

    private final MailBox mailBox;
    private final MailBox bookingBox;
    private final MailBox requestEnergyBox;

    public Consumer(){
        this.mailBox = new MailBox(this.getAID());
        this.bookingBox = new MailBox(this.getAID());
        this.requestEnergyBox = new MailBox(this.getAID());
    }


    protected void setup() {
        addBehaviour(new TickerBehaviour(this, 86400000) {
            @Override
            protected void onTick() {
                generateFlexibleNeeds();
                generateDailyBudget();
                NTH_DAY++;
            }
        });


        addBehaviour(new EnergyRequest(this, 10000));
        addBehaviour(new ReceiveMessageForConsumer(this));
        addBehaviour(new HandleBooking(this));
        // respondToBookingOffer
//        addBehaviour(new CyclicBehaviour(this) {
//            @Override
//            public void action() {
//                ACLMessage offerMsg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
//                if (offerMsg != null) {
//                    String content = offerMsg.getContent();
//                    System.out.println(content);
//                    boolean acceptOffer = evaluateOffer(content);
//                    ACLMessage reply = offerMsg.createReply();
//
//                    if (acceptOffer){
//                        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
//                        reply.setContent("Accepted");
//                    } else {
//                        reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
//                        reply.setContent("Rejected");
//                    }
//                    send(reply);
//                } else {
//                    block();
//                }
//            }
//        });

    }

    public MailBox getMailBox() {
        return this.mailBox;
    }

    public MailBox getBookingBox() {
        return this.bookingBox;
    }

    public MailBox getRequestEnergyBox() {
        return this.requestEnergyBox;
    }
    private void generateDailyBudget() {
        Random random = new Random();
        budget = 50 + (200 - 50) * random.nextDouble();
    }

    private void generateFlexibleNeeds() {
        Random random = new Random();
        flexibleNeeds.clear();
        int numberOfNeeds = random.nextInt(5) + 1;

        for(int i = 0; i < numberOfNeeds; i++) {
            String time = generateRandomTime();
            double amount = 1 + (10 - 1) * random.nextDouble();
            flexibleNeeds.add(new EnergyNeed(time, amount));
        }
    }

    private String generateRandomTime() {
        Random random = new Random();
        int hour = random.nextInt(24);
        int minute = random.nextInt(2) * 30;
        return String.format("%02d:%02d", hour, minute);
    }

    private void requestForBooking(String marketAgent, String time, double amount, double price) {
        ACLMessage bookingRequest = new ACLMessage(ACLMessage.CFP);
        bookingRequest.addReceiver(new jade.core.AID(marketAgent, AID.ISLOCALNAME));
        bookingRequest.setLanguage("English");
        bookingRequest.setOntology("Energy-Trading");
        bookingRequest.setContent(time + "," + amount + "," + price);
        send(bookingRequest);
    }

    public int getPreferredEnergyType(){
        return this.preferredEnergyType;
    }
    public double utilityForOffer(int type, double price, double quantity) {
        double utilityScore = 0.0;
        if (this.preferredEnergyType == type) {
            utilityScore += 50;
        }
        utilityScore -= Math.abs(this.budget - price);

        return utilityScore;
    }

//    public boolean evaluateOffer(String content) {
//        Energy energy = EnergyDeserialization.deserializeFromJson(content);
//        int type = energy.getType();
//        double price = energy.getPrice();
//        double quantity = energy.getQuantity();
//
//        double utility = utilityForOffer(type, price, quantity);
//
//        boolean isWithinBudget = price <= budget;
//        boolean hasHighUtility = utility > 50;
//
//        return isWithinBudget && hasHighUtility;
//    }

    public boolean evaluateOffer(Energy energy) {
        int type = energy.getType();
        double price = energy.getPrice();
        double quantity = energy.getQuantity();

        double utility = utilityForOffer(type, price, quantity);

        boolean isWithinBudget = price <= budget;
        boolean hasHighUtility = utility > 50;

        return isWithinBudget && hasHighUtility;
    }

    public boolean hasBudget(){
        return this.budget > 0;
    }

}
