import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Launcher {

    public static void main(String[] args) {
        Runtime rt = Runtime.instance();

        // Create a main container profile
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.MAIN_PORT, "1099");

        // Create the main container
        AgentContainer mainContainer = rt.createMainContainer(profile);

        try {
            // Create NetworkManager agent
            AgentController agentController = mainContainer.createNewAgent("NetworkManager", "Agents.NetworkManager", null);
            agentController.start();
            AgentController consumer = mainContainer.createNewAgent("Consumer", "Agents.Consumer", null);
            consumer.start();
//            AgentController senderController = mainContainer.createNewAgent("SenderAgent", "Agents.SenderAgent", null);
//            senderController.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
