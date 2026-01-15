package org.polyfrost.polytime

import net.fabricmc.api.ModInitializer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer

import org.polyfrost.polytime.client.PolyTimeClient

class PolyTimeEntrypoint : ClientModInitializer {
    override
    fun onInitializeClient() {
        PolyTimeClient.initialize()
    }
}
