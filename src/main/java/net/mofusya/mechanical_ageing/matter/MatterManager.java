package net.mofusya.mechanical_ageing.matter;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class MatterManager {
    private static final Map<ResourceLocation, MatterType> MATTERS = new HashMap<>();

    public static void register(MatterRegister registries){
        MATTERS.putAll(registries.get());
    }

    public static Map<ResourceLocation, MatterType> get(){
        return new HashMap<>(MatterManager.MATTERS);
    }
}
