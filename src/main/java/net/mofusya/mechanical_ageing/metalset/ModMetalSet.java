package net.mofusya.mechanical_ageing.metalset;

import net.mofusya.mechanical_ageing.MechanicalAgeing;

public class ModMetalSet {
    public static final MetalSetRegister METAL_SET = MetalSetRegister.create(MechanicalAgeing.MOD_ID);

    public static final MetalSet IRON = METAL_SET.register("iron", new MetalSet.Builder(7.874, 1811, 3134).magnetic());
}
