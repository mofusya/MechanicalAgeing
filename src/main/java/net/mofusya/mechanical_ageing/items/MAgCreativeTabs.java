package net.mofusya.mechanical_ageing.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.ores.MAgOreTypes;
import net.mofusya.ornatelib.registries.OrnateCreativeTabRegister;
import net.mofusya.ornatelib.util.ItemHelpers;

public class MAgCreativeTabs {
    public static final OrnateCreativeTabRegister R = new OrnateCreativeTabRegister(C.MOD_ID);

    public static final RegistryObject<CreativeModeTab> INGREDIENTS = R.register("ingredients", () -> new ItemStack(MAgOreTypes.IRON.getIngot().get()), (parameters, output) -> {
        output.acceptAll(ItemHelpers.itemRegistries2ItemStacks(MAgOreTypes.R.getItemR().getMainItems()));
        output.acceptAll(ItemHelpers.blockRegistries2ItemStacks(MAgOreTypes.R.getBlockR().getMainBlocks()));

        output.acceptAll(ItemHelpers.itemRegistries2ItemStacks(MAgOreTypes.R.getItemR().getItems(1)));
        output.acceptAll(ItemHelpers.blockRegistries2ItemStacks(MAgOreTypes.R.getBlockR().getBlocks(1)));
    });
}
