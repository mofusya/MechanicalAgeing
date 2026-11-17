package net.mofusya.mechanical_ageing.blocks.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class FoilBlockItem extends BlockItem {
    public FoilBlockItem(Block block, Properties build) {
        super(block, build);
    }

    public boolean isFoil(ItemStack itemStack) {
        return true;
    }
}
