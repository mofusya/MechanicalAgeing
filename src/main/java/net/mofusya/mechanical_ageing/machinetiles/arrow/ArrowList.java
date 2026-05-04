package net.mofusya.mechanical_ageing.machinetiles.arrow;

import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineMenu;

import java.util.ArrayList;
import java.util.function.Function;

public class ArrowList extends ArrayList<ArrowProperties> {
    public ArrowList create(int x, int y, int size, ArrowType type) {
        return this.create(x, y, size, blockEntity -> 1f, type);
    }

    public ArrowList create(int x, int y, int size, Function<MachineMenu, Float> showPercentageFunc, ArrowType type) {
        this.add(new ArrowProperties(x, y, size, showPercentageFunc, type));
        return this;
    }
}