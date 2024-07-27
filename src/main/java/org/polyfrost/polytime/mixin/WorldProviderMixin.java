package org.polyfrost.polytime.mixin;

import org.polyfrost.polytime.config.ModConfig;
import org.polyfrost.polytime.PolyTime;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.*;

@Mixin(WorldProvider.class)
public class WorldProviderMixin {

    @Overwrite
    public int getMoonPhase(long worldTime) {
        if (ModConfig.INSTANCE.getEnabled() && ModConfig.INSTANCE.getIrlLunarPhases())
            return PolyTime.INSTANCE.getLunarPhase();
        return (int) (worldTime / 24000L % 8L + 8L) % 8;
    }
}
