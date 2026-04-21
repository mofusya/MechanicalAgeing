package net.mofusya.mechanical_ageing;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mofusya.mechanical_ageing.blocks.ModBlocks;
import net.mofusya.mechanical_ageing.items.ModItem;
import net.mofusya.mechanical_ageing.items.ModTabs;
import net.mofusya.mechanical_ageing.recipes.ModRecipes;
import net.mofusya.mechanical_ageing.tiles.ModMachines;
import net.mofusya.mechanical_ageing.metalset.MetalSet;
import net.mofusya.mechanical_ageing.metalset.ModMetalSet;
import org.slf4j.Logger;

import java.util.ArrayList;

@Mod(MechanicalAgeing.MOD_ID)
public class MechanicalAgeing {
    public static final String MOD_ID = "mechanical_ageing";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MechanicalAgeing() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItem.ITEMS.register(modEventBus);
        ModMetalSet.METAL_SET.register(modEventBus);
        ModTabs.TABS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModMachines.MACHINES.register(modEventBus);
        ModRecipes.SERIALIZERS.register(modEventBus);
        ModRecipes.TYPES.register(modEventBus);

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

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientSetup {

        @SubscribeEvent
        public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
            for (MetalSet metalSet : ModMetalSet.METAL_SET.getEntries()) {

                event.register((state, level, pos, tintIndex) -> {
                    if (tintIndex == 0) {
                        return metalSet.color();
                    }
                    return 0xFFFFFF;
                }, metalSet.compressedBlock());

                event.register((state, level, pos, tintIndex) -> {
                    if (tintIndex == 0) {
                        return metalSet.color();
                    }
                    return 0xFFFFFF;
                }, metalSet.block());

                event.register((state, level, pos, tintIndex) -> {
                    if (tintIndex == 0) {
                        if (metalSet.oreColor() == -404) {
                            return metalSet.color();
                        }
                        return metalSet.oreColor();
                    }
                    return 0xFFFFFF;
                }, metalSet.ore());

                event.register((state, level, pos, tintIndex) -> {
                    if (tintIndex == 0) {
                        if (metalSet.oreColor() == -404) {
                            return metalSet.color();
                        }
                        return metalSet.oreColor();
                    }
                    return 0xFFFFFF;
                }, metalSet.deepslateOre());
            }
        }

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            for (MetalSet metalSet : ModMetalSet.METAL_SET.getEntries()) {
                ArrayList<Item> items = new ArrayList<>();
                items.add(metalSet.ingot());
                items.add(metalSet.chunk());
                items.add(metalSet.pureDust());
                items.add(metalSet.dust());
                items.add(metalSet.dirtyDust());
                items.add(metalSet.particle());
                items.add(metalSet.nugget());
                items.add(metalSet.compressedBlock().asItem());
                items.add(metalSet.block().asItem());

                for (Item item : items) {
                    event.register((itemStack, tintIndex) -> {
                        if (tintIndex == 0) {
                            return metalSet.color();
                        }
                        return 0xFFFFFF;
                    }, item);
                }

                ArrayList<Item> oreItems = new ArrayList<>();
                oreItems.add(metalSet.raw());
                oreItems.add(metalSet.ore().asItem());
                oreItems.add(metalSet.deepslateOre().asItem());

                for (Item item : oreItems) {
                    event.register((itemStack, tintIndex) -> {
                        if (tintIndex == 0) {
                            if (metalSet.oreColor() == -404) {
                                return metalSet.color();
                            }
                            return metalSet.oreColor();
                        }
                        return 0xFFFFFF;
                    }, item);
                }
            }
        }
    }
}
