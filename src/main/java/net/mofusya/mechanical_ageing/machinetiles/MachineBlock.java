package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class MachineBlock extends BaseEntityBlock {

    private final MachineTile machineTile;

    private final Supplier<BlockEntityType<? extends MachineBlockEntity>> blockEntity;

    public MachineBlock(Supplier<BlockEntityType<? extends MachineBlockEntity>> blockEntity, MachineTile machineTile) {
        super(machineTile.getBlockBuild());
        this.machineTile = machineTile;
        this.blockEntity = blockEntity;
    }

    public MachineTile getMachineTile() {
        return this.machineTile;
    }

    /*BLOCK ENTITY*/
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MachineBlockEntity machineBlockEntity) {
                machineBlockEntity.drops();
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MachineBlockEntity machineBlockEntity) {
                NetworkHooks.openScreen((ServerPlayer) player, machineBlockEntity, pos);
                return InteractionResult.SUCCESS;
            } else {
                throw new IllegalStateException("The BlockEntity did not open. **** :)");
            }
        }

        return super.use(state, level, pos, player, hand, result);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return blockEntity.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }
        return createTickerHelper(type, blockEntity.get(), (sLevel, sPos, sState, sBlockEntity) -> sBlockEntity.tick(sLevel, sPos, sState));
    }
}
