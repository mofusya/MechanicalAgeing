package net.mofusya.mechanical_ageing.machinetiles.arrow;

import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;

import java.util.ArrayList;
import java.util.function.Function;

public class ArrowList extends ArrayList<ArrowProperties> {
    public ArrowList create(int x, int y, ArrowType type) {
        return this.create(x, y, blockEntity -> 1f, type);
    }

    public ArrowList create(int x, int y, Function<MachineBlockEntity, Float> showPercentageFunc, ArrowType type) {
        this.add(new ArrowProperties(x, y, showPercentageFunc, type));
        return this;
    }
}