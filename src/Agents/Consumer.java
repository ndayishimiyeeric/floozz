package Agents;

import jade.core.AID;
import jade.core.Agent;
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
    private String preferredEnergyType;
    private double budget;

    protected void setup() {
        addBehaviour(new TickerBehaviour(this, 86400000) {
            @Override
            protected void onTick() {
                generateFlexibleNeeds();
                generateDailyBudget();
                NTH_DAY++;
            }
        });

        // respondToBookingOffer
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage offerMsg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
                if (offerMsg != null) {
                    String content = offerMsg.getContent();
                    boolean acceptOffer = evaluateOffer(content);
                    ACLMessage reply = offerMsg.createReply();

                    if (acceptOffer){
                        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                        reply.setContent("Accepted");
                    } else {
                        reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                        reply.setContent("Rejected");
                    }
                    send(reply);
                } else {
                    block();
                }
            }
        });
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

    private double utilityForOffer(String time, double price, String typeOfEnergy) {
        double utilityScore = 0.0;
        if (this.preferredEnergyType.equals(typeOfEnergy)) {
            utilityScore += 50;
        }
        utilityScore -= Math.abs(this.budget - price);

        return utilityScore;
    }

    private boolean evaluateOffer(String content) {
        String[] parts = content.split(",");
        String time = parts[0];
        double amount = Double.parseDouble(parts[1]);
        String typeOfEnergy = parts[2];
        double price = Double.parseDouble(parts[3]);

        double utility = utilityForOffer(time, price, typeOfEnergy);

        boolean isWithinBudget = price <= budget;
        boolean hasHighUtility = utility > 50;

        return isWithinBudget && hasHighUtility;
    }

}
