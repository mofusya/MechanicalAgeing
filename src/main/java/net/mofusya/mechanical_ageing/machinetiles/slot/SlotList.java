package net.mofusya.mechanical_ageing.machinetiles.slot;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.function.Function;

public class SlotList extends ArrayList<SlotProperties> {
    public SlotList create(int x, int y, Function<ItemStack, Boolean> itemValidFunc, SlotType type) {
        this.add(new SlotProperties(x, y, itemValidFunc, type));
        return this;
    }
}
