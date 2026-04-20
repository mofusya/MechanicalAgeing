package net.mofusya.mechanical_ageing.machinetiles.slot;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.function.Function;

public class SlotList extends ArrayList<SlotProperties> {
    public SlotList create(int x, int y, Function<ItemStack, Boolean> itemValidFunc, SlotType type) {
        this.add(new SlotProperties(x, y, itemValidFunc, type));
        return this;
    }

    public SlotList createVerLine(int x, int y, Function<ItemStack, Boolean> itemValidFunc, SlotType type, int count) {
        for (int i = 0; i < count; i++) {
            this.create(x + (i * 18), y, itemValidFunc, type);
        }
        return this;
    }

    public SlotList createHorLine(int x, int y, Function<ItemStack, Boolean> itemValidFunc, SlotType type, int count) {
        for (int i = 0; i < count; i++) {
            this.create(x, y + (i * 18), itemValidFunc, type);
        }
        return this;
    }

    public SlotList createCube(int x, int y, Function<ItemStack, Boolean> itemValidFunc, SlotType type, int size) {
        return this.createArea(x, y, itemValidFunc, type, size, size);
    }

    public SlotList createArea(int x, int y, Function<ItemStack, Boolean> itemValidFunc, SlotType type, int width, int height) {
        for (int i = 0; i < height; i++) {
            this.createVerLine(x, y + (i * 18), itemValidFunc, type, width);
        }
        return this;
    }
}
