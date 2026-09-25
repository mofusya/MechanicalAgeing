package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.ornatelib.lang.UnLong;

public class ManualBioConcentratorTile extends MachineTile {
    public ManualBioConcentratorTile(ResourceLocation id) {
        super(id);
    }

    @Override
    public MutableComponent getDisplayName() {
        return Component.translatable("block.mechanical_ageing.manual_bio_concentrator.machine_name");
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
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(176 / 2 - 9, 23, type -> type.is(MAgMatterTypes.METHANE), UnLong.thousand(), UnLong.zero(), UnLong.thousand());
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        super.tick(level, pos, state, blockEntity);

        BlockState composter = level.getBlockState(pos.below());
        if (!composter.is(Blocks.COMPOSTER)) return;

        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        if (matterHandler == null) return;

        MatterStack result = new MatterStack(MAgMatterTypes.METHANE, new UnLong(2));

        if (composter.getValue(ComposterBlock.LEVEL) == ComposterBlock.MAX_LEVEL &&
            matterHandler.canReceiveFromInside(result, 0)){
            matterHandler.receiveFromInside(result, 0);
        }
    }
}
