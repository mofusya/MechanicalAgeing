package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.function.Function;

public class SlotList extends ArrayList<SlotProperties> {

    public SlotList create(int x, int y, Function<ItemStack, Boolean> itemValidFunc) {
        this.add(new SlotProperties(x, y, itemValidFunc));
        return this;
    }
}
