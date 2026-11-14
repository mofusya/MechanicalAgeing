package net.mofusya.mechanical_ageing.matter;

import net.kairayk.yklibrary.util.VectorColor;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.ornatelib.registries.OrnateHolder;
import net.mofusya.ornatelib.registries.OrnateRegister;
import net.mofusya.ornatelib.registries.OrnateRegistry;

public class MAgMatters {
    public static final OrnateRegistry<IMatterType> REGISTRY = new OrnateRegistry<>();

    public static final OrnateRegister<IMatterType> R = new OrnateRegister<>(C.MOD_ID, REGISTRY::register);

    public static final OrnateHolder<IMatterType> EMPTY = R.register("empty", id -> new MatterType(id, VectorColor.TRANSPARENT));
}
