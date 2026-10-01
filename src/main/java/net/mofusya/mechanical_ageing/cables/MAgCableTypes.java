package net.mofusya.mechanical_ageing.cables;

import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.cables.cable.PlusMatterCableType;

public class MAgCableTypes {
    public static final CableTypeRegister CABLE_TYPES = new CableTypeRegister(C.MOD_ID);

    public static final ICableType<?> PLUS_MATTER = CABLE_TYPES.create(new PlusMatterCableType());
}
