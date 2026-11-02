package org.polyfrost.polytime.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
//? if > 1.8.9 {
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
//?} else
//import net.minecraft.client.render.GameRenderer;
import org.polyfrost.polytime.client.PolyTimeClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameRenderer.class)
public class Mixin_GameRenderer {
    //? if >= 26.3 {
    @WrapMethod(method = "render")
    private void polytime$scopeRender(Operation<Void> original) {
        PolyTimeClient.overriding(original);
    }
    //?} elif > 1.8.9 {
    /*@WrapMethod(method = "render")
    private void polytime$scopeRender(DeltaTracker deltaTracker, boolean advanceGameTime, Operation<Void> original) {
        PolyTimeClient.overriding(original, deltaTracker, advanceGameTime);
    }
    *///?} else {
    /*@WrapMethod(method = "render(FJ)V")
    private void polytime$scopeRender(float tickDelta, long nanoTime, Operation<Void> original) {
        PolyTimeClient.overriding(original, tickDelta, nanoTime);
    }
    *///?}

    //? if >= 26.1 {
    @WrapMethod(method = "extract")
    private void polytime$scopeExtract(DeltaTracker deltaTracker, boolean advanceGameTime, Operation<Void> original) {
        PolyTimeClient.overriding(original, deltaTracker, advanceGameTime);
    }
    //?}
}
