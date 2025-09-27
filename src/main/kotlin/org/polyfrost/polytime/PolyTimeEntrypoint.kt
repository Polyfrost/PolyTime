package org.polyfrost.polytime

//#if FABRIC
//$$ import net.fabricmc.api.ModInitializer
//$$ import net.fabricmc.api.ClientModInitializer
//$$ import net.fabricmc.api.DedicatedServerModInitializer
//#elseif FORGE
//#if MC >= 1.16.5
//$$ import net.minecraftforge.eventbus.api.IEventBus
//$$ import net.minecraftforge.fml.common.Mod
//$$ import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
//$$ import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
//$$ import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
//$$ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
//#else
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent

//#endif
//#elseif NEOFORGE
//$$ import net.neoforged.bus.api.IEventBus
//$$ import net.neoforged.fml.common.Mod
//$$ import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
//$$ import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
//$$ import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent
//#endif

import org.polyfrost.polytime.client.PolyTimeClient

//#if FORGE-LIKE
//#if MC >= 1.16.5
//$$ @Mod(PolyTimeConstants.ID)
//#else
@Mod(modid = PolyTimeConstants.ID, version = PolyTimeConstants.VERSION)
//#endif
//#endif

class PolyTimeEntrypoint
    //#if FABRIC
    //$$ : ClientModInitializer
    //#endif
{
    //#if FORGE && MC >= 1.16.5
    //$$ init {
    //$$     setupForgeEvents(FMLJavaModLoadingContext.get().getModEventBus())
    //$$ }
    //#elseif NEOFORGE
    //$$ constructor(modEventBus: IEventBus) {
    //$$     setupForgeEvents(modEventBus)
    //$$ }
    //#endif

    //#if FABRIC
    //$$ override
    //#elseif FORGE && MC <= 1.12.2
    @EventHandler
    //#endif
    fun onInitializeClient(
        //#if FORGE-LIKE
        //#if MC >= 1.16.5
        //$$ event: FMLClientSetupEvent
        //#else
        event: FMLInitializationEvent
        //#endif
        //#endif
    ) {
        //#if FORGE && MC <= 1.12.2
        //$$ if (!event
        //#if MC <= 1.8.9
        //$$ .side.isClient
        //#else
        //$$ .getSide().isClient()
        //#endif
        //$$ ) {
        //$$     return;
        //$$ }
        //#endif

        PolyTimeClient.initialize()
    }

    //#if FORGE-LIKE && MC >= 1.16.5
    //$$ private fun setupForgeEvents(modEventBus: IEventBus) {
    //$$     modEventBus.addListener(this::onInitializeClient)
    //$$ }
    //#endif
}
