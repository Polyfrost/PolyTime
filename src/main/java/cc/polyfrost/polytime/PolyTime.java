package cc.polyfrost.polytime;

import cc.polyfrost.oneconfig.utils.NetworkUtils;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import cc.polyfrost.polytime.command.TimeCommand;
import cc.polyfrost.polytime.config.TimeConfig;
import com.google.gson.JsonObject;
import org.shredzone.commons.suncalc.MoonIllumination;
import org.shredzone.commons.suncalc.SunTimes;

import java.time.ZonedDateTime;
import java.util.Calendar;

@net.minecraftforge.fml.common.Mod(modid = PolyTime.MODID, name = PolyTime.NAME, version = PolyTime.VERSION)
public class PolyTime {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    @net.minecraftforge.fml.common.Mod.Instance(MODID)
    public static PolyTime INSTANCE;
    public TimeConfig config;
    public static int lunarPhase = 0;
    private static boolean gotData = false;
    private static boolean alwaysUp;
    private static boolean alwaysDown;
    private static float rise;
    private static float noon;
    private static float set;
    private static float nadir;

    @net.minecraftforge.fml.common.Mod.EventHandler
    public void onFMLInitialization(net.minecraftforge.fml.common.event.FMLInitializationEvent event) {
        config = new TimeConfig();
        CommandManager.INSTANCE.registerCommand(TimeCommand.class);
        calculateSunriseSunset();
        calculateMoonPhases();
    }

    // O-24000, starts at 6
    // 5 -> sunrise
    // 19 -> sunset
    public static long timeToTicks() {
        if (!TimeConfig.irlTime || !gotData)
            return timeToMcTime(TimeConfig.time);
        if (alwaysUp)
            return 6000L;
        if (alwaysDown)
            return 18000L;
        float currentTime = getCurrentTime();
        if (isInPeriod(currentTime, rise, noon))
            return realTimeToMinecraftTime(currentTime, rise, noon, 5f, 12f);
        if (isInPeriod(currentTime, noon, set))
            return realTimeToMinecraftTime(currentTime, noon, set, 12f, 19f);
        if (isInPeriod(currentTime, set, nadir))
            return realTimeToMinecraftTime(currentTime, set, nadir, 19f, 0f);
        return realTimeToMinecraftTime(currentTime, nadir, rise, 0f, 5f);
    }

    public static void calculateSunriseSunset() {
        if (!TimeConfig.irlTime) return;
        JsonObject json = NetworkUtils.getJsonElement("http://ip-api.com/json/").getAsJsonObject();
        if (!json.has("lat") || !json.has("lon")) return;
        double latitude = json.get("lat").getAsDouble();
        double longitude = json.get("lon").getAsDouble();
        SunTimes times = SunTimes.compute()
                .at(latitude, longitude)
                .today()
                .oneDay()
                .timezone(Calendar.getInstance().getTimeZone())
                .execute();
        alwaysUp = times.isAlwaysUp();
        alwaysDown = times.isAlwaysDown();
        if (times.getRise() != null) rise = parseZonedTime(times.getRise());
        if (times.getNoon() != null) noon = parseZonedTime(times.getNoon());
        if (times.getSet() != null) set = parseZonedTime(times.getSet());
        if (times.getNadir() != null) nadir = parseZonedTime(times.getNadir());
        gotData = true;
    }

    public static void calculateMoonPhases() {
        if (!TimeConfig.irlLunarPhases) return;
        MoonIllumination illumination = MoonIllumination.compute()
                .today()
                .timezone(Calendar.getInstance().getTimeZone())
                .execute();
        // https://c.tadst.com/gfx/600x337/moon-phases-explained.png?1
        System.out.println(illumination.getClosestPhase());
        switch (illumination.getClosestPhase()) {
            case FULL_MOON:
                lunarPhase = 0;
                break;
            case WANING_GIBBOUS:
                lunarPhase = 1;
                break;
            case LAST_QUARTER:
                lunarPhase = 2;
                break;
            case WANING_CRESCENT:
                lunarPhase = 3;
                break;
            case NEW_MOON:
                lunarPhase = 4;
                break;
            case WAXING_CRESCENT:
                lunarPhase = 5;
                break;
            case FIRST_QUARTER:
                lunarPhase = 6;
                break;
            case WAXING_GIBBOUS:
                lunarPhase = 7;
                break;
        }
    }

    private static float parseZonedTime(ZonedDateTime time) {
        return time.getHour() + time.getMinute() / 60f + time.getSecond() / 3_600f;
    }

    private static long timeToMcTime(float time) {
        return (long) (time * 1_000L) + 18_000L;
    }

    private static float getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE) / 60f + calendar.get(Calendar.SECOND) / 3_600f
                + calendar.get(Calendar.MILLISECOND) / 3_600_000f;
    }

    private static long realTimeToMinecraftTime(float currentTime, float periodStart, float periodEnd, float mcStart, float mcEnd) {
        if (periodEnd < periodStart || mcEnd < mcStart) {
            periodEnd += 24;
            mcEnd += 24;
            if (currentTime < periodStart) currentTime += 24;
        }
        return timeToMcTime(map(currentTime, periodStart, periodEnd, mcStart, mcEnd));
    }

    private static boolean isInPeriod(float currentTime, float periodStart, float periodEnd) {
        if (periodEnd < periodStart) {
            periodEnd += 24;
            if (currentTime < periodStart) currentTime += 24;
        }
        return currentTime >= periodStart && currentTime <= periodEnd;
    }

    private static float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
