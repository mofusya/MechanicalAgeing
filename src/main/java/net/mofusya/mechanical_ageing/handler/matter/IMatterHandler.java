package net.mofusya.mechanical_ageing.handler.matter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.handler.IUnitHandler;
import net.mofusya.mechanical_ageing.matter.IMatterStack;
import net.mofusya.mechanical_ageing.matter.MatterStack;

@AutoRegisterCapability
public interface IMatterHandler extends IUnitHandler<IMatterStack> {

    /**
     * A method for converting this to a NBT tag.
     *
     * @return The NBT serialized {@code CompoundTag}.
     */
    @Override
    default CompoundTag serializeNBT(){
        CompoundTag tag = new CompoundTag();

        ListTag storedTags = new ListTag();
        for (int slot = 0; slot < this.getSlotCount(); slot++) {
            storedTags.add(this.getStored(slot).serializeNBT());
        }
        tag.put(C.STORED, storedTags);

        return tag;
    }

    /**
     * A method for reconstructing this from a {@code CompoundTag}/NBT.
     *
     * @param tag The NBT tag to reconstruct this from.
     */
    @Override
    default void deserializeNBT(CompoundTag tag){
        ListTag storedTags = tag.getList(C.STORED, Tag.TAG_COMPOUND);
        for (int slot = 0; slot < this.getSlotCount(); slot++) {
            this.setStored(MatterStack.deserializeNBT(storedTags.getCompound(slot)), slot);
        }
    }
}
