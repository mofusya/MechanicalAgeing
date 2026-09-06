package net.mofusya.mechanical_ageing.tiles.tile;

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
import net.mofusya.mechanical_ageing.machinetiles.watt.WattEnergyStorage;
import net.mofusya.mechanical_ageing.machinetiles.watt.WattSlotProperties;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.tiles.energy.ForgeEnergyStorage;
import net.mofusya.mechanical_ageing.tiles.energy.ForgeEnergyType;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;
import org.jetbrains.annotations.Nullable;

@MethodsReturnNonNullByDefault
public class DestructorTile extends MachineTile {
    public DestructorTile(ResourceLocation location) {
        super(location);
    }

    @Override
    public EnergySlotList getEnergySlots(EnergySlotList slots) {
        return super.getEnergySlots(slots)
                .create(34, ForgeEnergyType::new, 1000, 10, 0)
                /*.create(151, StarDustEnergyType::new, 100, 0, 10)*/;
    }

    @Override
    public @Nullable WattSlotProperties getWattSlot() {
        return new WattSlotProperties(151, new UnLong(100), UnLong.zero(), new UnLong(10));
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(97, 23, matterType -> matterType.is(MAgMatterTypes.WATER), UnLong.thousand().multi(100), UnLong.zero(), UnLong.ten())
                .create(124, 23, matterType -> matterType.is(MAgMatterTypes.FUEL), UnLong.hundred(), UnLong.zero(), UnLong.ten());
    }

    @Override
    public @Nullable FluidSlotProperties getFluidSlot() {
        return new FluidSlotProperties(62, 15, fluid -> fluid.isSame(Fluids.LAVA), 10000);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        super.tick(level, pos, state, blockEntity);

        ForgeEnergyStorage forgeEnergyStorage = (ForgeEnergyStorage) blockEntity.getEnergyStorage(0);
        //StarDustEnergyStorage starDustEnergyStorage = (StarDustEnergyStorage) blockEntity.getEnergyStorage(1);
        WattEnergyStorage wattEnergyStorage = (WattEnergyStorage) blockEntity.getWattEnergyStorage();
        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        FluidTank fluidTank = blockEntity.getFluidTank();
        if (matterHandler == null) return;
        if (fluidTank == null) return;

        if (forgeEnergyStorage.getEnergyStored() > 100 && fluidTank.drain(500, IFluidHandler.FluidAction.SIMULATE).getAmount() >= 500 &&
                //starDustEnergyStorage.receiveEnergyFromInside(new QuintLong(1), true).isGreaterOrSameThan(new QuintLong(1)) &&
                wattEnergyStorage.canReceiveFromInside(new UnLong(1)) &&
                matterHandler.canReceiveFromInside(new MatterStack(MAgMatterTypes.WATER, UnLong.thousand()), 0) &&
                matterHandler.canReceiveFromInside(new MatterStack(MAgMatterTypes.FUEL, new UnLong(1)), 1)
        ) {
            forgeEnergyStorage.extractEnergyFromInside(100, false);
            fluidTank.drain(500, IFluidHandler.FluidAction.EXECUTE);
            //starDustEnergyStorage.receiveEnergyFromInside(new QuintLong(1), false);
            wattEnergyStorage.receiveFromInside(new UnLong(1), false);
            matterHandler.receiveFromInside(new MatterStack(MAgMatterTypes.WATER, UnLong.thousand()), 0);
            matterHandler.receiveFromInside(new MatterStack(MAgMatterTypes.FUEL, new UnLong(1)), 1);
        }
    }
}