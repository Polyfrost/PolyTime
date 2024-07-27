package org.polyfrost.polytime

//#if FORGE == 1

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(
    modid = PolyTime.MODID,
    name = PolyTime.NAME,
    version = PolyTime.VERSION,
    //modLanguageAdapter = "org.polyfrost.oneconfig.utils.v1.forge.KotlinLanguageAdapter"
)
class PolyTimeForge {
    @Mod.EventHandler
    fun onFMLInitialization(event: FMLInitializationEvent?) {
        PolyTime.initialize()
    }
}

//#endif