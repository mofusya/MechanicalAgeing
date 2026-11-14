package net.mofusya.mechanical_ageing.ores;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.ornatelib.registries.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OreTypeRegistry{
    private final Map<ResourceLocation, OreType> R;

    public OreTypeRegistry() {
        this.R = new HashMap<>();
    }

    public Map<ResourceLocation, OreType> getEntries() {
        return Map.copyOf(this.R);
    }

    public void register(OreTypeRegister register) {
        this.R.putAll(register.getEntries());
    }
}