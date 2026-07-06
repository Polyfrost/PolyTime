package org.polyfrost.polytime.mixin;

import org.polyfrost.polytime.client.PolyTimeClient;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.spongepowered.asm.mixin.*;

//#if MC <= 1.12.2
import net.minecraft.world.storage.WorldInfo;
//#elseif MC >=1.21.4
//$$ import net.minecraft.client.world.ClientWorld;
//#else
//$$ import net.minecraft.client.MinecraftClient;
//#endif

//#if MC <= 1.12.2
@Mixin(WorldInfo.class)
//#elseif MC >=1.21.4
//$$ @Mixin(ClientWorld.Properties.class)
//#else
//$$ @Mixin(MinecraftClient.class)
//#endif
public class WorldInfoMixin {

    //#if MC <= 1.12.2 || MC >= 1.21.4
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
    //$$ @Overwrite(remap = false)
    //$$ public long getDayTime() {
    //$$     if (PolyTimeConfig.INSTANCE.getEnabled()) {
    //$$         return PolyTimeClient.getCurrentTime();
    //$$     }
    //$$
    //$$     return this.dayTime;
    //$$ }
    //#endif
}
