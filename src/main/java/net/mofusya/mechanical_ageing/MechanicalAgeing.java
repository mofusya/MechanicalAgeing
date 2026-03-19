package net.mofusya.mechanical_ageing;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mofusya.mechanical_ageing.items.ModItem;
import net.mofusya.mechanical_ageing.items.ModTabs;
import net.mofusya.mechanical_ageing.metalset.MetalSet;
import net.mofusya.mechanical_ageing.metalset.ModMetalSet;
import org.slf4j.Logger;

@Mod(MechanicalAgeing.MOD_ID)
public class MechanicalAgeing {
    public static final String MOD_ID = "mechanical_ageing";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MechanicalAgeing() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItem.ITEMS.register(modEventBus);
        ModMetalSet.METAL_SET.register(modEventBus);
        ModTabs.TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        for (MetalSet metalSet : ModMetalSet.METAL_SET.getEntries()) {
            ItemBlockRenderTypes.setRenderLayer(metalSet.ore(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(metalSet.deepslateOre(), RenderType.cutout());
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(EXAMPLE_BLOCK_ITEM);
    }
}
