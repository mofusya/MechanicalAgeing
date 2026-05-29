package net.mofusya.mechanical_ageing.blocks.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NarrowBlock extends Block {
    private final int gapSize;

    public NarrowBlock(Properties build, int gapSize) {
        super(build);
        this.gapSize = gapSize;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return this.makeShape();

    }
    public VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0 + (0.0625 * this.gapSize), 0, 0 + (0.0625 * this.gapSize), 1 - (0.0625 * this.gapSize), 1, 1 - (0.0625 * this.gapSize)), BooleanOp.OR);

        return shape;
    }
}
