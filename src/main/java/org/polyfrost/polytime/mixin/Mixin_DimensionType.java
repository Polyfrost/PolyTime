package org.polyfrost.polytime.mixin;

//? if 1.21.11 {
/*import net.minecraft.world.level.dimension.DimensionType;
import org.polyfrost.polytime.client.PolyTimeClient;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(DimensionType.class)
public class Mixin_DimensionType {
    @ModifyVariable(method = "timeOfDay(J)F", at = @At("HEAD"), argsOnly = true)
    private long polytime$overrideTimeOfDay(long dayTime) {
        if (PolyTimeConfig.isEnabled()) {
            return PolyTimeClient.getCurrentTime();
        }
        return dayTime;
    }
}
*///?}
