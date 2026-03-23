package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public record SlotProperties(int x, int y, Function<ItemStack, Boolean> itemValidFunc) {
}