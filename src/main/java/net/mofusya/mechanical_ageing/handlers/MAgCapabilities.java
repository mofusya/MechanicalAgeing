package net.mofusya.mechanical_ageing.handlers;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.mofusya.mechanical_ageing.handlers.matter.IMatterStackHandler;
import net.mofusya.mechanical_ageing.handlers.units.unit.IKelvinUnitHandler;
import net.mofusya.mechanical_ageing.handlers.units.unit.IRaltsUnitHandler;
import net.mofusya.mechanical_ageing.handlers.units.unit.ITsealvUnitHandler;
import net.mofusya.mechanical_ageing.handlers.units.unit.IVaarrUnitHandler;

/**
 * The final class for static storing all Units {@code Capability} for this mod.
 */
public final class MAgCapabilities {
    /**
     * The fields for storing the Kelvin, Ralts, Vaarr, Nealts, and Tsealv Capabilities.
     */
    public static final Capability<IKelvinUnitHandler> KELVIN = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<IRaltsUnitHandler> RALTS = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<IVaarrUnitHandler> VAARR = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<ITsealvUnitHandler> TSEALV = CapabilityManager.get(new CapabilityToken<>() {
    });

    /**
     * The field for storing the Matter Capability.
     */
    public static final Capability<IMatterStackHandler> MATTER = CapabilityManager.get(new CapabilityToken<>() {
    });
}