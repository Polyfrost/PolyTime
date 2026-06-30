package org.polyfrost.polytime.mixin;

//? if 1.21.11 {
/*import net.minecraft.client.multiplayer.ClientLevel;
import org.polyfrost.polytime.client.PolyTimeClient;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.ClientLevelData.class)
public class Mixin_ClientLevelData {
    @Inject(method = "getDayTime", at = @At("HEAD"), cancellable = true)
    private void polytime$overrideDayTime(CallbackInfoReturnable<Long> cir) {
        if (PolyTimeConfig.isEnabled()) {
            cir.setReturnValue(PolyTimeClient.getCurrentTime());
        }
    }
}
*///?} else
public class Mixin_ClientLevelData {}
