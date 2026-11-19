package net.mofusya.mechanical_ageing.handlers;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.mofusya.mechanical_ageing.handlers.matter.IMatterStackHandler;

/**
 * The final class for static storing all Units {@code Capability} for this mod.
 */
public final class MAgCapabilities {
    /**
     * The fields for storing the Kelvin, Ralts, Vaarr, Nealts, and Tsealv Capabilities.
     */
    public static final Capability<IUnitHandler<Double>> KELVIN = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<IUnitHandler<Double>> RALTS = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<IUnitHandler<Double>> VAARR = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<IUnitHandler<Double>> TSEALV = CapabilityManager.get(new CapabilityToken<>() {
    });

    /**
     * The field for storing the Matter Capability.
     */
    public static final Capability<IMatterStackHandler> MATTER = CapabilityManager.get(new CapabilityToken<>() {
    });
}