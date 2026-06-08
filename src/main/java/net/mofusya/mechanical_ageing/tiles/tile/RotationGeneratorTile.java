package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.machinetiles.watt.WattEnergyStorage;
import net.mofusya.mechanical_ageing.machinetiles.watt.WattSlotProperties;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;
import org.jetbrains.annotations.Nullable;

@MethodsReturnNonNullByDefault
public class RotationGeneratorTile extends MachineTile {
    public RotationGeneratorTile(ResourceLocation id) {
        super(id);
    }

    @Override
    public SlotList getSlots(SlotList slots) {
        return new SlotList();
    }

    @Override
    public int getUpgradeArchiveSlot() {
        return -1;
    }

    @Override
    public MutableComponent getDisplayName() {
        return Component.translatable("block.mechanical_ageing.basic_rotation_generator.machine_name");
    }

    @Override
    public @Nullable WattSlotProperties getWattSlot() {
        return new WattSlotProperties(151, SeptiLongValue.THOUSAND.get().multiply(48), new SeptiLong(), SeptiLongValue.THOUSAND.get().multiply(48));
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(176 / 2 - 9, 23, matterType -> matterType.is(MAgMatterTypes.ROTATION), new SeptiLong(1048576), new SeptiLong(1048576), new SeptiLong());
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        super.tick(level, pos, state, blockEntity);

        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        if (matterHandler == null) return;
        WattEnergyStorage wattEnergyStorage = (WattEnergyStorage) blockEntity.getWattEnergyStorage();
        if (wattEnergyStorage == null) return;

        MatterStack ingredient = new MatterStack(MAgMatterTypes.ROTATION, 100);
        SeptiLong result = new SeptiLong(8);

        if (matterHandler.canExtractFromInside(ingredient, 0) && wattEnergyStorage.canReceiveFromInside(result)){
            int ingredientCount = (int) matterHandler.getStored(0).getAmount().divideAndGetFloat(100);
            int resultCount = (int) wattEnergyStorage.getSpace().divideAndGetFloat(8);
            int count = Math.min(ingredientCount, resultCount);

            ingredient.modifyAmount(amount -> amount.multiply(count));
            result.multiply(count);

            matterHandler.extractFromInside(ingredient, 0);
            wattEnergyStorage.receiveFromInside(result, false);
        }
    }
}
