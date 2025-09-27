package org.polyfrost.polytime.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.render.WorldRenderer;
import org.polyfrost.polytime.client.PolyTimeConfig;
import org.polyfrost.polytime.client.realtime.RealTimeHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public class Mixin_FixMoonPhases {
    @Redirect(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getMoonPhase()I"))
    private int polyweather$fixMoonPhase(ClientWorld level) {
        if (PolyTimeConfig.isEnabled() && PolyTimeConfig.isIrlTime()) {
            return RealTimeHandler.getCurrentLunarPhase();
        }

        return level.getMoonPhase();
    }
}
