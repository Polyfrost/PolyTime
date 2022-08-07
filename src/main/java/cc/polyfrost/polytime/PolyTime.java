package cc.polyfrost.polytime;

import cc.polyfrost.polytime.command.TimeCommand;
import cc.polyfrost.polytime.config.TimeConfig;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@net.minecraftforge.fml.common.Mod(modid = PolyTime.MODID, name = PolyTime.NAME, version = PolyTime.VERSION)
public class PolyTime {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    @net.minecraftforge.fml.common.Mod.Instance(MODID)
    public static PolyTime INSTANCE;
    public TimeConfig config;
    private static Float sunrise = null;
    private static Float sunset = null;

    @net.minecraftforge.fml.common.Mod.EventHandler
    public void onFMLInitialization(net.minecraftforge.fml.common.event.FMLInitializationEvent event) {
        config = new TimeConfig();
        CommandManager.INSTANCE.registerCommand(TimeCommand.class);
        TimeConfig.fetchLocation();
    }

    // O-24000, starts at 6am
    // 5am -> sunrise
    // 7pm -> sunset
    public static long timeToTicks() {
        if (!TimeConfig.irlTime || sunrise == null || sunset == null)
            return (long) (TimeConfig.time * 1000L) + 18000L;
        float currentTime = getCurrentTime();
        if (currentTime >= sunrise && currentTime <= sunset)
            return (long) (map(currentTime, sunrise, sunset, 5f, 19f) * 1000L) + 18000L;
        return (long) (map(currentTime, sunset, sunrise, 19f, 29f) * 1000L) + 18000L;
    }

    public static void calculateSunriseSunset() {
        Location location = new Location(TimeConfig.latitude, TimeConfig.longitude);
        SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, Calendar.getInstance().getTimeZone());
        sunrise = parse24HourTime(calculator.getCivilSunriseForDate(Calendar.getInstance()));
        sunset = parse24HourTime(calculator.getCivilSunsetForDate(Calendar.getInstance()));
    }

    private static float parse24HourTime(String time) {
        String[] split = time.split(":");
        return Integer.parseInt(split[0]) + Integer.parseInt(split[1]) / 60f;
    }

    private static float getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE) / 60f + calendar.get(Calendar.SECOND) / 3_600f
                + calendar.get(Calendar.MILLISECOND) / 3_600_000f;
    }

    private static float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
