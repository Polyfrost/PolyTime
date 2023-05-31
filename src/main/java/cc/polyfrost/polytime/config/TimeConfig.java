package cc.polyfrost.polytime.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Checkbox;
import cc.polyfrost.oneconfig.config.annotations.Info;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.data.InfoType;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.polytime.PolyTime;

public class TimeConfig extends Config {

    @Info(
            text = "Credits to Fyu for the original TimeChanger mod.",
            type = InfoType.INFO
    )
    private static boolean credits;

    @Slider(
            name = "Time",
            min = 0,
            max = 24
    )
    public static float time = 12;

    @Checkbox(name = "Use IRL time")
    public static boolean irlTime = false;

    @Checkbox(name = "Use IRL lunar phase")
    public static boolean irlLunarPhases = false;

    public TimeConfig() {
        super(new Mod("Time Changer", ModType.UTIL_QOL, "/polytime_dark.svg"), PolyTime.MODID + ".json", false);
        initialize();
        addListener("irlTime", PolyTime::calculateSunriseSunset);
        addListener("irlLunarPhases", PolyTime::calculateMoonPhases);
    }
}

