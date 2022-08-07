package cc.polyfrost.polytime.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.utils.NetworkUtils;
import cc.polyfrost.polytime.PolyTime;
import com.google.gson.JsonObject;

public class TimeConfig extends Config {

    @Slider(
            name = "Time",
            min = 0,
            max = 24
    )
    public static float time = 12;

    @Checkbox(
            name = "Use IRL time",
            size = 2
    )
    public static boolean irlTime = false;

    public static void fetchLocation() {
        if (!irlTime) return;
        JsonObject json = NetworkUtils.getJsonElement("http://ip-api.com/json/").getAsJsonObject();
        if (!json.has("lat") || !json.has("lon")) return;
        latitude = json.get("lat").getAsDouble();
        longitude = json.get("lon").getAsDouble();
        PolyTime.calculateSunriseSunset();
    }

    @Exclude
    public static Double latitude = null;

    @Exclude
    public static Double longitude = null;

    public TimeConfig() {
        super(new Mod("Time Changer", ModType.UTIL_QOL), PolyTime.MODID + ".json");
        initialize();
        addListener("irlTime", TimeConfig::fetchLocation);
    }
}

