package org.polyfrost.polytime.mixin;

import org.polyfrost.polytime.config.ModConfig;
import org.polyfrost.polytime.PolyTime;
import org.spongepowered.asm.mixin.*;

//#if MC <= 11202
import net.minecraft.world.WorldProvider;
//#else
//$$ import net.minecraft.world.level.dimension.DimensionType;
//#endif

//#if MC <= 11202
@Mixin(WorldProvider.class)
//#else
//$$ @Mixin(DimensionType.class)
//#endif
public class WorldProviderMixin {

    @Overwrite
    public int
    //#if MC <= 11202
    getMoonPhase
    //#else
    //$$ moonPhase
    //#endif
            (long worldTime) {
        if (ModConfig.INSTANCE.getEnabled() && ModConfig.INSTANCE.getIrlLunarPhases())
            return PolyTime.INSTANCE.getLunarPhase();
        return (int) (worldTime / 24000L % 8L + 8L) % 8;
    }
}
