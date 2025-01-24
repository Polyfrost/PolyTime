package org.polyfrost.polytime.mixin;

import org.polyfrost.polytime.client.PolyTimeClient;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.spongepowered.asm.mixin.*;

//#if MC <= 1.12.2
import net.minecraft.world.storage.WorldInfo;
//#else
//$$ import net.minecraft.client.multiplayer.ClientLevel;
//#endif

//#if MC <= 1.12.2
@Mixin(WorldInfo.class)
//#else
//$$ @Mixin(ClientLevel.ClientLevelData.class)
//#endif
public class WorldInfoMixin {

    //#if MC <= 1.12.2
    @Shadow
    private long worldTime;

    /**
     * @author Deftu
     * @reason Replace the world time with the PolyTime time
     */
    @Overwrite
    public long getWorldTime() {
        if (PolyTimeConfig.INSTANCE.getEnabled()) {
            return PolyTimeClient.getCurrentTime();
        }

        return this.worldTime;
    }
    //#else
    //$$ @Shadow
    //$$ private long dayTime;
    //$$
    //$$ @Overwrite
    //$$ public long getDayTime() {
    //$$     if (PolyTimeConfig.INSTANCE.getEnabled()) {
    //$$         return PolyTimeClient.getCurrentTime();
    //$$     }
    //$$
    //$$     return this.dayTime;
    //$$ }
    //#endif
}
