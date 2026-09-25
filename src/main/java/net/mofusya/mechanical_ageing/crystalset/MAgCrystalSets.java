package net.mofusya.mechanical_ageing.crystalset;

import net.mofusya.mechanical_ageing.C;

public class MAgCrystalSets {
    public static final CrystalSetRegister CRYSTALS = new CrystalSetRegister(C.MOD_ID);

    public static final CrystalSet QUARTZ = CRYSTALS.register("quartz", CrystalSet.builder(2.65, -1, 1986, 3220).color(0xF7F0E0));
}
