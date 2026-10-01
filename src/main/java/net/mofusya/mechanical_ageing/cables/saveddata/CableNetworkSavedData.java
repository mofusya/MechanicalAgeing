package net.mofusya.mechanical_ageing.cables.saveddata;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.cables.CableNetwork;
import net.mofusya.mechanical_ageing.cables.CableNetworkRec;
import net.mofusya.mechanical_ageing.cables.CableTypeManager;
import net.mofusya.mechanical_ageing.cables.ICableType;
import net.mofusya.ornatelib.util.ArrayMap;

public class CableNetworkSavedData extends SavedData {

    private final ArrayMap<ResourceLocation, CableNetworkRec<?>> networkRecs;

    public CableNetworkSavedData() {
        this.networkRecs = new ArrayMap<>();
    }

    public static CableNetworkSavedData get(ServerLevel server) {
        CableNetworkSavedData savedData = server.getDataStorage().computeIfAbsent(CableNetworkSavedData::load, CableNetworkSavedData::new, C.MOD_ID + "_cable_network_recs");
        checkAndFixMissingRecs(savedData);
        return savedData;
    }

    public static CableNetworkSavedData load(CompoundTag tag) {
        CableNetworkSavedData savedData = new CableNetworkSavedData();
        ListTag recsTag = tag.getList(C.NETWORK_RECS, Tag.TAG_COMPOUND);
        for (Tag baseRecTag : recsTag) {
            CompoundTag recTag = (CompoundTag) baseRecTag;
            ResourceLocation cableTypeId = new ResourceLocation(recTag.getString(C.NETWORK_TYPE));
            ICableType<?> cableType = CableTypeManager.get().get(cableTypeId);

            CableNetworkRec<ICableType<?>> rec = new CableNetworkRec<>(cableType);

            ListTag networkBasePosListTag = recTag.getList(C.NETWORKS_BASE_POS, Tag.TAG_COMPOUND);
            for (Tag baseNetworkPosTag : networkBasePosListTag) {
                CompoundTag networkBasePosTag = (CompoundTag) baseNetworkPosTag;
                rec.add(new CableNetwork<>(cableType, NbtUtils.readBlockPos(networkBasePosTag)));
            }

            savedData.getNetworkRecs().put(cableTypeId, rec);
        }
        return savedData;
    }

    public static void checkAndFixMissingRecs(CableNetworkSavedData savedData) {
        CableTypeManager.get().forEach((cableTypeId, cableType) -> {
            boolean contains = savedData.getNetworkRecs().containsKey(cableTypeId);
            if (!contains) {
                savedData.getNetworkRecs().put(cableTypeId, new CableNetworkRec<>(cableType));
            }
        });
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag recsTag = new ListTag();
        for (CableNetworkRec<?> networkRec : this.networkRecs.getValues()) {
            CompoundTag networkRecTag = new CompoundTag();
            networkRecTag.putString(C.NETWORK_TYPE, networkRec.getType().getIdentifier().toString());

            ListTag networkBasePosListTag = new ListTag();
            for (CableNetwork<?> network : networkRec) {
                if (network.getCablePos().isEmpty()) {
                    networkBasePosListTag.add(NbtUtils.writeBlockPos(network.getCablePos().get(0)));
                }
            }
            networkRecTag.put(C.NETWORKS_BASE_POS, networkBasePosListTag);
            recsTag.add(networkRecTag);
        }

        tag.put(C.NETWORK_RECS, recsTag);
        return tag;
    }

    public ArrayMap<ResourceLocation, CableNetworkRec<?>> getNetworkRecs() {
        return networkRecs;
    }
}
