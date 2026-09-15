package org.polyfrost.polytime.mixin;

//? if >=26.1 {
import net.minecraft.client.ClientClockManager;
//? if <26.3 {
/*import net.minecraft.core.Holder;
import net.minecraft.world.clock.WorldClock;
*///?}

import org.polyfrost.polytime.client.PolyTimeClient;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if >=26.3 {
@Mixin(ClientClockManager.ClientClockInstance.class)
//?} else {
/*@Mixin(ClientClockManager.class)
*///?}
public class Mixin_ClientClockManager {
    @Inject(method = /*? if >=26.3 {*/ "totalTicks" /*?} else {*/ /*"getTotalTicks" *//*?}*/, at = @At("RETURN"), cancellable = true)
    private void polytime$overrideTicks(/*? if <26.3 {*/ /*Holder<WorldClock> definition, *//*?}*/ CallbackInfoReturnable<Long> cir) {
        if (PolyTimeConfig.isEnabled()) {
            long originalTicks = cir.getReturnValue();
            cir.setReturnValue(originalTicks - Math.floorMod(originalTicks, 24000L) + PolyTimeClient.getCurrentTime());
        }
    }
}
//?} else {
/*import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SharedConstants.class)
public class Mixin_ClientClockManager {}
*///?}
