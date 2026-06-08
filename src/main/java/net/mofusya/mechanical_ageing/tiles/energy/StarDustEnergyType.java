package net.mofusya.mechanical_ageing.tiles.energy;

import net.flansflame.flans_star_forge.energy.QuintLong;
import net.flansflame.flans_star_forge.energy.StarDustEnergyStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergyType;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergyTypeFunction;
import net.mofusya.ornatelib.lang.SeptiLong;

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

    @Override
    public String suffix() {
        return "SdE";
    }

    @Override
    public int getColor() {
        return 0xD02A36B1;
    }

    @Override
    public int getGradientColor() {
        return 0xD04A148C;
    }

    @Override
    public void serializeNBT(IEnergyStorage iEnergyStorage, CompoundTag tag, String nbtId) {
        StarDustEnergyStorage storage = (StarDustEnergyStorage) iEnergyStorage;
        long[] layers = storage.exGetEnergyStored().getLayer();

        for(int i = 0; i < layers.length; ++i) {
            tag.putLong(nbtId + "_layer" + i, layers[i]);
        }
    }

    @Override
    public void deserializeNBT(IEnergyStorage iEnergyStorage, CompoundTag tag, String nbtId) {
        StarDustEnergyStorage storage = (StarDustEnergyStorage) iEnergyStorage;
        long[] layers = new long[5];

        for(int i = 0; i < layers.length; ++i) {
            layers[i] = tag.getLong(nbtId + "_layer" + i);
        }

        storage.receiveEnergyFromInside(QuintLong.createFromList(layers), false);
    }
}
