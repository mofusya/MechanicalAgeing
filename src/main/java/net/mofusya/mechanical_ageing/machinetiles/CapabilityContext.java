package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.mofusya.mechanical_ageing.machinetiles.direction.MachineDirectionHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.IMatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.watt.IWattEnergyStorage;
import net.mofusya.ornatelib.util.annotation.FieldsMayBeNullByDefault;
import net.mofusya.ornatelib.util.annotation.MethodsMayReturnNullByDefault;
import net.mofusya.ornatelib.util.annotation.ParametersMayBeNullByDefault;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@FieldsMayBeNullByDefault
@ParametersMayBeNullByDefault
@MethodsMayReturnNullByDefault
public class CapabilityContext {
    @NotNull
    private final ItemStackHandler itemHandler;
    @NotNull
    private final LazyOptional<IItemHandler> lazyItemHandler;

    @NotNull
    private final List<IEnergyStorage> energyStorages;
    @NotNull
    private final List<LazyOptional<IEnergyStorage>> lazyEnergyHandler;

    private final IWattEnergyStorage wattEnergyStorage;
    private final LazyOptional<IWattEnergyStorage> lazyWattEnergyHandler;

    private final IMatterHandler matterHandler;
    private final LazyOptional<IMatterHandler> lazyMatterHandler;

    private final FluidTank fluidTank;
    private final LazyOptional<FluidTank> lazyFluidHandler;

    @NotNull
    private final MachineDirectionHandler directionHandler;

    public CapabilityContext(@NotNull ItemStackHandler itemHandler, @NotNull LazyOptional<IItemHandler> lazyItemHandler, @NotNull List<IEnergyStorage> energyStorages, @NotNull List<LazyOptional<IEnergyStorage>> lazyEnergyHandler, IWattEnergyStorage wattEnergyStorage, LazyOptional<IWattEnergyStorage> lazyWattEnergyHandler, IMatterHandler matterHandler, LazyOptional<IMatterHandler> lazyMatterHandler, FluidTank fluidTank, LazyOptional<FluidTank> lazyFluidHandler, @NotNull MachineDirectionHandler directionHandler) {
        this.itemHandler = itemHandler;
        this.lazyItemHandler = lazyItemHandler;
        this.energyStorages = energyStorages;
        this.lazyEnergyHandler = lazyEnergyHandler;
        this.wattEnergyStorage = wattEnergyStorage;
        this.lazyWattEnergyHandler = lazyWattEnergyHandler;
        this.matterHandler = matterHandler;
        this.lazyMatterHandler = lazyMatterHandler;
        this.fluidTank = fluidTank;
        this.lazyFluidHandler = lazyFluidHandler;
        this.directionHandler = directionHandler;
    }

    @NotNull
    public MachineDirectionHandler getDirectionHandler() {
        return directionHandler;
    }

    @NotNull
    public List<IEnergyStorage> getEnergyStorages() {
        return energyStorages;
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    @NotNull
    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    @NotNull
    public List<LazyOptional<IEnergyStorage>> getLazyEnergyHandler() {
        return lazyEnergyHandler;
    }

    public LazyOptional<FluidTank> getLazyFluidHandler() {
        return lazyFluidHandler;
    }

    @NotNull
    public LazyOptional<IItemHandler> getLazyItemHandler() {
        return lazyItemHandler;
    }

    public LazyOptional<IMatterHandler> getLazyMatterHandler() {
        return lazyMatterHandler;
    }

    public LazyOptional<IWattEnergyStorage> getLazyWattEnergyHandler() {
        return lazyWattEnergyHandler;
    }

    public IMatterHandler getMatterHandler() {
        return matterHandler;
    }

    public IWattEnergyStorage getWattEnergyStorage() {
        return wattEnergyStorage;
    }
}
