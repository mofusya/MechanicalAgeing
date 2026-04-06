package net.mofusya.mechanical_ageing.tiles.energy;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergyType;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergyTypeFunction;

public class ForgeEnergyType extends EnergyType<ForgeEnergyStorage> {
    @Override
    public EnergyTypeFunction<ForgeEnergyStorage> getStorage() {
        return (runnable, capacity, maxReceive, maxExtract, energy) -> new ForgeEnergyStorage(capacity, maxReceive, maxExtract, energy) {
            @Override
            public void onEnergyChanged() {
                runnable.run();
            }
        };
    }

    @Override
    public Capability<? extends IEnergyStorage> getCapability() {
        return ForgeCapabilities.ENERGY;
    }
}