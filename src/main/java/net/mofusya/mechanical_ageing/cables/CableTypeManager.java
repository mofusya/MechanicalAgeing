package net.mofusya.mechanical_ageing.cables;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class CableTypeManager {
    private static final Map<ResourceLocation, ICableType<?>> CABLE_TYPES = new HashMap<>();

    public static void register(CableTypeRegister register){
        CABLE_TYPES.putAll(register.get());
    }

    public static Map<ResourceLocation, ICableType<?>> get(){
        return new HashMap<>(CABLE_TYPES);
    }
}
