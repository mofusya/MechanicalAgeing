package net.mofusya.mechanical_ageing.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public final class ItemStackUtil {
    public static boolean itemStackIs(ItemStack itemStack, MultiversalObject<Item> itemLike){
        return itemStack.is(itemLike.get()) || itemStack.is(itemLike.getMultiversal());
    }
}
