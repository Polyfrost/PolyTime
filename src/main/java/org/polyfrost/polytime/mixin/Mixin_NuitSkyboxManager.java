package org.polyfrost.polytime.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.multiplayer.ClientLevel;
import org.polyfrost.polytime.client.PolyTimeClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "me.flashyreese.mods.nuit.SkyboxManager", remap = false)
public class Mixin_NuitSkyboxManager {
    @WrapMethod(method = "tick", require = 0)
    private void polytime$scopeTick(ClientLevel level, Operation<Void> original) {
        PolyTimeClient.overriding(original, level);
    }
}
