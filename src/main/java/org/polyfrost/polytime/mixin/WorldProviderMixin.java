package org.polyfrost.polytime.mixin;

import org.polyfrost.polytime.client.PolyTimeConfig;
import org.polyfrost.polytime.client.realtime.RealTimeHandler;
import org.spongepowered.asm.mixin.*;

//#if MC <= 1.12.2
import net.minecraft.world.WorldProvider;
//#else
//$$ import net.minecraft.world.level.dimension.DimensionType;
//#endif

//#if MC <= 1.12.2
@Mixin(WorldProvider.class)
//#else
//$$ @Mixin(DimensionType.class)
//#endif
public class WorldProviderMixin {

    /**
     * @author Deftu
     * @reason Implements lunar phases
     */
    @Overwrite
    public int
    //#if MC <= 1.12.2
    getMoonPhase
    //#else
    //$$ moonPhase
    //#endif
            (long worldTime) {
        if (PolyTimeConfig.INSTANCE.getEnabled() && PolyTimeConfig.INSTANCE.getIrlLunarPhases()) {
            return RealTimeHandler.getCurrentLunarPhase();
        }

        return (int) (worldTime / 24000L % 8L + 8L) % 8;
    }
}
