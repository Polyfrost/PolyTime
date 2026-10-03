package org.polyfrost.polytime.test

//? if > 1.8.9
import net.minecraft.SharedConstants
import net.minecraft.server.Bootstrap
//? if = 1.8.9 {
/*import net.fabricmc.loader.api.FabricLoader
import net.ornithemc.osl.entrypoints.api.ModInitializer
*///?}
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.spongepowered.asm.mixin.MixinEnvironment
import org.spongepowered.asm.mixin.MixinEnvironment.Option
import org.spongepowered.asm.mixin.transformer.IMixinTransformer

/**
 * Audits mixins for validity without launching a full Minecraft client
 * Inspired by [Skyblocker](https://github.com/SkyblockerMod/Skyblocker)
 */
class MixinTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun setupEnvironment() {
            //? if > 1.8.9 {
            SharedConstants.tryDetectVersion()
            //?} else {
            /*FabricLoader.getInstance().invokeEntrypoints(
                ModInitializer.ENTRYPOINT_KEY,
                ModInitializer::class.java,
                ModInitializer::init,
            )
            *///?}
            Bootstrap.bootStrap()
        }
    }

    @Test
    fun `mixins load successfully`() {
        val environment = MixinEnvironment.getCurrentEnvironment()
        Assertions.assertInstanceOf(
            IMixinTransformer::class.java,
            environment.activeTransformer,
        )
        // dev refmap remapping retries failed selectors without the descriptor so bad
        // selectors resolve by name alone, disable it to match production strictness
        environment.setOption(Option.REFMAP_REMAP, false)
        environment.audit()
    }
}
