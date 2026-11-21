package net.mofusya.mechanical_ageing.handlers.units.unit;

import net.kairayk.yklibrary.util.VectorColor;
import net.mofusya.mechanical_ageing.handlers.units.DoubleUnitHandler;

/**
 * A Capability handler interface for the Ralts unit.
 */
public interface IRaltsUnitHandler extends DoubleUnitHandler {

    @Override
    default VectorColor getColor(int slot){
        return VectorColor.fromARGB(0xFF_f8f644);
    }
}
