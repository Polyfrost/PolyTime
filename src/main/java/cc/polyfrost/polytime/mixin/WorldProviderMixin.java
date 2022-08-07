package cc.polyfrost.polytime.mixin;

import cc.polyfrost.polytime.PolyTime;
import cc.polyfrost.polytime.config.TimeConfig;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WorldProvider.class)
public class WorldProviderMixin {

    @Overwrite
    public int getMoonPhase(long worldTime) {
        if (PolyTime.INSTANCE.config != null && PolyTime.INSTANCE.config.enabled && TimeConfig.irlLunarPhases)
            return PolyTime.lunarPhase;
        return (int) (worldTime / 24000L % 8L + 8L) % 8;
    }
}
