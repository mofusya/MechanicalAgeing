package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.MAg;

public class MAgMatterTypes {
    public static final MatterType WATER = MatterManager.create(
            new ResourceLocation(MAg.MOD_ID, "water"),
            new MatterType.Builder(0x4040FF).build());

    public static final MatterType WATER_VAPOR = MatterManager.create(
            new ResourceLocation(MAg.MOD_ID, "water_vapor"),
            new MatterType.Builder(0xE0E0E0).build());

    public static final MatterType FUEL = MatterManager.create(
            new ResourceLocation(MAg.MOD_ID, "fuel"),
            new MatterType.Builder(0xF0DBC0).build());

    public static final MatterType HEAT = MatterManager.create(
            new ResourceLocation(MAg.MOD_ID, "heat"),
            new MatterType.Builder(0xBA3D06).suffix("K").build());

    public static final MatterType ROTATION = MatterManager.create(
            new ResourceLocation(MAg.MOD_ID, "rotation"),
            new MatterType.Builder(0x9E9380).suffix("RP").build());
}