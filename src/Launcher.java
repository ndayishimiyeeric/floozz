import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.Random;

public class Launcher {

    public static void main(String[] args) {
        Runtime rt = Runtime.instance();

        // Create a main container profile
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.MAIN_PORT, "1099");

        // Create the main container
        AgentContainer mainContainer = rt.createMainContainer(profile);

//        try {
//            // Create NetworkManager agent
//            AgentController agentController = mainContainer.createNewAgent("NetworkManager", "Agents.NetworkManager", null);
//            agentController.start();
//            AgentController senderController = mainContainer.createNewAgent("SenderAgent", "Agents.SenderAgent", null);
//            senderController.start();
//        } catch (StaleProxyException e) {
//            e.printStackTrace();
//        }

        try {
            // Start NetworkManager agent
            AgentController networkManager = mainContainer.createNewAgent("NetworkManager", "Agents.NetworkManager", null);
            networkManager.start();
            AgentController weatherAgent = mainContainer.createNewAgent("Weather", "Agents.Weather", null);
            weatherAgent.start();
            // Generating the producers
            Random random = new Random();
            int numProducers = 1+random.nextInt(9);
            for (int i = 0; i < numProducers; i++) {
                int energyType =  random.nextInt(3);
                int energyProductionCapacity =  1+random.nextInt(9);
                int energyPrice =  3+random.nextInt(2);
                AgentController producer = mainContainer.createNewAgent("Producer"+i, "Agents.Producer", new Object[]{energyType, energyProductionCapacity, energyPrice});
                 producer.start();
            }
            // Generating the consumers
            int numConsumers = 1+random.nextInt(999);
            for (int i = 0; i < numProducers; i++) {
                AgentController consumer = mainContainer.createNewAgent("Consumer"+i, "Agents.Consumer", null);
                consumer.start();
            }
            System.out.println("Generating an energy grid with " + numProducers + " producers and " + numConsumers + " consumers.");
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
