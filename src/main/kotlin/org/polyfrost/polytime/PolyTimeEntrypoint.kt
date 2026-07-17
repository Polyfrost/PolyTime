package org.polyfrost.polytime

import net.fabricmc.api.ClientModInitializer

import org.polyfrost.polytime.client.PolyTimeClient

class PolyTimeEntrypoint : ClientModInitializer {

    override fun onInitializeClient() {
        PolyTimeClient.initialize()
    }
}
