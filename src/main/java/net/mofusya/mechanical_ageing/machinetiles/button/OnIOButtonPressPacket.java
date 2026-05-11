package net.mofusya.mechanical_ageing.machinetiles.button;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.mofusya.mechanical_ageing.util.QuadConsumer;
import net.mofusya.ornatelib.registries.network.packet.ServerPacket;

import java.util.function.Supplier;

public class OnIOButtonPressPacket extends ServerPacket {

    private final QuadConsumer<Integer, Integer, ServerPlayer, BlockPos> run;
    public int index;
    public BlockPos pos;

    public OnIOButtonPressPacket(String id, QuadConsumer<Integer, Integer, ServerPlayer, BlockPos> run) {
        super(id);
        this.run = run;
    }

    public void send2Server(int index, int type, BlockPos pos) {
        OnIOButtonPressPacket toReturn = this.newSelf();
        toReturn.index = index;
        toReturn.type = type;
        toReturn.pos = pos;
        this.simpleChannel.sendToServer(toReturn);
    }

    @Override
    protected OnIOButtonPressPacket newSelf() {
        return new OnIOButtonPressPacket(this.id, this.run);
    }

    @Override
    public <T extends ServerPacket> void encode(T packet, FriendlyByteBuf buffer) {
        super.encode(packet, buffer);
        buffer.writeInt(((OnIOButtonPressPacket) packet).index);
        buffer.writeBlockPos(((OnIOButtonPressPacket) packet).pos);
    }

    @Override
    public <T extends ServerPacket> T decode(FriendlyByteBuf buffer) {
        OnIOButtonPressPacket toReturn = super.decode(buffer);
        toReturn.index = buffer.readInt();
        toReturn.pos = buffer.readBlockPos();
        return (T) toReturn;
    }

    @Override
    public <T extends ServerPacket> void handle(T packet, Supplier<NetworkEvent.Context> contextSupplier) {
        this.index = ((OnIOButtonPressPacket) packet).index;
        this.pos = ((OnIOButtonPressPacket) packet).pos;
        super. handle(packet, contextSupplier);
    }

    @Override
    protected void run(ServerPlayer serverPlayer, int i) {
        this.run.accept(this.index, i, serverPlayer, this.pos);
    }
}
