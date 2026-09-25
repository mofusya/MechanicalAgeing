package net.mofusya.mechanical_ageing.tiles;

import net.minecraft.network.chat.Component;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.machinetiles.MachineObject;
import net.mofusya.mechanical_ageing.machinetiles.MachineRegister;
import net.mofusya.mechanical_ageing.tiles.tile.HeatingChamberTile;
import net.mofusya.mechanical_ageing.tiles.tile.*;
import net.mofusya.ornatelib.lang.UnLong;

public class MAgMachines {
    public static final MachineRegister MACHINES = new MachineRegister(MAg.MOD_ID);

    public static final MachineObject TRI_DIM_CRAFTING_TABLE = MACHINES.register("tri_dimensional_crafting_table", TriDimCraftingTableTile::new);
    public static final MachineObject DESTRUCTOR = MACHINES.register("destructor", DestructorTile::new);

    public static final MachineObject BRICK_BURNING_CHAMBER = MACHINES.register("brick_burning_chamber", BrickBurningChamberTile::new);
    public static final MachineObject BRICK_SMELTING_CHAMBER = MACHINES.register("brick_smelting_chamber", BrickSmeltingChamberTile::new);
    public static final MachineObject BRICK_HEAT_COMBINING_CHAMBER = MACHINES.register("brick_heat_combining_chamber", BrickHeatCombingChamberTile::new);

    public static final MachineObject STEEL_BURNING_CHAMBER = MACHINES.register("steel_burning_chamber",
            id -> new BurningChamberTile(id, Component.translatable("block.mechanical_ageing.steel_burning_chamber.machine_name"),
                    UnLong.billion(), UnLong.million().multi(10), UnLong.zero(),
                    new UnLong(3350), UnLong.zero(), new UnLong(425), 1.2));
    public static final MachineObject STEEL_SMELTING_CHAMBER = MACHINES.register("steel_smelting_chamber",
            id -> new SmeltingChamberTile(id, Component.translatable("block.mechanical_ageing.steel_smelting_chamber.machine_name"),
                    new UnLong(3350), new UnLong(425), UnLong.zero(), 1.2));
    public static final MachineObject STEEL_HEAT_COMBINING_CHAMBER = MACHINES.register("steel_heat_combining_chamber",
            id -> new HeatCombingChamberTile(id, Component.translatable("block.mechanical_ageing.steel_heat_combining_chamber.machine_name"),
                    new UnLong(3350), new UnLong(425), UnLong.zero(), 1.2));
    public static final MachineObject STEEL_HEATING_CHAMBER = MACHINES.register("steel_heating_chamber",
            id -> new HeatingChamberTile(id, Component.translatable("block.mechanical_ageing.steel_heating_chamber.machine_name"),
                    UnLong.billion(), UnLong.million().multi(10), UnLong.zero(),
                    new UnLong(3350), new UnLong(425), UnLong.zero(),
                    UnLong.billion(), UnLong.zero(), UnLong.million().multi(10)));
    public static final MachineObject IMPULSE_TURBINE = MACHINES.register("impulse_turbine",
            id -> new ImpulseTurbineChamber(id,
                    UnLong.billion(), UnLong.billion(), UnLong.zero(),
                    new UnLong(1048576), UnLong.zero(), new UnLong(1048576)));
    public static final MachineObject BASIC_WATTER_PUMP = MACHINES.register("basic_watter_pump", BasicMatterPumpTile::new);
    public static final MachineObject BASIC_ROTATION_GENERATOR = MACHINES.register("basic_rotation_generator", RotationGeneratorTile::new);
    public static final MachineObject MANUAL_BIO_CONCENTRATOR = MACHINES.register("manual_bio_concentrator", ManualBioConcentratorTile::new);
    public static final MachineObject MANUAL_MIXING_CHAMBER = MACHINES.register("manual_mixing_chamber", ManualMixingChamberTile::new);
    public static final MachineObject WATER_COOLING_CHAMBER = MACHINES.register("water_cooling_chamber", WaterCoolingChamberTile::new);

    public static final MachineObject MULTIVERSO_MATTER_CELL = MACHINES.register("multiverso_matter_cell", MultiversoMatterCellTile::new);
}