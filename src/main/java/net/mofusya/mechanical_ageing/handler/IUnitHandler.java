package net.mofusya.mechanical_ageing.handler;

import net.kairayk.yklibrary.util.scrpos.IScrPos;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public interface IUnitHandler<T> extends INBTSerializable<CompoundTag> {
    int getSlotCount();
    T getStored(int slot);
    void setStored(T amount, int slot);
    T getCapacity(int slot);
    T getSpace(int slot);
    T getMaxReceive(int slot);
    T getMaxExtract(int slot);
    boolean canReceive(int slot);
    boolean canExtract(int slot);
    T receive(T amount, boolean simulated, boolean forceLightReceive, int slot);
    T extract(T amount, boolean simulated, boolean forceLightExtract, int slot);
    void render(GuiGraphics guiGraphics, IScrPos<Integer> pos, int slot);
}
