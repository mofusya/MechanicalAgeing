package net.mofusya.mechanical_ageing.matter;

import net.kairayk.yklibrary.util.VectorColor;
import net.minecraft.resources.ResourceLocation;

public final class MatterType implements IMatterType {
    private final ResourceLocation id;
    private final VectorColor color;

    public MatterType(ResourceLocation id, VectorColor color) {
        this.id = id;
        this.color = color;
    }

    /**
     * A getter for this registered id.
     * @return This {@code id}.
     */
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    /**
     * A getter for this rendering color.
     * @return This {@code color}.
     */
    @Override
    public VectorColor getColor() {
        return this.color;
    }
}
