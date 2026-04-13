package net.mofusya.mechanical_ageing.tiles;

import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.machinetiles.MachineObject;
import net.mofusya.mechanical_ageing.machinetiles.MachineRegister;
import net.mofusya.mechanical_ageing.tiles.tile.DestructorTile;
import net.mofusya.mechanical_ageing.tiles.tile.TriDimCraftingTable;

public class ModMachines {
    public static final MachineRegister MACHINES = new MachineRegister(MechanicalAgeing.MOD_ID);

    public static final MachineObject TRI_DIM_CRAFTING_TABLE = MACHINES.register("tri_dimensional_crafting_table", TriDimCraftingTable::new);
    public static final MachineObject DESTRUCTOR = MACHINES.register("destructor", DestructorTile::new);
}