package net.mofusya.mechanical_ageing.machinetiles;

import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.machinetiles.tile.DestructorTile;

public class ModMachines {
    public static final MachineRegister MACHINES = new MachineRegister(MechanicalAgeing.MOD_ID);

    static {
        MACHINES.register("destroyer", DestructorTile::new);
    }
}