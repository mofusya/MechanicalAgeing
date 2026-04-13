package net.mofusya.mechanical_ageing.machinetiles.energy;

import java.util.ArrayList;
import java.util.function.Supplier;

public class EnergySlotList extends ArrayList<EnergySlotProperties> {

    public EnergySlotList create(int x, Supplier<EnergyType<?>> type, int capacity) {
        return this.create(x, type, capacity, capacity, capacity, 0);
    }

    public EnergySlotList create(int x, Supplier<EnergyType<?>> type, int capacity, int maxTransfer) {
        return this.create(x, type, capacity, maxTransfer, maxTransfer, 0);
    }

    public EnergySlotList create(int x, Supplier<EnergyType<?>> type, int capacity, int maxReceive, int maxExtract) {
        return this.create(x, type, capacity, maxReceive, maxExtract, 0);
    }

    public EnergySlotList create(int x, Supplier<EnergyType<?>> type, int capacity, int maxReceive, int maxExtract, int energy) {
        this.add(new EnergySlotProperties(x, type, capacity, maxReceive, maxExtract, energy));
        return this;
    }
}