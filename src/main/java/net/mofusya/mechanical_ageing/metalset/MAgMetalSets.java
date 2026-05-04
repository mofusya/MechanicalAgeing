package net.mofusya.mechanical_ageing.metalset;

import net.mofusya.mechanical_ageing.MAg;

public class MAgMetalSets {
    public static final MetalSetRegister METAL_SET = MetalSetRegister.create(MAg.MOD_ID);

    public static final MetalSet IRON = METAL_SET.register("iron", MetalSet.builder(7.874, 150, 1811, 3134).magnetic());
    public static final MetalSet GOLD = METAL_SET.register("gold", MetalSet.builder(19.25, 30, 1337, 3129).color(0xFFFF60));
    public static final MetalSet COPPER = METAL_SET.register("copper", MetalSet.builder(8.96, 86, 1358, 2835).color(0xD49C3C));
    public static final MetalSet NICKEL = METAL_SET.register("nickel", MetalSet.builder(8.90, 175, 1728, 3186).color(0xFFFFB4).magnetic());
    public static final MetalSet ALUMINUM = METAL_SET.register("aluminum", MetalSet.builder(2.70, 9, 933, 2743).color(0xBDBDBD).oreColor(0xCC7460));
    public static final MetalSet SILVER = METAL_SET.register("silver", MetalSet.builder(10.49, 49, 1235, 2435).color(0xE3E0FF));
    public static final MetalSet LEAD = METAL_SET.register("lead", MetalSet.builder(11.34, 11, 600, 2022).color(0x7C8BB2));
    public static final MetalSet UNOBTAINIUM = METAL_SET.register("unobtainium", MetalSet.builder(5.83, 0.01, Integer.MAX_VALUE, Integer.MAX_VALUE).color(0xDD8FFF));
    public static final MetalSet MITHRIL = METAL_SET.register("mithril", MetalSet.builder(10.25, 1570, 2223, 3493).color(0xEED5FF));
    public static final MetalSet TIN = METAL_SET.register("tin", MetalSet.builder(7.31, 13, 505, 2875).color(0xDEDEDE));
    public static final MetalSet OSMIUM = METAL_SET.register("osmium", MetalSet.builder(22.595, 2000, 3306, 5300).color(0x73ADBD));
    public static final MetalSet COBALT = METAL_SET.register("cobalt", MetalSet.builder(8.88, 305, 1768, 3143).color(0x6269D6).magnetic());
    public static final MetalSet PLATINUM = METAL_SET.register("platinum", MetalSet.builder(21.45, 48, 2041, 4098).color(0x869496));
    public static final MetalSet TUNGSTEN = METAL_SET.register("tungsten", MetalSet.builder(10.59, 326.5, 33695, 6203).color(0x7A7A7A));
    public static final MetalSet CHROMIUM = METAL_SET.register("chromium", MetalSet.builder(7.192, Double.MAX_VALUE, 2180, 2945).color(0x333B3D));
    public static final MetalSet TITANIUM = METAL_SET.register("titanium", MetalSet.builder(4.508, 326.5, 1941, 3560).color(0x4F4F4F));

    //検索用めも
    /*
    純 の変形するまでに必要な力は何megapascal？
    What is the density of xxx in g/cm³
    What is the melting point of xxx in kelvin
    What is the boiling point of xxx in kelvin
    s
    */
}