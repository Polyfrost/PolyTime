package org.polyfrost.polytime

//#if FORGE

import net.minecraftforge.fml.common.Mod

//#if MODERN == 0
@Mod(
    modid = PolyTime.MODID,
    name = PolyTime.NAME,
    version = PolyTime.VERSION,
)
//#else
//$$ @Mod(PolyTime.MODID)
//#endif
class PolyTimeForge {

    init {
        PolyTime.initialize()
    }
}

//#endif