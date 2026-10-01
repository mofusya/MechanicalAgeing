package net.mofusya.mechanical_ageing.cables;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mofusya.mechanical_ageing.cables.saveddata.CableNetworkSavedData;

@Mod.EventBusSubscriber
public class CableNetworkEvent {

    @SubscribeEvent
    public static void onServerTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel server)) return;

        CableNetworkSavedData savedData = CableNetworkSavedData.get(server);

        for (CableNetworkRec<?> networkRec : savedData.getNetworkRecs().getValues()) {
            networkRec.tick(server);
        }
    }

    //todo: Make the networkRecs rebuild if any block is changed around it. (BlockEvent.EntityPlaceEvent, BlockEvent.BreakEvent)
}
