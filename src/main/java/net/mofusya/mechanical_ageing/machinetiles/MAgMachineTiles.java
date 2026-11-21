package net.mofusya.mechanical_ageing.machinetiles;

import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.machinetiles.machinetile.Test;
import net.mofusya.ornatelib.registries.OrnateHolder;

public class MAgMachineTiles {
    public static final MachineTileRegister R = new MachineTileRegister(C.MOD_ID);

    public static final OrnateHolder<MachineTile> TEST = R.register("test", Test::new);
}
