package net.mofusya.mechanical_ageing.cables;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.blocks.block.PlusMatterCableBlock;
import net.mofusya.ornatelib.util.ArrayMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CableNetwork<TYPE extends ICableType<?>> {

    private final TYPE type;

    private final List<BlockPos> cablePos;
    private final ArrayMap<BlockEntity, ArrayList<Direction>> producer;
    private final ArrayMap<BlockEntity, ArrayList<Direction>> consumer;

    @Nullable
    private BlockPos updateStartPos = null;

    public CableNetwork(TYPE type, BlockPos pos) {
        this(type, List.of(pos));
    }

    public CableNetwork(TYPE type, List<BlockPos> cablePos) {
        this.type = type;
        this.cablePos = new ArrayList<>(cablePos);
        this.producer = new ArrayMap<>();
        this.consumer = new ArrayMap<>();
    }

    public void tick(ServerLevel server) {
        this.rebuildIfUpdateStartIsFilled(server);
    }

    //Rebuild and update all the needed cables, producer, and consumer, if the updateStartPos is filled.
    protected void rebuildIfUpdateStartIsFilled(ServerLevel server) {
        if (this.getUpdateStartPos() == null) return;

        this.clear();

        //Set queue.
        Queue<BlockPos> queue = new ArrayDeque<>();
        queue.add(this.getUpdateStartPos());

        //Loop for all queues.
        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            this.getCablePos().add(pos);

            BlockState state = server.getBlockState(pos);
            if (!state.is(this.getType().getBlock())) continue;

            //Loop for all connected directions.
            for (Direction direction : Direction.values()) {
                CableConnection connection = PlusMatterCableBlock.getConnection(state, direction);
                if (connection.is(CableConnection.NONE)) continue;

                BlockPos nextPos = pos.relative(direction);
                BlockState nextState = server.getBlockState(pos);
                @Nullable
                BlockEntity nextBlockEntity = server.getBlockEntity(nextPos);

                //Run rebuild for the cable or the blockEntity.
                if (connection.is(CableConnection.NEUTRAL)) {
                    this.rebuildCableToPile(queue, nextPos, nextState, direction);
                } else {
                    this.rebuildBlockEntityToPile(nextBlockEntity, direction, connection);
                }
            }
        }

        this.updateStartPos = null;
    }

    //If the pos (or the nextPos) has the same cable, add it to the queue of the rebuild.
    protected void rebuildCableToPile(Queue<BlockPos> queue, BlockPos pos, BlockState state, Direction direction) {
        if (!state.is(this.getType().getBlock())) return;
        CableConnection nextConnection = PlusMatterCableBlock.getConnection(state, direction.getOpposite());
        if (nextConnection.is(CableConnection.NONE)) return;

        queue.add(pos);
    }

    //If the blockEntity (or the nextBlockEntity) has a blockEntity and has the capability of this CableType, add it to the network blockEntity pile.
    protected void rebuildBlockEntityToPile(BlockEntity blockEntity, Direction direction, CableConnection connection) {
        if (blockEntity == null || !blockEntity.getCapability(this.getType().getCapability()).isPresent()) return;

        var blockEntityPile = switch (connection) {
            case INSERT -> this.getConsumer();
            case EXTRACT -> this.getProducer();
            default -> throw new IllegalStateException("Unexpected value: " + connection);
        };

        if (blockEntityPile.containsKey(blockEntity)) {
            blockEntityPile.get(blockEntity).add(direction);
        } else {
            blockEntityPile.put(blockEntity, new ArrayList<>(List.of(direction)));
        }
    }


    public TYPE getType() {
        return type;
    }

    @Nullable
    public BlockPos getUpdateStartPos() {
        return this.updateStartPos;
    }

    public void update() {
        this.update(this.getCablePos().get(0));
    }

    public void update(@NotNull BlockPos updateStartPos) {
        this.updateStartPos = updateStartPos;
    }

    public List<BlockPos> getCablePos() {
        return this.cablePos;
    }

    public ArrayMap<BlockEntity, ArrayList<Direction>> getProducer() {
        return this.producer;
    }

    public ArrayMap<BlockEntity, ArrayList<Direction>> getConsumer() {
        return this.consumer;
    }

    //Clear all pos, producer, consumer.
    protected void clear() {
        this.getCablePos().clear();
        this.getProducer().clear();
        this.getConsumer().clear();
    }
}
