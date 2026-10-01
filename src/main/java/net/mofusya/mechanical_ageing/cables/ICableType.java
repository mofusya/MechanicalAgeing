package net.mofusya.mechanical_ageing.cables;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.Capability;

//todo: Make a easy abstract call for this shit.
public interface ICableType<T> {
    ResourceLocation getIdentifier();
    Capability<T> getCapability();
    Block getBlock();
}