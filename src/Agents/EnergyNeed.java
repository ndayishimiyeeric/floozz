package Agents;

/**
 * @author LIN Tianyuan
 * @version 1.0
 * @date 2024-02-29
 */
public class EnergyNeed {
    public String time; // Time of day
    public double amount; // Amount of energy needed
    public String typeOfEnergy; // Renewable or non-renewable

    public EnergyNeed(String time, double amount) {
        this.time = time;
        this.amount = amount;
    }

    public EnergyNeed(String time, double amount, String typeOfEnergy) {
        this.time = time;
        this.amount = amount;
        this.typeOfEnergy = typeOfEnergy;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTypeOfEnergy() {
        return typeOfEnergy;
    }

    public void setTypeOfEnergy(String typeOfEnergy) {
        this.typeOfEnergy = typeOfEnergy;
    }
}
