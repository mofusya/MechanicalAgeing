package net.mofusya.mechanical_ageing.machinetiles.arrow;

import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineMenu;

import java.util.function.Function;

public record ArrowProperties(int x, int y, int size, Function<MachineMenu, Float> showPercentageFunc, ArrowType type) {
}