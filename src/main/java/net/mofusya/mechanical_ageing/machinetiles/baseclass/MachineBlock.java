package net.mofusya.mechanical_ageing.machinetiles.baseclass;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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

    @Override @Nullable
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
