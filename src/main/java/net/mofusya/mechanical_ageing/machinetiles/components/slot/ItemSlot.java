package net.mofusya.mechanical_ageing.machinetiles.components.slot;

import net.kairayk.yklibrary.util.scrpos.ScrPosI;
import net.minecraft.world.item.ItemStack;
import net.mofusya.ornatelib.util.annotation.EverythingAreNonNullByDefault;

import java.util.function.Predicate;

@EverythingAreNonNullByDefault
public record ItemSlot(ScrPosI pos, Predicate<ItemStack> isValidFunc, SlotType type) {

    public static ItemSlot of(ScrPosI pos) {
        return of(pos, SlotType.NORMAL);
    }

    public static ItemSlot of(ScrPosI pos, SlotType type) {
        return of(pos, itemStack -> true, type);
    }

    public static ItemSlot of(ScrPosI pos, Predicate<ItemStack> inValidFunc, SlotType type){
        return new ItemSlot(pos, inValidFunc, type);
    }
}
