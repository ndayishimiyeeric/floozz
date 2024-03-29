package Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jade.core.AID;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class EnergySerialization {
    public static String serializeToJson(Energy energy) {
        Gson gson = new Gson();
        return gson.toJson(energy);
    }

    public static String serializeToJson(Map<AID, Energy> available) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Energy>>(){}.getType();
        return gson.toJson(available, type);
    }

    public static String serializeToJsonList(List<Energy> available) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Energy>>(){}.getType();
        return gson.toJson(available, type);
    }
}