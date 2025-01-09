package org.polyfrost.polytime.client.realtime

import org.apache.logging.log4j.LogManager
import org.polyfrost.oneconfig.utils.v1.JsonUtils
import org.polyfrost.polytime.client.PolyTimeConfig
import org.polyfrost.polytime.irlTime
import org.polyfrost.polytime.isWithinPeriod
import org.polyfrost.polytime.map
import org.shredzone.commons.suncalc.MoonIllumination
import org.shredzone.commons.suncalc.MoonPhase
import org.shredzone.commons.suncalc.SunTimes
import java.util.Calendar

object RealTimeHandler {

    private val logger = LogManager.getLogger(RealTimeHandler::class.java)

    private lateinit var data: RealTimeData

    @JvmStatic
    var currentLunarPhase: Int = 0
        private set

    val currentTime: Float
        get() {
            if (!::data.isInitialized) {
                populate() // Hopefully this blocks the thread until the data is initialized
            }

            when {
                data.isAlwaysUp -> return 6f
                data.isAlwaysDown -> return 18f

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

                    val irlTime = irlTime // Caches the value
                    return when {
                        irlTime.isWithinPeriod(data.sunrise, data.noon) -> currentTime.calculateMappedTime(data.sunrise, data.noon, 5f, 12f)
                        irlTime.isWithinPeriod(data.noon, data.sunset) -> currentTime.calculateMappedTime(data.noon, data.sunset, 12f, 19f)
                        irlTime.isWithinPeriod(data.sunset, data.nadir) -> currentTime.calculateMappedTime(data.sunset, data.nadir, 19f, 0f)
                        else -> currentTime.calculateMappedTime(data.nadir, data.sunrise, 0f, 5f)
                    }
                }
            }
        }

    fun initialize() {
        if (!PolyTimeConfig.irlTime) {
            return
        }

        populate()
    }

    private fun populate() {
        val (latitude, longitude) = obtainLongitudeLatitude() ?: return
        val times = SunTimes.compute()
            .at(latitude, longitude)
            .today()
            .oneDay()
            .timezone(Calendar.getInstance().timeZone)
            .execute()
        data = RealTimeData(times.isAlwaysUp, times.isAlwaysDown, times)

        val illumination = MoonIllumination.compute()
            .at(latitude, longitude)
            .today()
            .timezone(Calendar.getInstance().timeZone)
            .execute()
        currentLunarPhase = when (illumination.closestPhase) {
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
            val latitude = json.get("lat").asDouble
            val longitude = json.get("lon").asDouble
            return Pair(latitude, longitude)
        } catch (e: Exception) {
            logger.error("Failed to obtain latitude and longitude from ip-api.com JSON", e)
            return null
        }
    }

}
