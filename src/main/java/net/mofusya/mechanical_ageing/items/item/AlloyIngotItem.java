package net.mofusya.mechanical_ageing.items.item;

import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.mofusya.ornatelib.item.NonStaticAttributeItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AlloyIngotItem extends NonStaticAttributeItem implements IMetalAttribute {
    public AlloyIngotItem(Properties build, @NotNull Builder builder) {
        super(build, builder
                .attribute("density", 7.874, true)
                .attribute("melting_point", 1811, true)
                .attribute("boiling_point", 3134, true)
                .strangeAttribute("blended_material", new ListTag())
        );
    }

    @Override
    public double density(@Nullable ItemStack itemStack) {
        if (itemStack == null) return -1;
        return this.getDoubleAttribute(itemStack, "density");
    }

    @Override
    public int meltingPoint(@Nullable ItemStack itemStack) {
        if (itemStack == null) return -1;
        return this.getIntegerAttribute(itemStack, "melting_point");
    }

    @Override
    public int boilingPoint(@Nullable ItemStack itemStack) {
        if (itemStack == null) return -1;
        return this.getIntegerAttribute(itemStack, "boiling_point");
    }

    @Override
    public void setDensity(@Nullable ItemStack itemStack, double density) {
        if (itemStack == null) return;
        this.setAttribute(itemStack, "density", density);
    }

    @Override
    public void setMeltingPoint(@Nullable ItemStack itemStack, double meltingPoint) {
        if (itemStack == null) return;
        this.setAttribute(itemStack, "melting_point", meltingPoint);
    }

    @Override
    public void setBoilingPoint(@Nullable ItemStack itemStack, double boilingPoint) {
        if (itemStack == null) return;
        this.setAttribute(itemStack, "boiling_point", boilingPoint);
    }
}