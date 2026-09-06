package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class MatterRegister {
    private final Map<ResourceLocation, MatterType> MATTERS = new HashMap<>();

    public MatterType create(ResourceLocation id, MatterType matterType) {
        matterType.setId(id);
        MATTERS.put(id, matterType);
        return matterType;
    }

    public Map<ResourceLocation, MatterType> get(){
        return new HashMap<>(MATTERS);
    }

    public void register(){
        MatterManager.register(this);
    }
}
