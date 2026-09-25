package net.mofusya.mechanical_ageing.alloyset;

import net.mofusya.mechanical_ageing.MAg;

public class MAgAlloySets {
    public static final AlloySetRegister ALLOYS = new AlloySetRegister(MAg.MOD_ID);

    public static final AlloySet STEEL = ALLOYS.register("steel", AlloySet.builder(7.85, 350, 3350, 4250).color(0x999999).magnetic());
    public static final AlloySet MAGNETITE = ALLOYS.register("magnetite", AlloySet.builder(5.17, -1, 1870, 2896).color(0x606060).hasMagneticField());
    public static final AlloySet SILICON = ALLOYS.register("silicon", AlloySet.builder(33, -1, 1687, 2638).color(0xEEF6FA));
    public static final AlloySet METALLIC_SILICON = ALLOYS.register("metallic_silicon", AlloySet.builder(2.65, -1, 1687, 2638).color(0xFEFFFF));
}
