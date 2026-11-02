package org.polyfrost.polytime.mixin;

//? if >=26.1 {
import net.minecraft.client.ClientClockManager;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.polyfrost.polytime.client.PolyTimeClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=26.3 {
@Mixin(ClientClockManager.ClientClockInstance.class)
//?} else {
/*@Mixin(ClientClockManager.class)
*///?}
public class Mixin_ClientClockManager {
    @ModifyReturnValue(method = /*? if >=26.3 {*/ "totalTicks" /*?} else {*/ /*"getTotalTicks" *//*?}*/, at = @At("RETURN"))
    private long polytime$overrideTicks(long ticks) {
        return PolyTimeClient.getShouldOverride() ? PolyTimeClient.adjustTicks(ticks) : ticks;
    }
}
//?} else {
/*import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SharedConstants.class)
public class Mixin_ClientClockManager {}
*///?}
