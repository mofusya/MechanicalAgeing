package net.mofusya.mechanical_ageing.handler;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.mofusya.mechanical_ageing.handler.matter.IMatterHandler;

/**
 * The final class for static storing all Units {@code Capability} for this mod.
 */
public final class MAgCapabilities {
    /**
     * The fields for storing the Kelvin, Ralts, Vaarr, Nealts, and Tsealv Capabilities.
     */
    public static final Capability<IUnitHandler<Double>> KELVIN = MAgCapabilities.<IUnitHandler<Double>>create();
    public static final Capability<IUnitHandler<Double>> RALTS = MAgCapabilities.<IUnitHandler<Double>>create();
    public static final Capability<IUnitHandler<Double>> VAARR = MAgCapabilities.<IUnitHandler<Double>>create();
    public static final Capability<IUnitHandler<Double>> NEALTS = MAgCapabilities.<IUnitHandler<Double>>create();
    public static final Capability<IUnitHandler<Double>> TSEALV = MAgCapabilities.<IUnitHandler<Double>>create();

    /**
     * The field for storing the Matter Capability.
     */
    public static final Capability<IMatterHandler> MATTER = MAgCapabilities.<IMatterHandler>create();

    /**
     * A helper method to create a new {@code Capability}/{@code CapabilityToken}
     * @return A new {@code Capability}/{@code CapabilityToken}
     * @param <T> An @anotated {@code interface} for the {@code Capability}.
     */
    public static <T> Capability<T> create(){
        return CapabilityManager.get(new CapabilityToken<>() {
        });
    }
}