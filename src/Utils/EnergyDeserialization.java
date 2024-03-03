package Utils;

import com.google.gson.Gson;

public class EnergyDeserialization {
    public static Energy deserializeFromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Energy.class);
    }
}

