package org.polyfrost.polytime.mixin;

//? if >= 1.21.11 {
import org.spongepowered.asm.mixin.injection.Inject;
//?} else {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
*///?}
//? if >= 1.21.11 {
import net.minecraft.client.Camera;
//~ if >= 26.1 'state.SkyRenderState' -> 'state.level.SkyRenderState'
import net.minecraft.client.renderer.state.level.SkyRenderState;
import net.minecraft.world.level.MoonPhase;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?}
import net.minecraft.client.multiplayer.ClientLevel;
//? if >= 1.21.10 {
import net.minecraft.client.renderer.SkyRenderer;
//?} else
//import net.minecraft.client.renderer.LevelRenderer;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >= 1.21.10 {
@Mixin(SkyRenderer.class)
//?} else {
/*@Mixin(LevelRenderer.class)
*///?}
public class Mixin_FixMoonPhases {
    //? if >= 1.21.11 {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void polytime$fixMoonPhase(ClientLevel level, float partialTicks, Camera camera, SkyRenderState state, CallbackInfo ci) {
        if (PolyTimeConfig.isEnabled() && PolyTimeConfig.getMoonPhaseOverride() >= 0) {
            state.moonPhase = MoonPhase.values()[PolyTimeConfig.getMoonPhaseOverride()];
        }
    }
    //?} else {
    /*@WrapOperation(method = /^? if 1.21.10 {^/ /^"extractRenderState" ^//^?} elif >= 1.21.4 {^/ "method_62215" /^?} else {^/ /^"renderSky" ^//^?}^/ , at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getMoonPhase()I"))
    private int polyweather$fixMoonPhase(ClientLevel instance, Operation<Integer> original) {
        if (PolyTimeConfig.isEnabled() && PolyTimeConfig.getMoonPhaseOverride() >= 0) {
            return PolyTimeConfig.getMoonPhaseOverride();
        }

        return original.call(instance);
    }
    *///?}
}
