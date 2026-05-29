package net.mofusya.mechanical_ageing.alloyset;

import net.mofusya.mechanical_ageing.MAg;

public class MAgAlloySets {
    public static final AlloySetRegister ALLOYS = new AlloySetRegister(MAg.MOD_ID);

    public static final AlloySet STEEL = ALLOYS.register("steel", AlloySet.builder(7.85, 350, 3350, 4250).color(0x999999).magnetic());
}
