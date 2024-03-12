package Utils;

import jade.core.AID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnergyTable {
    private final Map<AID, Energy> storage;

    public EnergyTable() {
        this.storage = new HashMap<>();
    }

    public void addEnergy (Energy energy, AID owner) {
        storage.put(owner, energy);
    }

    public void reset () {
        storage.clear();
    }

    public Map<AID, Energy> getEnergy(int type) {
        Map<AID, Energy> result = new HashMap<>();
        for (Map.Entry<AID, Energy> entry : storage.entrySet()) {
            Energy energy = entry.getValue();
            if (energy.getType() == type) {
                result.put(entry.getKey(), energy);
            }
        }
        return result;
    }

    public Map<AID, Energy> getEnergy(String season) {
        Map<AID, Energy> result = new HashMap<>();
        for (Map.Entry<AID, Energy> entry : storage.entrySet()) {
            Energy energy = entry.getValue();
            if (energy.getSeason().equals(season)) {
                result.put(entry.getKey(), energy);
            }
        }
        return result;
    }

    public List<Energy> getEnergyList(int type) {
        List<Energy> result = new ArrayList<>();

        for (Map.Entry<AID, Energy> entry : storage.entrySet()) {
            Energy energy = entry.getValue();
            result.add(energy);
        }

        return result;
    }

    public int getSize() {
        return storage.size();
    }
}
