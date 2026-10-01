package net.mofusya.mechanical_ageing.cables;

import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;

public class CableNetworkRec<TYPE extends ICableType<?>> extends ArrayList<CableNetwork<TYPE>> {

    private final TYPE type;

    public void tick(ServerLevel server){
        for (CableNetwork<TYPE> network : this) {
            network.tick(server);
        }
    }

    public CableNetworkRec(TYPE type) {
        super();
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }
}
