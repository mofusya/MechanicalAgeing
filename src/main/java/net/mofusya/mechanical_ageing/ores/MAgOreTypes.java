package net.mofusya.mechanical_ageing.ores;

import net.kairayk.yklibrary.util.VectorColor;
import net.mofusya.mechanical_ageing.C;

//todo: Make a data generation system for this.(Recipes, Models, BlockStates, etc)
public class MAgOreTypes {
    public static final OreTypeRegistry REGISTRY = new OreTypeRegistry();

    public static final OreTypeRegister R = new OreTypeRegister(C.MOD_ID, REGISTRY::register);

    public static final OreType IRON = R.register("iron", VectorColor.of(0.5f, 0.5f, 0.5f, 1f), new OreType.Builder()
            .hasOres()
            .hasBlocks()
            .hasRaws()
            .hasIngots()
            .hasProcessingItems()
    );
}
