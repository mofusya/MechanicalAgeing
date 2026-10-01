package net.mofusya.mechanical_ageing.cables;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class CableTypeRegister {
    private final Map<ResourceLocation, ICableType<?>> cableTypes;

    private final String modId;

    public CableTypeRegister(String modId) {
        this.modId = modId;
        this.cableTypes = new HashMap<>();
    }

    public <T> ICableType<T> create(ICableType<T> cableType) {
        cableTypes.put(cableType.getIdentifier(), cableType);
        return cableType;
    }

    public Map<ResourceLocation, ICableType<?>> get() {
        return new HashMap<>(cableTypes);
    }

    public void register() {
        CableTypeManager.register(this);
    }
}
