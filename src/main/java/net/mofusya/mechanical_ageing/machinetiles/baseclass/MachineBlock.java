package net.mofusya.mechanical_ageing.machinetiles.baseclass;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.ornatelib.util.annotation.FieldsAreNonNullByDefault;
import org.jetbrains.annotations.Nullable;

@FieldsAreNonNullByDefault
public class MachineBlock extends BaseEntityBlock {
    private final MachineTile machineTile;

    public MachineBlock(Properties build, MachineTile machineTile) {
        super(build);
        this.machineTile = machineTile;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MachineBlockEntity machineBlockEntity) {
                machineBlockEntity.drop();
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide) return super.use(state, level, pos, player, hand, result);

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MachineBlockEntity machineBlockEntity) {
            NetworkHooks.openScreen((ServerPlayer) player, machineBlockEntity, pos);
            return InteractionResult.SUCCESS;
        }

        throw new IllegalStateException("The MachineBlockEntity could not open. :)");
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) return null;

        return createTickerHelper(type, this.getMachineTile().getBlockEntityType().get(),
                (pLevel, pPos, pState, pBlockEntity) -> pBlockEntity.tick(pLevel, pPos, pState));
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return machineTile.getBlockEntityType().get().create(pos, state);
    }

    public MachineTile getMachineTile() {
        return machineTile;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }
}
