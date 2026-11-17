package net.mofusya.mechanical_ageing.tint;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.Block;

@FunctionalInterface
public interface BlockColorRegister {
    void register(BlockColor blockColor, Block... blocks);
}
