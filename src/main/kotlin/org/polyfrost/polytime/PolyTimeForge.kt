package org.polyfrost.polytime

//#if FORGE == 1

import net.minecraftforge.fml.common.Mod

//#if MC <= 11202
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