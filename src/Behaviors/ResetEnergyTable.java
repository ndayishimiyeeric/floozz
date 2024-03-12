package Behaviors;

import Agents.NetworkManager;
import Utils.EnergyTable;
import jade.core.behaviours.TickerBehaviour;

public class ResetEnergyTable extends TickerBehaviour {
    private final NetworkManager networkManager;

    public  ResetEnergyTable(NetworkManager networkManager, int period) {
        super(networkManager, period);
        this.networkManager = networkManager;
    }

    @Override
    protected void onTick() {
        System.out.println("This Day I received "+ networkManager.getAvailableServicesTable().getSize() + " Energy registration request from producers");
        EnergyTable energyTable = networkManager.getAvailableServicesTable();
        energyTable.reset();
    }
}
