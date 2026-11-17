package net.mofusya.mechanical_ageing.tint;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;

@FunctionalInterface
public interface ItemColorRegister {
    void register(ItemColor itemColor, ItemLike... items);
}
