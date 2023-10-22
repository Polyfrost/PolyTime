package org.polyfrost.polytime.mixin;

import org.polyfrost.polytime.PolyTime;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin(WorldInfo.class)
public class WorldInfoMixin {

    @Shadow
    private long worldTime;

    @Overwrite
    public long getWorldTime() {
        if (PolyTime.INSTANCE.config != null && PolyTime.INSTANCE.config.enabled)
            return PolyTime.timeToTicks();
        return this.worldTime;
    }
}
