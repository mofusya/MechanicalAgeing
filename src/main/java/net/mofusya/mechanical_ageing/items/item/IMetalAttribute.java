package net.mofusya.mechanical_ageing.items.item;

import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public interface IMetalAttribute {
    double density(@Nullable ItemStack itemStack);

    int meltingPoint(@Nullable ItemStack itemStack);

    int boilingPoint(@Nullable ItemStack itemStack);

    void setDensity(@Nullable ItemStack itemStack, double density);

    void setMeltingPoint(@Nullable ItemStack itemStack, double meltingPoint);

    void setBoilingPoint(@Nullable ItemStack itemStack, double boilingPoint);
}
