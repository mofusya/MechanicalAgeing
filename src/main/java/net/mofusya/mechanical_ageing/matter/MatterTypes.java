package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.MechanicalAgeing;

public class MatterTypes {
    public static final MatterType WATER = MatterManager.create(
            new ResourceLocation(MechanicalAgeing.MOD_ID, "water"),
            new MatterType.Builder(0x4040FF).build());

    public static final MatterType ANTI_MATTER = MatterManager.create(
            new ResourceLocation(MechanicalAgeing.MOD_ID, "anti_matter"),
            new MatterType.Builder(0x000000).build());
}