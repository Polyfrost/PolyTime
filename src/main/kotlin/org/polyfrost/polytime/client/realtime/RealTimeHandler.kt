package org.polyfrost.polytime.client.realtime

import net.minecraft.client.Minecraft
import org.apache.logging.log4j.LogManager
import org.polyfrost.oneconfig.api.notifications.v1.Notifications
import org.polyfrost.oneconfig.utils.v1.JsonUtils
import org.polyfrost.oneconfig.utils.v1.Multithreading
import org.polyfrost.polytime.client.PolyTimeConfig
import org.polyfrost.polytime.irlTime
import org.polyfrost.polytime.isWithinPeriod
import org.polyfrost.polytime.map
import org.shredzone.commons.suncalc.MoonIllumination
import org.shredzone.commons.suncalc.MoonPhase
import org.shredzone.commons.suncalc.SunTimes
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.concurrent.atomic.AtomicBoolean

object RealTimeHandler {
    private val logger = LogManager.getLogger(RealTimeHandler::class.java)

    private val currentlyUpdatingTime = AtomicBoolean(false)

    @Volatile
    private var sunData: RealTimeData? = null

    @Volatile
    private var sunDataExpiresAt = 0L

    @Volatile
    private var failureNotified = false

    @Volatile
    private var nextTimeUpdateAttempt = 0L

    private const val TIME_UPDATE_RETRY_INTERVAL_MS = 30L * 1000L

    private var cachedLunarPhase = 0
    private var lastLunarPhaseUpdate = 0L
    private const val LUNAR_PHASE_UPDATE_INTERVAL_MS = 60L * 60L * 1000L

    @JvmStatic
    val currentLunarPhase: Int
        get() {
            val now = System.currentTimeMillis()

            if (now - lastLunarPhaseUpdate >= LUNAR_PHASE_UPDATE_INTERVAL_MS) {
                populateCurrentLunarPhase()
                lastLunarPhaseUpdate = now
            }

            return cachedLunarPhase
        }

    val currentTime: Float?
        get() {
            val sunData = sunData
            if (sunData == null) {
                populateTime()
                return null
            }

            if (System.currentTimeMillis() >= sunDataExpiresAt) {
                populateTime() // keep using yesterday's data until the refresh lands
            }

            when {
                sunData.isAlwaysUp -> return 6f
                sunData.isAlwaysDown -> return 18f

                else -> {
                    fun Float.calculateMappedTime(periodStart: Float, periodEnd: Float, gameStart: Float, gameEnd: Float): Float {
                        var current = this
                        var periodEnd = periodEnd
                        var gameEnd = gameEnd
                        if (periodEnd < periodStart || gameEnd < gameStart) {
                            periodEnd += 24f
                            gameEnd += 24f
                            if (current < periodStart) current += 24f
                        }

                        return map(current, periodStart, periodEnd, gameStart, gameEnd)
                    }

                    val irlTime = irlTime
                    return when {
                        irlTime.isWithinPeriod(sunData.sunrise, sunData.noon) -> irlTime.calculateMappedTime(sunData.sunrise, sunData.noon, 5f, 12f)
                        irlTime.isWithinPeriod(sunData.noon, sunData.sunset) -> irlTime.calculateMappedTime(sunData.noon, sunData.sunset, 12f, 19f)
                        irlTime.isWithinPeriod(sunData.sunset, sunData.nadir) -> irlTime.calculateMappedTime(sunData.sunset, sunData.nadir, 19f, 0f)
                        else -> irlTime.calculateMappedTime(sunData.nadir, sunData.sunrise, 0f, 5f)
                    }
                }
            }
        }

    fun initialize() {
        if (!PolyTimeConfig.isIrlTime) {
            return
        }

        populateTime()
    }

    fun populateTime() {
        if (System.currentTimeMillis() < nextTimeUpdateAttempt) {
            return
        }

        if (!currentlyUpdatingTime.compareAndSet(false, true)) {
            return
        }

        try {
            Multithreading.submit(::fetchTime)
        } catch (e: Exception) {
            logger.error("Failed to schedule real-time data fetch", e)
            onFetchFailed()
            currentlyUpdatingTime.set(false)
        }
    }

    private fun fetchTime() {
        var updateSucceeded = false

        try {
            val (longitude, latitude) = obtainLongitudeLatitude() ?: return
            val times = SunTimes.compute()
                .at(latitude, longitude)
                .today()
                .oneDay()
                .timezone(Calendar.getInstance().timeZone)
                .execute()
            val data = RealTimeData.from(times) ?: return logger.error("Failed to obtain real-time data")
            sunDataExpiresAt = LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            sunData = data
            nextTimeUpdateAttempt = 0L
            updateSucceeded = true
            logger.info("Obtained real-time data: $data")
        } catch (e: Exception) {
            logger.error("Failed to obtain real-time data", e)
        } finally {
            if (!updateSucceeded) {
                onFetchFailed()
            }
            currentlyUpdatingTime.set(false)
        }
    }

    private fun onFetchFailed() {
        nextTimeUpdateAttempt = System.currentTimeMillis() + TIME_UPDATE_RETRY_INTERVAL_MS

        if (sunData == null && !failureNotified) {
            failureNotified = true
            Minecraft.getInstance().execute {
                Notifications.error(
                    "PolyTime",
                    "Couldn't get your location for IRL time. Using server time until it works, retrying every ${TIME_UPDATE_RETRY_INTERVAL_MS / 1000}s."
                )
            }
        }
    }

    private fun populateCurrentLunarPhase() {
        val illumination = MoonIllumination.compute()
            .today()
            .timezone(Calendar.getInstance().timeZone)
            .execute()
        cachedLunarPhase = when (illumination.closestPhase) {
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

        logger.info("Obtained lunar phase: $cachedLunarPhase")
    }

    private fun obtainLongitudeLatitude(): Pair<Double, Double>? {
        val json = JsonUtils.parseFromUrl("http://ip-api.com/json/")?.asJsonObject
        if (json == null) {
            logger.error("Failed to obtain JSON from ip-api.com")
            return null
        }

        if (!json.has("lat") || !json.has("lon")) {
            logger.error("ip-api.com did not return longitude and latitude values")
            return null
        }

        try {
            val longitude = json.get("lon").asDouble
            val latitude = json.get("lat").asDouble
            return Pair(longitude, latitude)
        } catch (e: Exception) {
            logger.error("Failed to obtain latitude and longitude from ip-api.com JSON", e)
            return null
        }
    }
}
