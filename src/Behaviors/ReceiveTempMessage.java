package Behaviors;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


// Testing messaging

public class ReceiveTempMessage extends CyclicBehaviour {
    public ReceiveTempMessage() {}

    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.or(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
        );

        ACLMessage message = myAgent.receive(messageTemplate);

        if (message != null) {
            String content = message.getContent();
            // System.out.println(myAgent.getAID().getName() + "Received message: " + content + " Ontology is " + message.getOntology());
        }
    }
}
