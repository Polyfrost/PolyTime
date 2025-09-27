package org.polyfrost.polytime.mixin;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.polyfrost.polytime.client.realtime.RealTimeHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderGlobal.class)
public class Mixin_FixMoonPhases {
    @Redirect(method = "renderSky(FI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getMoonPhase()I"))
    private int polyweather$fixMoonPhase(WorldClient world) {
        if (PolyTimeConfig.isEnabled() && PolyTimeConfig.isIrlTime()) {
            return RealTimeHandler.getCurrentLunarPhase();
        }

        return world.getMoonPhase();
    }
}
