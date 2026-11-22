package org.polyfrost.polytime.mixin;

//? if = 1.8.9 {
/*import net.minecraft.world.level.Level;
import org.polyfrost.polytime.client.PolyTimeClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Level.class)
public abstract class Mixin_FreezeSkyTickDelta {
    @Shadow @Final public boolean isClient;

    @ModifyArg(method = "getTimeOfDay(F)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/Dimension;getTimeOfDay(JF)F"))
    private float polytime$freezeSkyTickDelta(float tickDelta) {
        return this.isClient && PolyTimeClient.getShouldOverride() ? 0.0F : tickDelta;
    }
}
*///?} else {
import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SharedConstants.class)
public class Mixin_FreezeSkyTickDelta {}
//?}
