package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.MechanicalAgeing;

public enum BgTileType implements IBgTileType{
    VANILLA("vanilla_like")
    ;

    private final ResourceLocation id;

    BgTileType(String id){
        this(new ResourceLocation(MechanicalAgeing.MOD_ID, id));
    }

    BgTileType(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }
}
