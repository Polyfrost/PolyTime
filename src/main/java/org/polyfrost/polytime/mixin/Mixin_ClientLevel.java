package org.polyfrost.polytime.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.multiplayer.ClientLevel;
import org.polyfrost.polytime.client.PolyTimeClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientLevel.class)
public class Mixin_ClientLevel {
    //~ if = 1.8.9 '"animateTick"' -> '"doRandomDisplayTicks"'
    @WrapMethod(method = "animateTick")
    private void polytime$scopeAnimateTick(int x, int y, int z, Operation<Void> original) {
        PolyTimeClient.overriding(original, x, y, z);
    }
}
