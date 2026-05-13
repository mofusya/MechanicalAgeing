package net.mofusya.mechanical_ageing.data.blockstate;

import net.mofusya.mechanical_ageing.machinetiles.MachineObject;
import net.mofusya.mechanical_ageing.metalset.MAgMetalSets;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MachineBlockStateHelper {

    public static final Iterable<MachineBlockStateBuilder> BUILDER_LIST = register();

    @NotNull
    public static List<MachineBlockStateBuilder> register() {
        ArrayList<MachineBlockStateBuilder> builderList = new ArrayList<>();

        builderList.add(builder(MAgMachines.TRI_DIM_CRAFTING_TABLE)
                .baseTexture("minecraft", "block/crafting_table", true)
                .frontBaseTexture("minecraft", "block/crafting_table_side", false)
                .bottomBaseTexture("minecraft", "block/oak_planks", false)
                .upperCrystalColor(MAgMetalSets.COPPER.color())
                .sideColor(MAgMetalSets.OSMIUM.color())
                .hasFacing(false)
        );

        builderList.add(builder(MAgMachines.BRICK_BURNING_CHAMBER)
                .baseTexture("minecraft", "block/lava_still", false)
                .topBaseTexture("block/reinforced_bricks", false)
                .bottomBaseTexture("block/reinforced_bricks", false)
                .frameColor(0x8D6E63)
                .upperCrystalColor(0x4E342E)
                .lowerCrystalColor(0x4E342E)
                .sideColor(MAgMetalSets.OSMIUM.color())
                .hasFacing(false)
        );

        return builderList;
    }

    private static MachineBlockStateBuilder builder(MachineObject machine) {
        return new MachineBlockStateBuilder(machine);
    }
}
