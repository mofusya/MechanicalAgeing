package net.mofusya.mechanical_ageing.blocks.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.mofusya.mechanical_ageing.cables.CableConnection;
import net.mofusya.mechanical_ageing.cables.CableNetworkUtils;
import net.mofusya.mechanical_ageing.cables.ICableType;
import net.mofusya.mechanical_ageing.items.implemts.IScrewDriverItem;

import java.util.function.Supplier;

public class PlusMatterCableBlock<TYPE extends ICableType<?>> extends Block {

    public static final EnumProperty<CableConnection> NORTH = EnumProperty.create("north", CableConnection.class);
    public static final EnumProperty<CableConnection> SOUTH = EnumProperty.create("south", CableConnection.class);
    public static final EnumProperty<CableConnection> EAST = EnumProperty.create("east", CableConnection.class);
    public static final EnumProperty<CableConnection> WEST = EnumProperty.create("west", CableConnection.class);
    public static final EnumProperty<CableConnection> UP = EnumProperty.create("up", CableConnection.class);
    public static final EnumProperty<CableConnection> DOWN = EnumProperty.create("down", CableConnection.class);

    private final Supplier<TYPE> type;

    public PlusMatterCableBlock(Supplier<TYPE> type, Properties build) {
        super(build);
        this.type = type;
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(NORTH, CableConnection.NONE)
                .setValue(SOUTH, CableConnection.NONE)
                .setValue(EAST, CableConnection.NONE)
                .setValue(WEST, CableConnection.NONE)
                .setValue(UP, CableConnection.NONE)
                .setValue(DOWN, CableConnection.NONE)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    //todo: Add a simple getShape() to this.
    //todo: Make the networkRecs rebuild if any block is changed around it. (onPlace(), neighborChanged(), onRemove())
    //todo: Add a wrench system to this shit. (Probably the use() method)


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.375, 0.375, 0.375, 0.625, 0.625, 0.625), BooleanOp.OR);

        if (!state.getValue(UP).is(CableConnection.NONE)){
            shape = Shapes.join(shape, Shapes.box(0.375, 0.625, 0.375, 0.625, 1, 0.625), BooleanOp.OR);
        }
        if (!state.getValue(DOWN).is(CableConnection.NONE)){
            shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.375, 0.625, 0.375, 0.625), BooleanOp.OR);
        }
        if (!state.getValue(NORTH).is(CableConnection.NONE)){
            shape = Shapes.join(shape, Shapes.box(0.375, 0.375, 0, 0.625, 0.625, 0.375), BooleanOp.OR);
        }
        if (!state.getValue(SOUTH).is(CableConnection.NONE)){
            shape = Shapes.join(shape, Shapes.box(0.375, 0.375, 0.625, 0.625, 0.625, 1), BooleanOp.OR);
        }
        if (!state.getValue(EAST).is(CableConnection.NONE)){
            shape = Shapes.join(shape, Shapes.box(0.625, 0.375, 0.375, 1, 0.625, 0.625), BooleanOp.OR);
        }
        if (!state.getValue(WEST).is(CableConnection.NONE)){
            shape = Shapes.join(shape, Shapes.box(0, 0.375, 0.375, 0.375, 0.625, 0.625), BooleanOp.OR);
        }
        return shape;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!(level instanceof ServerLevel server)) return super.use(state, level, pos, player, hand, result);
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() instanceof IScrewDriverItem && player.isShiftKeyDown()){
            setConnection(state, result.getDirection(), getConnection(state, result.getDirection()).next());
            server.setBlock(pos, state, 3);
        }
        return super.use(state, level, pos, player, hand, result);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!(level instanceof ServerLevel server)) return;

        CableNetworkUtils.onPlaceCable(this.getType(), server, pos);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, level, pos, newState, isMoving);
        if (!(level instanceof ServerLevel server)) return;

        CableNetworkUtils.onRemoveCable(this.getType(), server, pos);
    }

    public TYPE getType() {
        return this.type.get();
    }

    public static CableConnection getConnection(BlockState state, Direction direction) {
        return switch (direction) {
            case DOWN -> state.getValue(DOWN);
            case UP -> state.getValue(UP);
            case NORTH -> state.getValue(NORTH);
            case SOUTH -> state.getValue(SOUTH);
            case WEST -> state.getValue(WEST);
            case EAST -> state.getValue(EAST);
        };
    }

    public static void setConnection(BlockState state, Direction direction, CableConnection cableConnection) {
        state.setValue(switch (direction) {
            case DOWN -> DOWN;
            case UP -> UP;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
        }, cableConnection);
    }
}
