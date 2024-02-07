package org.polyfrost.polytime.mixin;

import org.polyfrost.polytime.config.ModConfig;
import org.polyfrost.polytime.PolyTime;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.mixin.*;

@SideOnly(Side.CLIENT)
@Mixin(WorldInfo.class)
public class WorldInfoMixin {

    @Shadow
    private long worldTime;

    @Overwrite
    public long getWorldTime() {
        if (ModConfig.INSTANCE.enabled)
            return PolyTime.INSTANCE.timeToTicks();
        return this.worldTime;
    }
}
