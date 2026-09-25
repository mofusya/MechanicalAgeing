package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
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
public record CapabilityContext(@NotNull MachineBlockEntity blockEntity,
                                @NotNull ItemStackHandler itemHandler,
                                @NotNull LazyOptional<IItemHandler> lazyItemHandler,
                                @NotNull List<IEnergyStorage> energyStorages,
                                @NotNull List<LazyOptional<IEnergyStorage>> lazyEnergyHandler,
                                IWattEnergyStorage wattEnergyStorage,
                                LazyOptional<IWattEnergyStorage> lazyWattEnergyHandler, IMatterHandler matterHandler,
                                LazyOptional<IMatterHandler> lazyMatterHandler, FluidTank fluidTank,
                                LazyOptional<FluidTank> lazyFluidHandler,
                                @NotNull MachineDirectionHandler directionHandler) {
}
