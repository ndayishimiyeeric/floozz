
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import b2b.BookBuyerAgent;
import b2b.BookSellerAgent;

public class Main {
    public static void main(String[] args) {
        Runtime runtime = Runtime.instance();
        Profile config = new ProfileImpl("localhost", 8888, null);
        config.setParameter("gui", "true");

        // tests go here
        for(int i = 0; i )
            AgentContainer mc = runtime.createMainContainer(config);
            AgentController ac1;
            AgentController ac2;
            try {
                ac1 = mc.createNewAgent("producer", BookSellerAgent.class.getName(), null);
                ac2 = mc.createNewAgent("consumer", BookBuyerAgent.class.getName(), null);
                ac1.start();
                ac2.start();
            } catch (StaleProxyException e) {
            }
    }
}
