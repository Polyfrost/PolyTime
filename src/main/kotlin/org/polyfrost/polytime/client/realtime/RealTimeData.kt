package org.polyfrost.polytime.client.realtime

import org.apache.logging.log4j.LogManager
import org.shredzone.commons.suncalc.SunTimes
import java.time.ZonedDateTime

data class RealTimeData(
    val isAlwaysUp: Boolean,
    val isAlwaysDown: Boolean,
    val sunrise: Float,
    val sunset: Float,
    val noon: Float,
    val nadir: Float
) {
    companion object {
        private val logger = LogManager.getLogger(RealTimeData::class.java)

        @JvmStatic
        fun from(times: SunTimes): RealTimeData? {
            val sunrise = times.rise ?: return null.also { logger.error("Sunrise time is null") }
            val sunset = times.set ?: return null.also { logger.error("Sunset time is null") }
            val noon = times.noon ?: return null.also { logger.error("Noon time is null") }
            val nadir = times.nadir ?: return null.also { logger.error("Nadir time is null") }

            return RealTimeData(
                times.isAlwaysUp,
                times.isAlwaysDown,
                sunrise,
                sunset,
                noon,
                nadir
            )
        }

        private fun ZonedDateTime.parse(): Float {
            return hour + minute / 60f + second / 3600f
        }
    }

    constructor(
        isAlwaysUp: Boolean,
        isAlwaysDown: Boolean,
        sunrise: ZonedDateTime,
        sunset: ZonedDateTime,
        noon: ZonedDateTime,
        nadir: ZonedDateTime
    ) : this(
        isAlwaysUp,
        isAlwaysDown,
        sunrise.parse(),
        sunset.parse(),
        noon.parse(),
        nadir.parse()
    )
}
