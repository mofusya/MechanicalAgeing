package net.mofusya.mechanical_ageing.ores;

import net.kairayk.yklibrary.util.VectorColor;
import net.mofusya.mechanical_ageing.C;

public class MAgOreTypes {
    public static final OreTypeRegistry REGISTRY = new OreTypeRegistry();

    public static final OreTypeRegister R = new OreTypeRegister(C.MOD_ID, REGISTRY::register);

    public static final OreType IRON = R.register("iron", VectorColor.WHITE, simpleMetalLikeBuilder());
    public static final OreType GOLD = R.register("gold", VectorColor.WHITE, simpleMetalLikeBuilder());

    public static OreType.Builder simpleMetalLikeBuilder(){
        return new OreType.Builder()
                .hasOres()
                .hasBlocks()
                .hasRaws()
                .hasIngots()
                .hasProcessingItems();
    }
}
