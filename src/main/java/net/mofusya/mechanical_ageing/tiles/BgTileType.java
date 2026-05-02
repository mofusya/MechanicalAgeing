package net.mofusya.mechanical_ageing.tiles;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.machinetiles.IBgTileType;
import org.jetbrains.annotations.Nullable;

public enum BgTileType implements IBgTileType {
    VANILLA("vanilla_like"),
    BRICK("brick", ChatFormatting.WHITE);

    private final ResourceLocation id;
    @Nullable
    private final ChatFormatting format;

    BgTileType(String id) {
        this(id, null);
    }

    BgTileType(ResourceLocation id) {
        this(id, null);
    }

    BgTileType(String id, @Nullable ChatFormatting format) {
        this(new ResourceLocation(MechanicalAgeing.MOD_ID, id), format);
    }

    BgTileType(ResourceLocation id, @Nullable ChatFormatting format) {
        this.id = id;
        this.format = format;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nullable
    @Override
    public ChatFormatting getFormat() {
        return this.format;
    }
}
