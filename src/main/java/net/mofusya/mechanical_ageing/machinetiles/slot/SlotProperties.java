package net.mofusya.mechanical_ageing.machinetiles.slot;

import net.minecraft.world.item.ItemStack;
import org.stringtemplate.v4.ST;

import java.util.function.Function;

public record SlotProperties(int x, int y, Function<ItemStack, Boolean> itemValidFunc, SlotType type) {
}