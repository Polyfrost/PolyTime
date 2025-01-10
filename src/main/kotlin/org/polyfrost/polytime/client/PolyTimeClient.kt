package org.polyfrost.polytime.client

import org.polyfrost.oneconfig.api.commands.v1.CommandManager
import org.polyfrost.polytime.client.realtime.RealTimeHandler
import org.polyfrost.polytime.realTimeToGameTime

object PolyTimeClient {

    @JvmStatic
    val currentTime: Long
        get() = (if (PolyTimeConfig.irlTime) RealTimeHandler.currentTime else PolyTimeConfig.time).realTimeToGameTime()

    fun initialize() {
        PolyTimeConfig.preload()
        CommandManager.registerCommand(PolyTimeCommand())

        RealTimeHandler.initialize()
    }

}
