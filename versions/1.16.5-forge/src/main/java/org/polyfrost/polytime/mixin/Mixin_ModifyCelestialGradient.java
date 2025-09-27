package org.polyfrost.polytime.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.polyfrost.polytime.client.PolyTimeClient;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class Mixin_ModifyCelestialGradient extends Level {
    protected Mixin_ModifyCelestialGradient(WritableLevelData arg, ResourceKey<Level> arg2, DimensionType arg3, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l) {
        super(arg, arg2, arg3, supplier, bl, bl2, l);
    }

    @Override
    public float getTimeOfDay(float tickDelta) {
        if (PolyTimeConfig.isEnabled()) {
            return polyweather$angleFromTime(PolyTimeClient.getCurrentTime(), tickDelta);
        }

        return super.getTimeOfDay(tickDelta);
    }

    @Unique
    private float polyweather$angleFromTime(float time, float tickDelta) {
        float f = ((time % 24000L) + tickDelta) / 24000f - 0.25f;
        if (f < 0f) f += 1f;
        if (f > 1f) f -= 1f;
        return 1f - (float) ((Math.cos(f * Math.PI) + 1.0) / 2.0);
    }
}
