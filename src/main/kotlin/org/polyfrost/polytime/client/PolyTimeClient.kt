package org.polyfrost.polytime.client

import com.llamalad7.mixinextras.injector.wrapoperation.Operation
import org.polyfrost.polytime.client.realtime.RealTimeHandler
import org.polyfrost.polytime.realTimeToGameTime

object PolyTimeClient {
    @JvmStatic
    val currentTime: Long
        get() = if (PolyTimeConfig.isIrlTime) {
            RealTimeHandler.currentTime?.realTimeToGameTime() ?: NO_TIME
        } else if (PolyTimeConfig.fastTime) {
            (System.currentTimeMillis() * 0.02 * PolyTimeConfig.fastSpeed % 24000).toLong()
        } else {
            PolyTimeConfig.time.realTimeToGameTime()
        }

    const val NO_TIME = -1L

    private var overriding = false

    @JvmStatic
    val shouldOverride: Boolean
        get() = overriding && PolyTimeConfig.isEnabled

    @JvmStatic
    fun overriding(original: Operation<Void>, vararg args: Any?) {
        val previous = overriding
        overriding = true
        try {
            original.call(*args)
        } finally {
            overriding = previous
        }
    }

    @JvmStatic
    fun adjustTicks(ticks: Long): Long {
        val time = currentTime
        return if (time == NO_TIME) ticks else ticks - Math.floorMod(ticks, 24000L) + Math.floorMod(time, 24000L)
    }

    fun initialize() {
        PolyTimeConfig.preload()
        RealTimeHandler.initialize()
    }
}
