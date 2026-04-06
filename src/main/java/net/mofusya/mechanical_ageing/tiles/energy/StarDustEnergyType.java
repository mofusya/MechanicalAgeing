package net.mofusya.mechanical_ageing.tiles.energy;

import net.flansflame.flans_star_forge.energy.QuintLong;
import net.flansflame.flans_star_forge.energy.StarDustEnergyStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergyType;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergyTypeFunction;

public class StarDustEnergyType extends EnergyType<StarDustEnergyStorage> {
    @Override
    public EnergyTypeFunction<StarDustEnergyStorage> getStorage() {
        return (runnable, capacity, maxReceive, maxExtract, energy) -> new StarDustEnergyStorage(new QuintLong(capacity), new QuintLong(maxReceive), new QuintLong(maxExtract), new QuintLong(energy)) {
            @Override
            public void onEnergyChanged() {
                runnable.run();
            }
        };
    }

    @Override
    public Capability<? extends IEnergyStorage> getCapability() {
        return StarDustEnergyStorage.CAPABILITY;
    }
}
