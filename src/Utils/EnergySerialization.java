package Utils;

import com.google.gson.Gson;

public class EnergySerialization {
    public static String serializeToJson(Energy energy) {
        Gson gson = new Gson();
        return gson.toJson(energy);
    }
}