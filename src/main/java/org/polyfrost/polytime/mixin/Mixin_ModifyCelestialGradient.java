package org.polyfrost.polytime.mixin;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.polyfrost.polytime.client.PolyTimeClient;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WorldClient.class)
public abstract class Mixin_ModifyCelestialGradient extends World {
    protected Mixin_ModifyCelestialGradient(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client) {
        super(saveHandlerIn, info, providerIn, profilerIn, client);
    }

    @Override
    public float getCelestialAngle(float tickDelta) {
        if (PolyTimeConfig.isEnabled()) {
            return polyweather$angleFromTime(PolyTimeClient.getCurrentTime(), tickDelta);
        }

        return super.getCelestialAngle(tickDelta);
    }

    @Unique
    private float polyweather$angleFromTime(float time, float tickDelta) {
        float f = ((time % 24000L) + tickDelta) / 24000f - 0.25f;
        if (f < 0f) f += 1f;
        if (f > 1f) f -= 1f;
        return 1f - (float) ((Math.cos(f * Math.PI) + 1.0) / 2.0);
    }
}
