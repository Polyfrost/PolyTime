package org.polyfrost.polytime.mixin;

//? if < 26.1 {
/*import net.minecraft.client.multiplayer.ClientLevel;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.polyfrost.polytime.client.PolyTimeClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if = 1.8.9 {
/^import net.minecraft.client.Minecraft;
import net.minecraft.world.WorldData;
^///?}

//~ if = 1.8.9 'ClientLevel.ClientLevelData' -> 'WorldData'
@Mixin(ClientLevel.ClientLevelData.class)
public class Mixin_ClientLevelData {
    @ModifyReturnValue(method = "getDayTime", at = @At("RETURN"))
    private long polytime$overrideDayTime(long ticks) {
        //? if = 1.8.9
        //if (Minecraft.getInstance().world == null || Minecraft.getInstance().world.getData() != (Object) this) return ticks;
        return PolyTimeClient.getShouldOverride() ? PolyTimeClient.adjustTicks(ticks) : ticks;
    }
}
*///?} else {
import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SharedConstants.class)
public class Mixin_ClientLevelData {}
//?}
