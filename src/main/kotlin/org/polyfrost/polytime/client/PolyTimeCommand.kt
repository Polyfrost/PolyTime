package org.polyfrost.polytime.client

import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Command
import org.polyfrost.oneconfig.utils.v1.dsl.openUI
import org.polyfrost.polytime.PolyTimeConstants

@Command(PolyTimeConstants.ID)
class PolyTimeCommand {

    @Command
    private fun main() {
        PolyTimeConfig.openUI()
    }

}