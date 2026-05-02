package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public interface IBgTileType {
    ResourceLocation getId();
    @Nullable
    ChatFormatting getFormat();
}
