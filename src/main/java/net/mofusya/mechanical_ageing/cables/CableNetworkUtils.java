package net.mofusya.mechanical_ageing.cables;

import io.netty.handler.ssl.util.SimpleKeyManagerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.cables.saveddata.CableNetworkSavedData;
import org.jetbrains.annotations.Nullable;

import java.security.InvalidParameterException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CableNetworkUtils {

    public static <TYPE extends ICableType<?>> void onPlaceCable(TYPE type, ServerLevel server, BlockPos pos) {
        CableNetworkRec<TYPE> networkRec = getNetworkRec(type, server);

        List<CableNetwork<TYPE>> touchingNetworks = getTouchingNetworks(networkRec, pos);
        if (touchingNetworks.isEmpty()) {
            CableNetwork<TYPE> newNetwork = new CableNetwork<>(type, pos);
            newNetwork.update();
            networkRec.add(newNetwork);
        } else if (touchingNetworks.size() == 1) {
            touchingNetworks.get(0).update(pos);
            if (touchingNetworks.get(0).getCablePos().contains(pos)){
                CableNetwork<TYPE> newNetwork = new CableNetwork<>(type, pos);
                newNetwork.update();
                networkRec.add(newNetwork);
            }
        } else {
            for (int i = 0; i < touchingNetworks.size(); i++) {
                if (i == 0) {
                    CableNetwork<TYPE> touchingNetwork = touchingNetworks.get(i);
                    touchingNetwork.update();
                }
            }
        }
    }

    public static <TYPE extends ICableType<?>> void onRemoveCable(TYPE type, ServerLevel server, BlockPos pos) {
        CableNetworkRec<TYPE> networkRec = getNetworkRec(type, server);
        @Nullable
        CableNetwork<TYPE> network = getNetworkThatContains(networkRec, pos);
        if (network == null) throw new InvalidParameterException("The pos is not included in any network.");

        Queue<BlockPos> queue = new ArrayDeque<>();
        for (Direction direction : Direction.values()) {
            BlockPos nextPos = pos.relative(direction);
            if (network.getCablePos().contains(nextPos)){
                queue.add(nextPos);
            }
        }
        networkRec.remove(network);

        while (!queue.isEmpty()){
            BlockPos checkPos = queue.poll();
            if (hasNetworkThatContains(networkRec, checkPos)) continue;

            CableNetwork<TYPE> newNetwork = new CableNetwork<>(type, checkPos);
            newNetwork.update();
            networkRec.add(newNetwork);
        }
    }

    @Nullable
    public static <TYPE extends ICableType<?>> CableNetwork<TYPE> getNetworkThatContains(CableNetworkRec<TYPE> networkRec, BlockPos pos){
        for (CableNetwork<TYPE> network : networkRec) {
            if (network.getCablePos().contains(pos)){
                return network;
            }
        }
        return null;
    }

    public static <TYPE extends ICableType<?>> boolean hasNetworkThatContains(CableNetworkRec<TYPE> networkRec, BlockPos pos){
        for (CableNetwork<TYPE> network : networkRec) {
            if (network.getCablePos().contains(pos)){
                return true;
            }
        }
        return false;
    }

    public static <TYPE extends ICableType<?>> List<CableNetwork<TYPE>> getTouchingNetworks(CableNetworkRec<TYPE> networkRec, BlockPos pos) {
        List<CableNetwork<TYPE>> touchingNetworks = new ArrayList<>();

        for (CableNetwork<TYPE> network : networkRec) {
            if (isTouchingNetwork(network, pos)) {
                touchingNetworks.add(network);
            }
        }

        return touchingNetworks;
    }

    public static boolean isTouchingNetwork(CableNetwork<?> network, BlockPos pos) {
        for (Direction value : Direction.values()) {
            if (network.getCablePos().contains(pos.relative(value))) {
                return true;
            }
        }
        return false;
    }

    public static <TYPE extends ICableType<?>> CableNetworkRec<TYPE> getNetworkRec(TYPE type, ServerLevel server) {
        CableNetworkSavedData savedData = CableNetworkSavedData.get(server);
        return (CableNetworkRec<TYPE>) savedData.getNetworkRecs().get(type.getIdentifier());
    }
}
