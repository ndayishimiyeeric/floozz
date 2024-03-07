package Behaviors;

import Agents.NetworkManager;
import Utils.Energy;
import Utils.EnergySerialization;
import Utils.EnergyTable;
import Utils.MailBox;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.HashMap;
import java.util.Map;

public class HandleEnergyRequest extends CyclicBehaviour {
    private final MailBox requestsMailBox;
    private final NetworkManager networkManager;
    private final EnergyTable availableServicesTable;

    public HandleEnergyRequest(NetworkManager networkManager) {
        super(networkManager);
        this.requestsMailBox = networkManager.getRequestsMailBox();
        this.networkManager = networkManager;
        this.availableServicesTable = networkManager.getAvailableServicesTable();
    }
    @Override
    public void action() {
        if (!requestsMailBox.messages.isEmpty()) {
            ACLMessage message = requestsMailBox.messages.peek();
            AID sender = message.getSender();
            String content = message.getContent();
            ACLMessage newMessage = new ACLMessage();

            switch (message.getPerformative()) {
                case ACLMessage.REQUEST:
                    // Ask which energy
                    newMessage.setPerformative(ACLMessage.REQUEST);
                    newMessage.setOntology("WhichEnergyType");
                    newMessage.setContent("1,2");
                    newMessage.addReplyTo(sender);
                    newMessage.addReceiver(sender);
                    networkManager.send(newMessage);
                    requestsMailBox.messages.poll();
                    System.out.println(networkManager.getAID().getName() + " Reply: " + newMessage.getContent());
                    break;
                case ACLMessage.INFORM:
                    if (content.isEmpty()) {
                        // no energy then ask again
                        newMessage.setPerformative(ACLMessage.REQUEST);
                        newMessage.setOntology("WhichEnergyType");
                        newMessage.setContent("1,2");
                        newMessage.addReplyTo(sender);
                        newMessage.addReceiver(sender);
                        networkManager.send(newMessage);
                        requestsMailBox.messages.poll();
                        System.out.println(networkManager.getAID() + " Reply: " + newMessage.getContent());
                        return;
                    }

                    int type = (int) content.charAt(0);
                    Map<AID, Energy> available = availableServicesTable.getEnergy(type);
                    // Change this
                    AID aid = new AID("123");
                    Energy energy = new Energy(1, 22, 222, "sunny", "night");
                    Map energyHashMap = new HashMap<AID, Energy>();
                    energyHashMap.put(sender, energy);
                    newMessage.setOntology("AvailableEnergy");
                    String jsonObject = EnergySerialization.serializeToJson(energyHashMap);
                    newMessage.setContent(jsonObject);
//                    if (available.size() > 1) {
//                        newMessage.setOntology("AvailableEnergy");
//                        String jsonObject = EnergySerialization.serializeToJson(available);
//                        newMessage.setContent(jsonObject);
//                    } else {
//                        newMessage.setOntology("NoAvailableEnergy");
//                        newMessage.setContent("No energy at this time");
//                    }

                    newMessage.setPerformative(ACLMessage.INFORM);
                    newMessage.addReplyTo(sender);
                    newMessage.addReceiver(sender);
                    networkManager.send(newMessage);
                    requestsMailBox.messages.poll();
                    System.out.println(networkManager.getAID() + " Reply: " + newMessage.getContent());
                    break;

                default:
                    break;
            }
        } else {
            block();
        }
    }
}