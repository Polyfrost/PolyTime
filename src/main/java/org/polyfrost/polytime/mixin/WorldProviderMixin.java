package org.polyfrost.polytime.mixin;

import org.polyfrost.polytime.PolyTime;
import org.polyfrost.polytime.config.TimeConfig;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@SideOnly(Side.CLIENT)
@Mixin(WorldProvider.class)
public class WorldProviderMixin {

    @Overwrite
    public int getMoonPhase(long worldTime) {
        if (PolyTime.INSTANCE.config != null && PolyTime.INSTANCE.config.enabled && TimeConfig.irlLunarPhases)
            return PolyTime.lunarPhase;
        return (int) (worldTime / 24000L % 8L + 8L) % 8;
    }
}
