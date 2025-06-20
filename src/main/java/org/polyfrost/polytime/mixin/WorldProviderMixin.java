package org.polyfrost.polytime.mixin;

import org.polyfrost.polytime.client.PolyTimeConfig;
import org.polyfrost.polytime.client.realtime.RealTimeHandler;
import org.spongepowered.asm.mixin.*;

//#if MC <= 1.12.2
import net.minecraft.world.WorldProvider;
//#elseif MC >=1.21.4
//$$ import net.minecraft.world.dimension.DimensionType;
//#else
//$$ import net.minecraft.client.MinecraftClient;
//#endif

//#if MC <= 1.12.2
@Mixin(WorldProvider.class)
//#elseif MC >=1.21.4
//$$ @Mixin(DimensionType.class)
//#else
//$$ import net.minecraft.client.MinecraftClient;
//#endif
public class WorldProviderMixin {

    //#if MC <= 1.12.2 || MC >= 1.21.4
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
    //#endif
}
