package org.polyfrost.polytime.mixin;

import org.polyfrost.polytime.config.ModConfig;
import org.polyfrost.polytime.PolyTime;
import org.spongepowered.asm.mixin.*;

//#if MC <= 11202
import net.minecraft.world.storage.WorldInfo;
//#else
//$$ import net.minecraft.client.world.ClientWorld;
//#endif

//#if MC <= 11202
@Mixin(WorldInfo.class)
//#else
//$$ @Mixin(ClientWorld.Properties.class)
//#endif
public class WorldInfoMixin {

    //#if MC <= 11202
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
    //$$ private long timeOfDay;
    //$$
    //$$ @Overwrite
    //$$ //#if FABRIC == 1
    //$$ public long getTimeOfDay() {
    //$$ //#else
    //$$ //$$ public long getDayTime() {
    //$$ //#endif
    //$$     if (ModConfig.INSTANCE.getEnabled())
    //$$         return PolyTime.INSTANCE.timeToTicks();
    //$$     return this.timeOfDay;
    //$$ }
    //#endif
}
