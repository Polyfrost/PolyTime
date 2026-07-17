package org.polyfrost.polytime.client

import org.polyfrost.polytime.client.realtime.RealTimeHandler
import org.polyfrost.polytime.realTimeToGameTime

object PolyTimeClient {
    @JvmStatic
    val currentTime: Long
        get() = (if (PolyTimeConfig.isIrlTime) RealTimeHandler.currentTime else PolyTimeConfig.time).realTimeToGameTime()

    fun initialize() {
        PolyTimeConfig.preload()
        RealTimeHandler.initialize()
    }
}
