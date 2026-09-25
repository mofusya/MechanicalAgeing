package net.mofusya.mechanical_ageing.data.blockstate;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.alloyset.MAgAlloySets;
import net.mofusya.mechanical_ageing.machinetiles.MachineObject;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
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
                .background("block/block", false)
                .backgroundColor(MAgMetalSets.OSMIUM.color())
                .baseTexture("minecraft", "block/fire_0", false)
                .topBaseTexture("block/reinforced_bricks", false)
                .bottomBaseTexture("block/reinforced_bricks", false)
                .frameColor(0x8D6E63)
                .upperCrystalColor(0x4E342E)
                .lowerCrystalColor(0x4E342E)
                .sideColor(MAgMetalSets.OSMIUM.color())
        );

        builderList.add(builder(MAgMachines.BRICK_SMELTING_CHAMBER)
                .baseTexture("minecraft", "block/lava_still", false)
                .bottomBaseTexture("block/reinforced_bricks", false)
                .frameColor(0x8D6E63)
                .upperCrystalColor(MAgMetalSets.OSMIUM.color())
                .sideColor(0x4E342E)
        );

        builderList.add(builder(MAgMachines.BRICK_HEAT_COMBINING_CHAMBER)
                .baseTexture("minecraft", "block/lava_still", false)
                .topBaseTexture("block/reinforced_bricks", false)
                .bottomBaseTexture("block/reinforced_bricks", false)
                .frameColor(0x8D6E63)
                .upperCrystalColor(MAgMetalSets.TITANIUM.color())
                .sideColor(0x4E342E)
        );

        builderList.add(builder(MAgMachines.STEEL_BURNING_CHAMBER)
                .background("block/compressed_block", false)
                .backgroundColor(MAgAlloySets.STEEL.color())
                .baseTexture("minecraft", "block/fire_0", false)
                .topBaseTexture((ResourceLocation) null, false)
                .bottomBaseTexture((ResourceLocation) null, false)
                .frameColor(MAgAlloySets.STEEL.color())
                .upperCrystalColor(MAgAlloySets.STEEL.color())
                .lowerCrystalColor(MAgAlloySets.STEEL.color())
                .sideColor(MAgMetalSets.TUNGSTEN.color())
        );

        builderList.add(builder(MAgMachines.STEEL_SMELTING_CHAMBER)
                .background("block/compressed_block", false)
                .backgroundColor(MAgAlloySets.STEEL.color())
                .baseTexture("minecraft", "block/lava_still", false)
                .bottomBaseTexture((ResourceLocation) null, false)
                .frameColor(MAgAlloySets.STEEL.color())
                .upperCrystalColor(MAgMetalSets.TUNGSTEN.color())
                .sideColor(MAgAlloySets.STEEL.color())
        );

        builderList.add(builder(MAgMachines.STEEL_HEAT_COMBINING_CHAMBER)
                .background("block/compressed_block", false)
                .backgroundColor(MAgAlloySets.STEEL.color())
                .baseTexture("minecraft", "block/lava_still", false)
                .topBaseTexture((ResourceLocation) null, false)
                .bottomBaseTexture((ResourceLocation) null, false)
                .frameColor(MAgAlloySets.STEEL.color())
                .upperCrystalColor(MAgAlloySets.STEEL.color())
                .sideColor(MAgMetalSets.TUNGSTEN.color())
        );

        builderList.add(builder(MAgMachines.STEEL_HEATING_CHAMBER)
                .background("block/compressed_block", false)
                .backgroundColor(MAgAlloySets.STEEL.color())
                .frameColor(MAgAlloySets.STEEL.color())
                .upperCrystalColor(MAgAlloySets.STEEL.color())
                .lowerCrystalColor(MAgAlloySets.STEEL.color())
                .sideColor(MAgMetalSets.TUNGSTEN.color())
        );

        builderList.add(builder(MAgMachines.IMPULSE_TURBINE)
                .background("block/compressed_block", false)
                .backgroundColor(MAgAlloySets.STEEL.color() - 0x202020)
                .coverTexture("block/impulse_turbine_shaft", false)
                .bottomCoverTexture((ResourceLocation) null, false)
                .topCoverTexture((ResourceLocation) null, false)
                .frameColor(MAgAlloySets.STEEL.color())
                .sideColor(MAgMetalSets.TUNGSTEN.color())
        );

        builderList.add(builder(MAgMachines.BASIC_WATTER_PUMP)
                .background("minecraft", "block/water_still", false)
                .backgroundColor(MAgMatterTypes.WATER.getColor())
                .frameColor(MAgAlloySets.STEEL.color())
                .sideColor(MAgAlloySets.STEEL.color() - 0x202020)
                .upperCrystalColor(MAgMetalSets.ALUMINUM.color())
                .lowerCrystalColor(MAgMetalSets.ALUMINUM.color())
        );

        builderList.add(builder(MAgMachines.BASIC_ROTATION_GENERATOR)
                .background("block/compressed_block", false)
                .backgroundColor(MAgAlloySets.STEEL.color())
                .coverTexture("block/impulse_turbine_shaft", false)
                .bottomCoverTexture((ResourceLocation) null, false)
                .topCoverTexture((ResourceLocation) null, false)
                .frameColor(MAgAlloySets.STEEL.color())
                .upperCrystalColor(MAgMetalSets.ALUMINUM.color())
                .lowerCrystalColor(MAgAlloySets.STEEL.color() - 0x202020)
        );

        builderList.add(builder(MAgMachines.MANUAL_BIO_CONCENTRATOR)
                .background("minecraft", "block/composter_side", false)
                .frameColor(MAgAlloySets.STEEL.color())
                .sideColor(MAgMetalSets.COPPER.color())
                .upperCrystalColor(MAgMetalSets.COPPER.color())
                .lowerCrystalColor(MAgMetalSets.COPPER.color())
        );

        builderList.add(builder(MAgMachines.MANUAL_MIXING_CHAMBER)
                .background("minecraft", "block/oak_log", false)
                .upperCrystalColor(0x70E2E8)
                .sideColor(MAgAlloySets.STEEL.color())
        );

        builderList.add(builder(MAgMachines.WATER_COOLING_CHAMBER)
                .background("minecraft", "block/oak_log", false)
                .upperCrystalColor(MAgMetalSets.OSMIUM.color())
                .lowerCrystalColor(MAgMetalSets.OSMIUM.color())
                .sideColor(MAgAlloySets.STEEL.color())
        );

        builderList.add(builder(MAgMachines.MULTIVERSO_MATTER_CELL)
                .background("block/compressed_block", false)
                .backgroundColor(MAgMetalSets.UNOBTAINIUM.color())
                .frameColor(MAgMetalSets.UNOBTAINIUM.color())
                .sideColor(MAgMetalSets.UNOBTAINIUM.color())
                .upperCrystalColor(0x101010)
                .lowerCrystalColor(0x101010)
        );

        return builderList;
    }

    private static MachineBlockStateBuilder builder(MachineObject machine) {
        return new MachineBlockStateBuilder(machine);
    }
}
