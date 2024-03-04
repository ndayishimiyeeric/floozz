package Utils;

import com.google.gson.Gson;
import jade.core.AID;

import java.util.Map;

public class EnergySerialization {
    public static String serializeToJson(Energy energy) {
        Gson gson = new Gson();
        return gson.toJson(energy);
    }

    public static String serializeToJson(Map<AID, Energy> available) {
        Gson gson = new Gson();
        return gson.toJson(available);
    }
}