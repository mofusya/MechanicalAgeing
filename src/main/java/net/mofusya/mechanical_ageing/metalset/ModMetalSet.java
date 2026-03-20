package net.mofusya.mechanical_ageing.metalset;

import net.mofusya.mechanical_ageing.MechanicalAgeing;

public class ModMetalSet {
    public static final MetalSetRegister METAL_SET = MetalSetRegister.create(MechanicalAgeing.MOD_ID);

    public static final MetalSet IRON = METAL_SET.register("iron", new MetalSet.Builder(7.874, 150, 1811, 3134).magnetic());
    public static final MetalSet GOLD = METAL_SET.register("gold", new MetalSet.Builder(19.25, 30, 1337, 3129).color(0xFFFF60));
    public static final MetalSet COPPER = METAL_SET.register("copper", new MetalSet.Builder(8.96, 86, 1358, 2835).color(0xD49C3C));
    public static final MetalSet NICKEL = METAL_SET.register("nickel", new MetalSet.Builder(8.90, 175, 1728, 3186).color(0xFFFFB4).magnetic());
    public static final MetalSet ALUMINUM = METAL_SET.register("aluminum", new MetalSet.Builder(2.70, 9, 933, 2743).color(0xBDBDBD).oreColor(0xCC7460));
    public static final MetalSet SILVER = METAL_SET.register("silver", new MetalSet.Builder(10.49, 49, 1235, 2435).color(0xE3E0FF));
    public static final MetalSet LEAD = METAL_SET.register("lead", new MetalSet.Builder(11.34, 11, 600, 2022).color(0x7C8BB2));
    public static final MetalSet UNOBTAINIUM = METAL_SET.register("unobtainium", new MetalSet.Builder(5.83, 0.01, Integer.MAX_VALUE, Integer.MAX_VALUE).color(0xDD8FFF));
    public static final MetalSet MITHRIL = METAL_SET.register("mithril", new MetalSet.Builder(10.25, 1570, 2223, 3493).color(0xEED5FF));

    //検索用めも
    /*
    純 の変形するまでに必要な力は何megapascal？
    What is the density of xxx in g/cm³
    What is the melting point of xxx in kelvin
    What is the boiling point of xxx in kelvin
    Is xxx magnetic?
    */
}