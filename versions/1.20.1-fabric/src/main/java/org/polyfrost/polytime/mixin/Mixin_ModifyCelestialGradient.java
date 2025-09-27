package org.polyfrost.polytime.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.MutableWorldProperties;
import org.polyfrost.polytime.client.PolyTimeClient;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class Mixin_ModifyCelestialGradient extends World {
    protected Mixin_ModifyCelestialGradient(MutableWorldProperties mutableWorldProperties, RegistryKey<World> registryKey, DynamicRegistryManager dynamicRegistryManager, RegistryEntry<DimensionType> registryEntry, Supplier<Profiler> supplier, boolean bl, boolean bl2, long l, int i) {
        super(mutableWorldProperties, registryKey, dynamicRegistryManager, registryEntry, supplier, bl, bl2, l, i);
    }

    @Override
    public float getSkyAngle(float tickDelta) {
        if (PolyTimeConfig.isEnabled()) {
            return polyweather$angleFromTime(PolyTimeClient.getCurrentTime(), tickDelta);
        }

        return super.getSkyAngle(tickDelta);
    }

    @Unique
    private float polyweather$angleFromTime(float time, float tickDelta) {
        float f = ((time % 24000L) + tickDelta) / 24000f - 0.25f;
        if (f < 0f) f += 1f;
        if (f > 1f) f -= 1f;
        return 1f - (float) ((Math.cos(f * Math.PI) + 1.0) / 2.0);
    }
}
