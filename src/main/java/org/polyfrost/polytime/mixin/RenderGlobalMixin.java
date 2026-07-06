package org.polyfrost.polytime.mixin;

import net.minecraft.client.renderer.RenderGlobal;
import org.polyfrost.polytime.config.ModConfig;
import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@SideOnly(Side.CLIENT)
@Mixin(RenderGlobal.class)
public class RenderGlobalMixin {
    @ModifyVariable(method = "renderSky(FI)V", at = @At(value = "HEAD"), ordinal = 0, argsOnly = true)
    public float injectStaticSky(float value) {
        if (!ModConfig.INSTANCE.enabled) return value;
        return 1f;
    }
}
