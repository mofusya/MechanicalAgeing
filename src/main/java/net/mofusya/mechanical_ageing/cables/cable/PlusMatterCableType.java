package net.mofusya.mechanical_ageing.cables.cable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.Capability;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.blocks.MAgBlocks;
import net.mofusya.mechanical_ageing.cables.ICableType;
import net.mofusya.mechanical_ageing.machinetiles.MAgCapabilities;
import net.mofusya.mechanical_ageing.machinetiles.matter.IMatterHandler;

public class PlusMatterCableType implements ICableType<IMatterHandler> {
    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(C.MOD_ID, C.PLUS_MATTER);
    }

    @Override
    public Capability<IMatterHandler> getCapability() {
        return MAgCapabilities.MATTER;
    }

    @Override
    public Block getBlock() {
        return MAgBlocks.PLUS_MATTER_CABLE.get();
    }
}
