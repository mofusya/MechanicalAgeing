package net.mofusya.mechanical_ageing.tiles;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.mofusya.mechanical_ageing.machinetiles.matter.IMatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.watt.WattEnergyStorage;

public class MAgCapabilities {
    public static final Capability<IMatterHandler> MATTER = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<WattEnergyStorage> WATT = CapabilityManager.get(new CapabilityToken<>() {
    });
}