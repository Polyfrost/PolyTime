package org.polyfrost.polytime.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.polyfrost.polytime.client.realtime.RealTimeHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public class Mixin_FixMoonPhases {
    @Redirect(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getMoonPhase()I"))
    private int polyweather$fixMoonPhase(ClientLevel level) {
        if (PolyTimeConfig.isEnabled() && PolyTimeConfig.isIrlTime()) {
            return RealTimeHandler.getCurrentLunarPhase();
        }

        return level.getMoonPhase();
    }
}
