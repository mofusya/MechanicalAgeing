package net.mofusya.mechanical_ageing.machinetiles.button;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.mofusya.mechanical_ageing.util.TriConsumer;
import net.mofusya.ornatelib.registries.network.packet.ServerPacket;

import java.util.function.Supplier;

public class OnButtonPressPacket extends ServerPacket {

    private final TriConsumer<Integer, ServerPlayer, BlockPos> run;
    public BlockPos pos;

    public OnButtonPressPacket(String id, TriConsumer<Integer, ServerPlayer, BlockPos> run) {
        super(id);
        this.run = run;
    }

    public void send2Server(int type, BlockPos pos) {
        OnButtonPressPacket toReturn = this.newSelf();
        toReturn.type = type;
        toReturn.pos = pos;
        this.simpleChannel.sendToServer(toReturn);
    }

    @Override
    protected OnButtonPressPacket newSelf() {
        return new OnButtonPressPacket(this.id, this.run);
    }

    @Override
    public <T extends ServerPacket> void encode(T packet, FriendlyByteBuf buffer) {
        super.encode(packet, buffer);
        buffer.writeBlockPos(((OnButtonPressPacket) packet).pos);
    }

    @Override
    public <T extends ServerPacket> T decode(FriendlyByteBuf buffer) {
        OnButtonPressPacket toReturn = super.decode(buffer);
        toReturn.pos = buffer.readBlockPos();
        return (T) toReturn;
    }

    @Override
    public <T extends ServerPacket> void handle(T packet, Supplier<NetworkEvent.Context> contextSupplier) {
        this.pos = ((OnButtonPressPacket) packet).pos;
        super. handle(packet, contextSupplier);
    }

    @Override
    protected void run(ServerPlayer serverPlayer, int i) {
        this.run.accept(i, serverPlayer, this.pos);
    }
}
