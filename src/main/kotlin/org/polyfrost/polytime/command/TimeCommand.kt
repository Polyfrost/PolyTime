package org.polyfrost.polytime.command

import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Command
import org.polyfrost.polytime.config.ModConfig
import org.polyfrost.polytime.PolyTime

@Command(PolyTime.MODID, "timechanger")
object TimeCommand {
    @Command
    private fun main() {
        // TODO
        // ModConfig.openGui()
    }
}