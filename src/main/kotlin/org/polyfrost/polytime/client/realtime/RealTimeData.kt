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

    private companion object {

        private val logger = LogManager.getLogger(RealTimeData::class.java)

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

    constructor(
        isAlwaysUp: Boolean,
        isAlwaysDown: Boolean,
        times: SunTimes
    ) : this(
        isAlwaysUp,
        isAlwaysDown,
        times.rise ?: return logger.error("Sunrise time is null"),
        times.set ?: return logger.error("Sunset time is null"),
        times.noon ?: return logger.error("Noon time is null"),
        times.nadir ?: return logger.error("Nadir time is null")
    )

}
