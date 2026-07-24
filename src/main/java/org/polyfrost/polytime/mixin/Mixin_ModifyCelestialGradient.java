package org.polyfrost.polytime.mixin;

//? if < 1.21.11 {
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
//? if 1.21.1
//import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.polyfrost.polytime.client.PolyTimeClient;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

//? if 1.21.1
//import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class Mixin_ModifyCelestialGradient extends Level {
    protected Mixin_ModifyCelestialGradient(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, /*? if 1.21.1 {*/ /*Supplier<ProfilerFiller> profiler, *//*?}*/ boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, /*? if 1.21.1 {*/ /*profiler, *//*?}*/ isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
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
//?} else {
/*import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SharedConstants.class)
public class Mixin_ModifyCelestialGradient {}
*///?}
