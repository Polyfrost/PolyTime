package cc.polyfrost.polytime.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Checkbox;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.polytime.PolyTime;

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
    public TimeConfig() {
        super(new Mod("Time Changer", ModType.UTIL_QOL), PolyTime.MODID + ".json");
        initialize();
        addListener("irlTime", PolyTime::calculateSunriseSunset);
    }
}

