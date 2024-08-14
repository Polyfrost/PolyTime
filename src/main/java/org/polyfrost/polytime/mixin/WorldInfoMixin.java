package org.polyfrost.polytime.mixin;

import org.polyfrost.polytime.config.ModConfig;
import org.polyfrost.polytime.PolyTime;
import org.spongepowered.asm.mixin.*;

//#if MODERN==0
import net.minecraft.world.storage.WorldInfo;
//#else
//$$ import net.minecraft.client.multiplayer.ClientLevel;
//#endif

//#if MODERN == 0
@Mixin(WorldInfo.class)
//#else
//$$ @Mixin(ClientLevel.ClientLevelData.class)
//#endif
public class WorldInfoMixin {

    //#if MODERN == 0
    @Shadow
    private long worldTime;

    @Overwrite
    public long getWorldTime() {
        if (ModConfig.INSTANCE.getEnabled())
            return PolyTime.INSTANCE.timeToTicks();
        return this.worldTime;
    }
    //#else
    //$$ @Shadow
    //$$ private long dayTime;
    //$$
    //$$ @Overwrite
    //$$ public long getDayTime() {
    //$$     if (ModConfig.INSTANCE.getEnabled())
    //$$         return PolyTime.INSTANCE.timeToTicks();
    //$$     return this.dayTime;
    //$$ }
    //#endif
}
