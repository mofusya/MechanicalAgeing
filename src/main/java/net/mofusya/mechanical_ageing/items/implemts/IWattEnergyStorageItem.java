package net.mofusya.mechanical_ageing.items.implemts;

import net.minecraft.world.item.ItemStack;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import org.jetbrains.annotations.Nullable;

@MethodsReturnNonNullByDefault
public interface IWattEnergyStorageItem {
    SeptiLong receive(ItemStack itemStack, SeptiLong maxReceive, boolean simulate);

    SeptiLong extract(ItemStack itemStack, SeptiLong maxExtract, boolean simulate);

    SeptiLong getStored(ItemStack itemStack);

    SeptiLong getCapacity(ItemStack itemStack);

    SeptiLong setStored(ItemStack itemStack);

    @Nullable
    default SeptiLong getMaxReceive(ItemStack itemStack){
        return null;
    }

    default boolean canReceive(ItemStack itemStack){
        SeptiLong maxReceive = this.getMaxReceive(itemStack);
        return maxReceive == null || maxReceive.isGreaterThan(0);
    }

    @Nullable
    default SeptiLong getMaxExtract(ItemStack itemStack){
        return null;
    }

    default boolean canExtract(ItemStack itemStack){
        SeptiLong maxExtract = this.getMaxExtract(itemStack);
        return maxExtract == null || maxExtract.isGreaterThan(0);
    }
}
