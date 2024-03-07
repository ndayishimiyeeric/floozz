package Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jade.core.AID;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class EnergyDeserialization {
    public static Energy deserializeFromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Energy.class);
    }

    public static Map<AID, Energy> deserializeMapEnergyFromJson(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Energy>>(){}.getType();
        return gson.fromJson(jsonString, type);
    }

    public static List<Energy> deserializeListEnergyFromJson(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Energy>>(){}.getType();
        return gson.fromJson(jsonString, type);
    }
}

