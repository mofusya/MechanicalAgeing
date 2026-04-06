package net.mofusya.mechanical_ageing.machinetiles.slot;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.function.Function;

public class SlotList extends ArrayList<SlotProperties> {

    public SlotList create(int x, int y, Function<ItemStack, Boolean> itemValidFunc) {
        return this.create(x, y, itemValidFunc, false);
    }

    public SlotList create(int x, int y, Function<ItemStack, Boolean> itemValidFunc, boolean system) {
        this.add(new SlotProperties(x, y, itemValidFunc, system));
        return this;
    }
}
