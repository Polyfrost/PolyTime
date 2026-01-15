package org.polyfrost.polytime.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
//? if >= 1.21.10
import net.minecraft.client.renderer.SkyRenderer;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.polyfrost.polytime.client.realtime.RealTimeHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >= 1.21.10 {
@Mixin(SkyRenderer.class)
//?} else {
/*@Mixin(LevelRenderer.class)
*///?}
public class Mixin_FixMoonPhases {
    @WrapOperation(method = /*? if >= 1.21.10 {*/ "extractRenderState" /*?} else if >= 1.21.4 {*/ /*"method_62215" *//*?} else {*/ /*"renderSky" *//*?}*/ , at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getMoonPhase()I"))
    private int polyweather$fixMoonPhase(ClientLevel instance, Operation<Integer> original) {
        if (PolyTimeConfig.isEnabled() && PolyTimeConfig.isIrlTime()) { // TODO: use irlLunarPhases instead?
            return RealTimeHandler.getCurrentLunarPhase();
        }

        return original.call(instance);
    }
}