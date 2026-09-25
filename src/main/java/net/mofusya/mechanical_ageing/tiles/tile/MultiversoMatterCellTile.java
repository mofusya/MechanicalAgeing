package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlock;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.matter.MatterManager;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

@MethodsReturnNonNullByDefault
public class MultiversoMatterCellTile extends MachineTile {
    public MultiversoMatterCellTile(ResourceLocation id) {
        super(id);
    }

    @Override
    public MutableComponent getDisplayName() {
        return Component.translatable("block.mechanical_ageing.multiverso_matter_cell.machine_name");
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
    public int getDataSlotCount() {
        return 1;
    }

    @Override
    public int getDefaultDataSlotAmount(int index) {
        return -1;
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(176 / 2 - 9, 23, matterType -> true, tags -> true, UnLong.quadragintillion(), UnLong.zero(), UnLong.quadragintillion());
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        super.tick(level, pos, state, blockEntity);
        if (blockEntity.getData(0) < 0) return;

        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        if (matterHandler == null || matterHandler.getSpace(0).isSmallerOrSameAs(UnLong.zero())) return;

        matterHandler.receiveFromInside(new MatterStack(MatterManager.get().values().stream().toList().get(blockEntity.getData(0)), matterHandler.getSpace(0)), 0);
    }

    @Override
    public BlockItem getCustomItem(Block block, Item.Properties build) {
        return new MultiversoMatterCellBlockItem(block, build);
    }

    @Override
    public MachineBlock getCustomBlock(Supplier<BlockEntityType<? extends MachineBlockEntity>> blockEntity, MachineTile machineTile) {
        return new MultiversoMatterCellBlock(blockEntity, machineTile);
    }

    public static class MultiversoMatterCellBlockItem extends BlockItem{
        public MultiversoMatterCellBlockItem(Block block, Properties build) {
            super(block, build);
        }

        @Override
        public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            super.appendHoverText(itemStack, level, tooltip, flag);
            if (!itemStack.getOrCreateTag().contains(C.MATTER_INDEX)) return;

            MatterType matterType = MatterManager.get().values().stream().toList().get(itemStack.getOrCreateTag().getInt(C.MATTER_INDEX));
            tooltip.add(Component.translatable(matterType.getTranslationId()).withStyle(ChatFormatting.GRAY));
        }
    }

    public static class MultiversoMatterCellBlock extends MachineBlock {
        public MultiversoMatterCellBlock(Supplier<BlockEntityType<? extends MachineBlockEntity>> blockEntity, MachineTile machineTile) {
            super(blockEntity, machineTile);
        }

        @Override
        public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack itemStack) {
            super.setPlacedBy(level, pos, state, entity, itemStack);
            if (level.isClientSide) return;

            if (entity instanceof Player && itemStack.is(MAgMachines.MULTIVERSO_MATTER_CELL.block().asItem())){
                if (!itemStack.getOrCreateTag().contains(C.MATTER_INDEX)) return;

                int matterIndex = itemStack.getOrCreateTag().getInt(C.MATTER_INDEX);
                if (matterIndex < 0) return;

                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof MachineBlockEntity machine){
                    machine.setData(0, matterIndex);
                }
            }
        }
    }
}
