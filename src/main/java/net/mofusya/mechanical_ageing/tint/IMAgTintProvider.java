package net.mofusya.mechanical_ageing.tint;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IMAgTintProvider {
    default void registerItemColorTint(ItemColorRegister R, ItemColors itemColors) {

    }

    default void registerBlockColorTint(BlockColorRegister R, BlockColors itemColors) {

    }
}
