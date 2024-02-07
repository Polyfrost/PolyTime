package org.polyfrost.polytime.command

import cc.polyfrost.oneconfig.utils.commands.annotations.*
import org.polyfrost.polytime.config.ModConfig
import org.polyfrost.polytime.PolyTime

@Command(value = PolyTime.MODID, description = "Access the " + PolyTime.NAME + " GUI.", aliases = ["timechanger"])
object TimeCommand {
    @Main
    private fun main() {
        ModConfig.openGui()
    }
}