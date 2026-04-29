package net.mofusya.mechanical_ageing.jei;

import net.mofusya.mechanical_ageing.machinetiles.energy.EnergyType;
import net.mofusya.ornatelib.lang.SeptiLong;

public record EnergyStack(EnergyType<?> type, SeptiLong amount) {
}