package net.mofusya.mechanical_ageing.items.item;

import net.minecraft.world.item.Item;

public class BatteryCoreItem extends Item {
    private final int color;

    public BatteryCoreItem(Properties build, int color) {
        super(build);
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
