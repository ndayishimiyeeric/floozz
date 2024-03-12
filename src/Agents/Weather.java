package Agents;

import Utils.FancyPrint;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.CyclicBehaviour;

import java.util.Arrays;
import java.util.Random;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.core.AID;

import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Weather extends Agent {


    // The length in milliseconds that counts
    // as a full day
    public static final int ABSTRACT_DAY_LENGTH = 8000;

    private static int DAY_COUNTER;

    private int[] weather;

    @Override
    protected void setup() {
        super.setup();

        // Register the book-selling service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("weather-agent");
        sd.setName("weather-agent");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Renew weather
        addBehaviour(new TickerBehaviour(this, ABSTRACT_DAY_LENGTH) {
            @Override
            protected void onTick() {
                showNewDayBanner();
                Weather.DAY_COUNTER++;
                Random rand = new Random();
                // Generates a random integer between 1 and 100
                int sunIntensity = 1+rand.nextInt(100);
                // Generates a random integer between 1 and 100
                int windIntensity = 1+rand.nextInt(100);
                ((Weather)this.myAgent).weather =  new int[]{sunIntensity, windIntensity};
                FancyPrint.white_fore__print("New weather: [sunIntensity, windForce] == " + Arrays.toString(((Weather) this.myAgent).weather), true);
            }

            private void showNewDayBanner() {
                System.out.println();
                String bannerTopBottom = "+----------------+";
                String bannerMiddle = String.format("|      DAY %-5d |", Weather.DAY_COUNTER);
                FancyPrint.green_fore__print(bannerTopBottom, true);
                FancyPrint.green_fore__print(bannerMiddle, true);
                FancyPrint.green_fore__print(bannerTopBottom, true);
            }
        });

        // Responding to Weather inquiries
        addBehaviour(new CyclicBehaviour(this) {

            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
                ACLMessage msg = myAgent.receive(mt);
                if (msg != null) {
                    AID requester = msg.getSender();
                    // We don't even need any content.
                    // Let's reply to him
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    String content = ((Weather) myAgent).weather[0]+";"+((Weather) myAgent).weather[1];
                    reply.setContent(content);
                    myAgent.send(reply);
                } else {
                    block();
                }
            }
        });
    }
}
