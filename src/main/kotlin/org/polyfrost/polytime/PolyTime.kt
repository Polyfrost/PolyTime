package org.polyfrost.polytime

import cc.polyfrost.oneconfig.utils.NetworkUtils
import cc.polyfrost.oneconfig.utils.commands.CommandManager
import com.google.gson.JsonObject
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.polyfrost.polytime.config.ModConfig
import org.polyfrost.polytime.command.TimeCommand
import org.shredzone.commons.suncalc.*
import java.time.ZonedDateTime
import java.util.*

@Mod(modid = PolyTime.MODID, name = PolyTime.NAME, version = PolyTime.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object PolyTime {

    @Mod.EventHandler
    fun onFMLInitialization(event: FMLInitializationEvent?) {
        ModConfig
        CommandManager.INSTANCE.registerCommand(TimeCommand)
        calculateSunriseSunset()
        calculateMoonPhases()
    }

    const val MODID: String = "@ID@"
    const val NAME: String = "@NAME@"
    const val VERSION: String = "@VER@"

    var lunarPhase: Int = 0
    private var gotData = false
    private var alwaysUp = false
    private var alwaysDown = false
    private var rise = 0f
    private var noon = 0f
    private var set = 0f
    private var nadir = 0f

    // O-24000, starts at 6
    // 5 -> sunrise
    // 19 -> sunset
    fun timeToTicks(): Long {
        if (!ModConfig.irlTime && ModConfig.fastTime) return (Minecraft.getSystemTime() % (24000 / ModConfig.fastSpeed) * ModConfig.fastSpeed).toLong()

        if (!ModConfig.irlTime || !gotData) return timeToMcTime(ModConfig.time)
        if (alwaysUp) return 6000L
        if (alwaysDown) return 18000L
        val currentTime = getCurrentTime()
        if (isInPeriod(currentTime, rise, noon)) return realTimeToMinecraftTime(currentTime, rise, noon, 5f, 12f)
        if (isInPeriod(currentTime, noon, set)) return realTimeToMinecraftTime(currentTime, noon, set, 12f, 19f)
        if (isInPeriod(currentTime, set, nadir)) return realTimeToMinecraftTime(currentTime, set, nadir, 19f, 0f)
        return realTimeToMinecraftTime(currentTime, nadir, rise, 0f, 5f)
    }

    fun calculateSunriseSunset() {
        val json: JsonObject = NetworkUtils.getJsonElement("http://ip-api.com/json/").asJsonObject
        if (!json.has("lat") || !json.has("lon")) return
        val latitude: Double = json.get("lat").asDouble
        val longitude: Double = json.get("lon").asDouble
        val times: SunTimes = SunTimes.compute()
            .at(latitude, longitude)
            .today()
            .oneDay()
            .timezone(Calendar.getInstance().getTimeZone())
            .execute()
        alwaysUp = times.isAlwaysUp
        alwaysDown = times.isAlwaysDown
        if (times.rise != null) rise = parseZonedTime(times.rise!!)
        if (times.noon != null) noon = parseZonedTime(times.noon!!)
        if (times.set != null) set = parseZonedTime(times.set!!)
        if (times.nadir != null) nadir = parseZonedTime(times.nadir!!)
        gotData = true
    }

    fun calculateMoonPhases() {
        val illumination: MoonIllumination = MoonIllumination.compute()
            .today()
            .timezone(Calendar.getInstance().getTimeZone())
            .execute()
        lunarPhase = when (illumination.closestPhase) {
            MoonPhase.Phase.FULL_MOON -> 0
            MoonPhase.Phase.WANING_GIBBOUS -> 1
            MoonPhase.Phase.LAST_QUARTER -> 2
            MoonPhase.Phase.WANING_CRESCENT -> 3
            MoonPhase.Phase.NEW_MOON -> 4
            MoonPhase.Phase.WAXING_CRESCENT -> 5
            MoonPhase.Phase.FIRST_QUARTER -> 6
            MoonPhase.Phase.WAXING_GIBBOUS -> 7
            else -> 0
        }
    }

    private fun parseZonedTime(time: ZonedDateTime): Float {
        return time.hour + time.minute / 60f + time.second / 3600f
    }

    private fun timeToMcTime(time: Float): Long {
        return (time * 1000L).toLong() + 18000L
    }

    private fun getCurrentTime(): Float {
        val calendar: Calendar = Calendar.getInstance()
        return calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE) / 60f + calendar.get(Calendar.SECOND) / 3600f + calendar.get(Calendar.MILLISECOND) / 3600000f
    }

    private fun realTimeToMinecraftTime(currentTime: Float, periodStart: Float, periodEnd: Float, mcStart: Float, mcEnd: Float): Long {
        var currentTime = currentTime
        var periodEnd = periodEnd
        var mcEnd = mcEnd
        if (periodEnd < periodStart || mcEnd < mcStart) {
            periodEnd += 24f
            mcEnd += 24f
            if (currentTime < periodStart) currentTime += 24f
        }
        return timeToMcTime(map(currentTime, periodStart, periodEnd, mcStart, mcEnd))
    }

    private fun isInPeriod(currentTime: Float, periodStart: Float, periodEnd: Float): Boolean {
        var currentTime = currentTime
        var periodEnd = periodEnd
        if (periodEnd < periodStart) {
            periodEnd += 24f
            if (currentTime < periodStart) currentTime += 24f
        }
        return currentTime in periodStart..periodEnd
    }

    private fun map(x: Float, in_min: Float, in_max: Float, out_min: Float, out_max: Float): Float {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
    }

}

