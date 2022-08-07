package cc.polyfrost.polytime.mixin;

import cc.polyfrost.polytime.PolyTime;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldInfo.class)
public class WorldInfoMixin {

    @Inject(method = "getWorldTime", at = @At("HEAD"), cancellable = true)
    private void getWorldTime(CallbackInfoReturnable<Long> cir) {
        if (PolyTime.INSTANCE.config.enabled) {
            cir.setReturnValue(PolyTime.timeToTicks());
        }
    }
}
