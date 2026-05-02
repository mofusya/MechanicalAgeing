package net.mofusya.mechanical_ageing.tiles;

import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.machinetiles.MachineObject;
import net.mofusya.mechanical_ageing.machinetiles.MachineRegister;
import net.mofusya.mechanical_ageing.tiles.tile.BrickBurningChamberTile;
import net.mofusya.mechanical_ageing.tiles.tile.BrickSmeltingChamber;
import net.mofusya.mechanical_ageing.tiles.tile.DestructorTile;
import net.mofusya.mechanical_ageing.tiles.tile.TriDimCraftingTableTile;

public class MAgMachines {
    public static final MachineRegister MACHINES = new MachineRegister(MechanicalAgeing.MOD_ID);

    public static final MachineObject TRI_DIM_CRAFTING_TABLE = MACHINES.register("tri_dimensional_crafting_table", TriDimCraftingTableTile::new);
    public static final MachineObject DESTRUCTOR = MACHINES.register("destructor", DestructorTile::new);
    public static final MachineObject BRICK_BURNING_CHAMBER = MACHINES.register("brick_burning_chamber", BrickBurningChamberTile::new);
    public static final MachineObject BRICK_SMELTING_CHAMBER = MACHINES.register("brick_smelting_chamber", BrickSmeltingChamber::new);
}