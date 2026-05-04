package net.mofusya.mechanical_ageing.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.alloyset.MAgAlloySets;
import net.mofusya.mechanical_ageing.blocks.MAgBlocks;
import net.mofusya.mechanical_ageing.metalset.MAgMetalSets;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

import java.util.ArrayList;
import java.util.List;

public class MAgTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MAg.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MATERIAL = TABS.register("material", () -> CreativeModeTab.builder()
            .title(Component.translatable("tab." + MAg.MOD_ID + ".material"))
            .icon(() -> new ItemStack(MAgMetalSets.IRON.ingot()))
            .displayItems((parameters, output) -> {
                List<ItemLike> items = new ArrayList<>();
                items.addAll(MAgBlocks.BLOCKS.getItems(0).stream().map(RegistryObject::get).toList());
                items.addAll(MAgAlloySets.ALLOYS.getItems().stream().map(RegistryObject::get).toList());
                items.addAll(MAgMetalSets.METAL_SET.getAllItemLikes());

                for (ItemLike item : items) {
                    output.accept(item);
                }
            })
            .build());

    public static final RegistryObject<CreativeModeTab> MACHINES = TABS.register("machines", () -> CreativeModeTab.builder()
            .title(Component.translatable("tab." + MAg.MOD_ID + ".machines"))
            .icon(() -> new ItemStack(MAgMachines.DESTRUCTOR.block()))
            .displayItems((parameters, output) -> {
                List<ItemLike> items = new ArrayList<>();
                items.addAll(MAgItem.ITEMS.getItems(1).stream().map(RegistryObject::get).toList());
                items.addAll(MAgMachines.MACHINES.getBlockEntries().stream().map(RegistryObject::get).toList());

                for (ItemLike item : items) {
                    output.accept(item);
                }
            })
            .build());
}
