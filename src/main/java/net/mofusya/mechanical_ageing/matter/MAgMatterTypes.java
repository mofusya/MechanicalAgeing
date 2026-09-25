package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.C;

public class MAgMatterTypes {
    public static final MatterRegister MATTERS = new MatterRegister();

    public static final MatterType CARBON = create("carbon", 0x3C3C3C);
    public static final MatterType HEAT = create("heat", 0xBA3D06, "K");
    public static final MatterType CARBON_DIOXIDE = create("carbon_dioxide", 0xB5B5B5);
    public static final MatterType WATER = create("water", 0x4040FF);
    public static final MatterType WATER_VAPOR = create("water_vapor", 0xE0E0E0);
    public static final MatterType ROTATION = create("rotation", 0x9E9380, "RP");
    public static final MatterType METHANE = create("methane", 0xA3BFEB);
    public static final MatterType METHANOL = create("methanol", 0xB3CFFB);
    public static final MatterType SILANE_COMPOUND = create("silane_compound", 0xC3DFFF);
    public static final MatterType SILANE_MIXTURE = create("silane_mixture", 0xD3FFFF);
    public static final MatterType SILOXANE = create("siloxane", 0x76CBFF);
    public static final MatterType LIQUID_SILICONE_RUBBER = create("liquid_silicone_rubber", 0xEFEFFF);

    /*Helpers*/
    private static MatterType create(String id, int color) {
        return MATTERS.create(new ResourceLocation(C.MOD_ID, id), new MatterType.Builder(color).build());
    }

    private static MatterType create(String id, int color, String suffix) {
        return MATTERS.create(new ResourceLocation(C.MOD_ID, id), new MatterType.Builder(color).suffix(suffix).build());
    }
}