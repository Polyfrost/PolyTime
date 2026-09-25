package org.polyfrost.polytime.client

import org.polyfrost.polytime.client.realtime.RealTimeHandler
import org.polyfrost.polytime.realTimeToGameTime

object PolyTimeClient {
    @JvmStatic
    val currentTime: Long
        get() = if (PolyTimeConfig.isIrlTime) {
            RealTimeHandler.currentTime?.realTimeToGameTime() ?: NO_TIME
        } else {
            PolyTimeConfig.time.realTimeToGameTime()
        }

    const val NO_TIME = -1L

    @JvmStatic
    fun adjustTicks(ticks: Long): Long {
        val time = currentTime
        return if (time == NO_TIME) ticks else ticks - Math.floorMod(ticks, 24000L) + time
    }

    fun initialize() {
        PolyTimeConfig.preload()
        RealTimeHandler.initialize()
    }
}
