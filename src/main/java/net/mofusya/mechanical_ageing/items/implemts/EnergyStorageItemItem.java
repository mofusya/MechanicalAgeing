package net.mofusya.mechanical_ageing.items.implemts;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.mofusya.ornatelib.lang.SeptiLong;

public abstract class EnergyStorageItemItem extends Item implements IWattEnergyStorageItem {
    public EnergyStorageItemItem(Properties build) {
        super(build);
    }

    @Override
    public SeptiLong receive(ItemStack itemStack, SeptiLong maxReceive, boolean simulate) {
        return null;
    }

    @Override
    public SeptiLong extract(ItemStack itemStack, SeptiLong maxExtract, boolean simulate) {
        return null;
    }

    @Override
    public SeptiLong getStored(ItemStack itemStack) {
        return null;
    }

    @Override
    public SeptiLong setStored(ItemStack itemStack) {
        return null;
    }
}
