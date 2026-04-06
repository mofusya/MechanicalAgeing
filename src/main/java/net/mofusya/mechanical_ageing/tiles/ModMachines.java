package net.mofusya.mechanical_ageing.tiles;

import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.machinetiles.MachineObject;
import net.mofusya.mechanical_ageing.machinetiles.MachineRegister;
import net.mofusya.mechanical_ageing.tiles.tile.DestructorTile;

public class ModMachines {
    public static final MachineRegister MACHINES = new MachineRegister(MechanicalAgeing.MOD_ID);

    public static final MachineObject DESTRUCTOR = MACHINES.register("destructor",DestructorTile::new);
}