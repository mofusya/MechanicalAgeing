package net.mofusya.mechanical_ageing.tiles.tile;

import net.flansflame.flans_star_forge.energy.QuintLong;
import net.flansflame.flans_star_forge.energy.StarDustEnergyStorage;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergySlotList;
import net.mofusya.mechanical_ageing.machinetiles.fluid.FluidSlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.matter.MatterTypes;
import net.mofusya.mechanical_ageing.tiles.energy.ForgeEnergyStorage;
import net.mofusya.mechanical_ageing.tiles.energy.ForgeEnergyType;
import net.mofusya.mechanical_ageing.tiles.energy.StarDustEnergyType;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;
import org.jetbrains.annotations.Nullable;

@MethodsReturnNonnullByDefault
public class DestructorTile extends MachineTile {
    public DestructorTile(ResourceLocation location) {
        super(location);
    }

    @Override
    public EnergySlotList getEnergySlots(EnergySlotList slots) {
        return super.getEnergySlots(slots)
                .create(34, ForgeEnergyType::new, 1000, 10, 0)
                .create(151, StarDustEnergyType::new, 100, 0, 10);
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(97, 23, matterType -> matterType.is(MatterTypes.WATER), SeptiLongValue.THOUSAND.get().multiply(100), SeptiLongValue.ZERO.get(), SeptiLongValue.TEN.get())
                .create(124, 23, matterType -> matterType.is(MatterTypes.FUEL), SeptiLongValue.HUNDRED.get(), SeptiLongValue.ZERO.get(), SeptiLongValue.TEN.get());
    }

    @Override
    public @Nullable FluidSlotProperties getFluidSlot() {
        return new FluidSlotProperties(62, 15, fluid -> fluid.isSame(Fluids.LAVA), 10000);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        ForgeEnergyStorage forgeEnergyStorage = (ForgeEnergyStorage) blockEntity.getEnergyStorage(0);
        StarDustEnergyStorage starDustEnergyStorage = (StarDustEnergyStorage) blockEntity.getEnergyStorage(1);
        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        FluidTank fluidTank = blockEntity.getFluidTank();
        if (matterHandler == null) return;
        if (fluidTank == null) return;

        if (forgeEnergyStorage.getEnergyStored() > 100 && fluidTank.drain(500, IFluidHandler.FluidAction.SIMULATE).getAmount() >= 500 &&
                starDustEnergyStorage.receiveEnergyFromInside(new QuintLong(1), true).isGreaterOrSameThan(new QuintLong(1)) &&
                matterHandler.canReceiveFromInside(new MatterStack(MatterTypes.WATER, SeptiLongValue.THOUSAND.get()), 0) &&
                matterHandler.canReceiveFromInside(new MatterStack(MatterTypes.FUEL, new SeptiLong(1)), 1)
        ) {
            forgeEnergyStorage.extractEnergyFromInside(100, false);
            fluidTank.drain(500, IFluidHandler.FluidAction.EXECUTE);
            starDustEnergyStorage.receiveEnergyFromInside(new QuintLong(1), false);
            matterHandler.receiveFromInside(new MatterStack(MatterTypes.WATER, SeptiLongValue.THOUSAND.get()), 0);
            matterHandler.receiveFromInside(new MatterStack(MatterTypes.FUEL, new SeptiLong(1)), 1);
        }
    }
}