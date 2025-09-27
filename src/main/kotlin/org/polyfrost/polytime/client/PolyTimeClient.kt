package org.polyfrost.polytime.client

import com.mojang.brigadier.Command
import org.polyfrost.oneconfig.api.commands.v1.CommandManager
import org.polyfrost.oneconfig.utils.v1.dsl.openUI
import org.polyfrost.polytime.PolyTimeConstants
import org.polyfrost.polytime.client.realtime.RealTimeHandler
import org.polyfrost.polytime.realTimeToGameTime

object PolyTimeClient {
    @JvmStatic
    val currentTime: Long
        get() = (if (PolyTimeConfig.isIrlTime) RealTimeHandler.currentTime else PolyTimeConfig.time).realTimeToGameTime()

    fun initialize() {
        PolyTimeConfig.preload()
        CommandManager.register(with(CommandManager.literal(PolyTimeConstants.ID)) {
            executes {
                PolyTimeConfig.openUI()
                Command.SINGLE_SUCCESS
            }
        })

        RealTimeHandler.initialize()
    }
}
