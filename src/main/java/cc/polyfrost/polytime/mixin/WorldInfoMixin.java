package cc.polyfrost.polytime.mixin;

import cc.polyfrost.polytime.PolyTime;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldInfo.class)
public class WorldInfoMixin {

    @Shadow private long worldTime;

    @Overwrite
    public long getWorldTime() {
        if (PolyTime.INSTANCE.config.enabled) return PolyTime.timeToTicks();
        return this.worldTime;
    }
}
