package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.mofusya.mechanical_ageing.machinetiles.matter.IMatterHandler;

public class MAgCapabilities {
    public static final Capability<IMatterHandler> MATTER = CapabilityManager.get(new CapabilityToken<>() {
    });
}
