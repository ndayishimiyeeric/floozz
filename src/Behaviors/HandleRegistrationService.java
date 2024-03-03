package Behaviors;

import Agents.NetworkManager;
import Utils.Energy;
import Utils.EnergyDeserialization;
import Utils.EnergyTable;
import Utils.MailBox;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class HandleRegistrationService extends CyclicBehaviour {
    private final EnergyTable availableServicesTable;
    private final MailBox registrationMailBox;

    public HandleRegistrationService(NetworkManager networkManager) {
        super(networkManager);
        this.availableServicesTable = networkManager.getAvailableServicesTable();
        this.registrationMailBox = networkManager.getRegistrationMailBox();
    }
    @Override
    public void action() {
        if (!registrationMailBox.messages.isEmpty()) {
            ACLMessage message = registrationMailBox.messages.peek();
            AID sender = message.getSender();
            String content = message.getContent();

            if (message.getOntology().equals("EnergyServiceRegistration")) {
                Energy energy = EnergyDeserialization.deserializeFromJson(content);
                availableServicesTable.addEnergy(energy, sender);
                registrationMailBox.messages.poll();
            }

        } else {
            block();
        }
    }
}
