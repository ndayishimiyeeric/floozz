package Behaviors;

import Agents.Producer;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.domain.DFService;
import jade.lang.acl.MessageTemplate;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class CheckWeatherBehavior extends TickerBehaviour {

    //  the weather agent
    private AID weatherAgentAID;

    public CheckWeatherBehavior(Agent agent, int period) {
        super(agent, period);

        this.weatherAgentAID = this.getWeatherAgentAID();
    }

    public AID getWeatherAgentAID()  {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("weather-agent");
        template.addServices(sd);

        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            if(result.length > 0) {
                return result[0].getName();
            }
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onTick() {
        ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
        req.addReceiver(weatherAgentAID);
        req.setContent("");
        myAgent.send(req);
        myAgent.addBehaviour(new CyclicBehaviour(myAgent) {

            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                ACLMessage msg = myAgent.receive(mt);

                if (msg != null) {
                    AID senderAID = msg.getSender();
                    if (senderAID.equals(weatherAgentAID)) {
                        String content = msg.getContent();
                        String[] parts = content.split(";");
                        int[] numbers = new int[parts.length];
                        numbers[0] = Integer.parseInt(parts[0]);
                        numbers[1] = Integer.parseInt(parts[1]);
                        ((Producer)myAgent).setWeatherParams(numbers);
                    }
                    else {
                        System.out.println("I received a message but not from weather agent");
                    }
                } else {
                    block();
                }
            }
        });
    }
}
