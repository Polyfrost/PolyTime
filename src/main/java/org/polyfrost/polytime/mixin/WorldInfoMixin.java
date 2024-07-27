package org.polyfrost.polytime.mixin;

import org.polyfrost.polytime.config.ModConfig;
import org.polyfrost.polytime.PolyTime;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.*;

@Mixin(WorldInfo.class)
public class WorldInfoMixin {

    @Shadow
    private long worldTime;

    @Overwrite
    public long getWorldTime() {
        if (ModConfig.INSTANCE.getEnabled())
            return PolyTime.INSTANCE.timeToTicks();
        return this.worldTime;
    }
}
