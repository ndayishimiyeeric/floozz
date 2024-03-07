package Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jade.core.AID;

import java.lang.reflect.Type;
import java.util.Map;

public class EnergyDeserialization {
    public static Energy deserializeFromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Energy.class);
    }

    public static Map<AID, Energy> deserializeMapEnergyFromJson(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<AID, Energy>>(){}.getType();
        System.out.println("jasontring " + jsonString);

        return gson.fromJson(jsonString, type);
    }
}

