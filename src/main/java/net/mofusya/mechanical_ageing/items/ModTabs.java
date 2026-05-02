package net.mofusya.mechanical_ageing.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.blocks.ModBlocks;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;
import net.mofusya.mechanical_ageing.metalset.ModMetalSet;

import java.util.ArrayList;
import java.util.List;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MechanicalAgeing.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MATERIAL = TABS.register("material", () -> CreativeModeTab.builder()
            .title(Component.translatable("tab." + MechanicalAgeing.MOD_ID + ".material"))
            .icon(() -> new ItemStack(ModMetalSet.IRON.ingot()))
            .displayItems((parameters, output) -> {
                List<ItemLike> items = new ArrayList<>();
                items.addAll(ModBlocks.BLOCKS.getItems(0).stream().map(RegistryObject::get).toList());
                items.addAll(ModMetalSet.METAL_SET.getAllItemLikes());

                for (ItemLike item : items) {
                    output.accept(item);
                }
            })
            .build());

    public static final RegistryObject<CreativeModeTab> MACHINES = TABS.register("machines", () -> CreativeModeTab.builder()
            .title(Component.translatable("tab." + MechanicalAgeing.MOD_ID + ".machines"))
            .icon(() -> new ItemStack(MAgMachines.DESTRUCTOR.block()))
            .displayItems((parameters, output) -> {
                List<RegistryObject<Block>> blocks = new ArrayList<>();
                blocks.addAll(MAgMachines.MACHINES.getBlockEntries());

                for (RegistryObject<Block> block : blocks) {
                    output.accept(block.get());
                }
            })
            .build());
}
