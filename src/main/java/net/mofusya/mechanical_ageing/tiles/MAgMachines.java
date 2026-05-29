package net.mofusya.mechanical_ageing.tiles;

import net.minecraft.network.chat.Component;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.machinetiles.MachineObject;
import net.mofusya.mechanical_ageing.machinetiles.MachineRegister;
import net.mofusya.mechanical_ageing.tiles.tile.HeatingChamberTile;
import net.mofusya.mechanical_ageing.tiles.tile.*;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;

public class MAgMachines {
    public static final MachineRegister MACHINES = new MachineRegister(MAg.MOD_ID);

    public static final MachineObject TRI_DIM_CRAFTING_TABLE = MACHINES.register("tri_dimensional_crafting_table", TriDimCraftingTableTile::new);
    public static final MachineObject DESTRUCTOR = MACHINES.register("destructor", DestructorTile::new);

    public static final MachineObject BRICK_BURNING_CHAMBER = MACHINES.register("brick_burning_chamber", BrickBurningChamberTile::new);
    public static final MachineObject BRICK_SMELTING_CHAMBER = MACHINES.register("brick_smelting_chamber", BrickSmeltingChamberTile::new);
    public static final MachineObject BRICK_HEAT_COMBINING_CHAMBER = MACHINES.register("brick_heat_combining_chamber", BrickHeatCombingChamberTile::new);

    public static final MachineObject STEEL_BURNING_CHAMBER = MACHINES.register("steel_burning_chamber",
            id -> new BurningChamberTile(id, Component.translatable("block.mechanical_ageing.steel_burning_chamber.machine_name"),
                    SeptiLongValue.BILLION.get(), SeptiLongValue.MILLION.get().multiply(10), SeptiLongValue.ZERO.get(),
                    new SeptiLong(3350), new SeptiLong(), new SeptiLong(425), 1.2));
    public static final MachineObject STEEL_SMELTING_CHAMBER = MACHINES.register("steel_smelting_chamber",
            id -> new SmeltingChamberTile(id, Component.translatable("block.mechanical_ageing.steel_smelting_chamber.machine_name"),
                    new SeptiLong(3350), new SeptiLong(425), new SeptiLong(), 1.2));
    public static final MachineObject STEEL_HEAT_COMBINING_CHAMBER = MACHINES.register("steel_heat_combining_chamber",
            id -> new HeatCombingChamberTile(id, Component.translatable("block.mechanical_ageing.steel_heat_combining_chamber.machine_name"),
                    new SeptiLong(3350), new SeptiLong(425), new SeptiLong(), 1.2));
    public static final MachineObject STEEL_HEATING_CHAMBER = MACHINES.register("steel_heating_chamber",
            id -> new HeatingChamberTile(id, Component.translatable("block.mechanical_ageing.steel_heating_chamber.machine_name"),
                    SeptiLongValue.BILLION.get(), SeptiLongValue.MILLION.get().multiply(10), SeptiLongValue.ZERO.get(),
                    new SeptiLong(3350), new SeptiLong(425), new SeptiLong(),
                    SeptiLongValue.BILLION.get(), SeptiLongValue.ZERO.get(), SeptiLongValue.MILLION.get().multiply(10)));
    public static final MachineObject IMPULSE_TURBINE = MACHINES.register("impulse_turbine",
            id -> new ImpulseTurbineChamber(id,
                    SeptiLongValue.BILLION.get(), SeptiLongValue.BILLION.get(), SeptiLongValue.ZERO.get(),
                    new SeptiLong(1048576), new SeptiLong(), new SeptiLong(1048576)));
}