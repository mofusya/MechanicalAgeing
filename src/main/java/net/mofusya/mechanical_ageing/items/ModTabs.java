package net.mofusya.mechanical_ageing.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
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
                items.addAll(ModMetalSet.METAL_SET.getAllItemLikes());

                for (ItemLike item : items) {
                    output.accept(item);
                }
            })
            .build());

    /*
    public static final RegistryObject<CreativeModeTab> MACHINES = TABS.register("machines", () -> CreativeModeTab.builder()
            .title(Component.translatable("tab." + MechanicalAgeing.MOD_ID + ".machines"))
            .icon(() -> new ItemStack(ModMachines.DESTROYER.get()))
            .displayItems((parameters, output) -> {
                List<RegistryObject<Block>> blocks = new ArrayList<>();
                blocks.addAll(ModMachines.BLOCKS.getBlocks());

                for (RegistryObject<Block> block : blocks) {
                    output.accept(block.get());
                }
            })
            .build());
     */
}
