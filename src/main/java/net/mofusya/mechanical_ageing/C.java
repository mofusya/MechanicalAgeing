package net.mofusya.mechanical_ageing;

import net.minecraft.resources.ResourceLocation;

/**
 * The final class for static storing all constants for this mod.
 */
public final class C {
    public static final String MOD_ID = MAg.MOD_ID;
    public static final String STORED = "stored";
    public static final String TYPE = "type";
    public static final String AMOUNT = "amount";
    public static final String TAGS = "tags";
    public static final String ORETYPE_DIR = "oretype/";
    public static final String CAPACITY = "capacity";
    public static final String ITEM_STACK_HANDLER = "itemStackHandler";
    public static final String MATTER_STACK_HANDLER = "matterStackHandler";
    public static final String KELVIN_HANDLER = "kelvinHandler";
    public static final String RALTS_HANDLER = "raltsHandler";
    public static final String VAARR_HANDLER = "vaarrHandler";
    public static final String TSEALV_HANDLER = "tsealvHandler";
    public static final String COLOR = "color";

    public static ResourceLocation modLoc(String path){
        return new ResourceLocation(C.MOD_ID, path);
    }
}
