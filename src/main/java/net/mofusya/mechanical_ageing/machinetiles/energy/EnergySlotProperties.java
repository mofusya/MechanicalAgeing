package net.mofusya.mechanical_ageing.machinetiles.energy;

import java.util.function.Supplier;

public record EnergySlotProperties(int x, Supplier<EnergyType<?>> type, int capacity, int maxReceive, int maxExtract, int energy) {

    public EnergyType<?> energyType() {
        return this.type.get();
    }
}