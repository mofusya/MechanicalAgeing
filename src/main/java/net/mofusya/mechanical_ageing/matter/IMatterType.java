package net.mofusya.mechanical_ageing.matter;

import net.kairayk.yklibrary.util.VectorColor;
import net.minecraft.resources.ResourceLocation;

public interface IMatterType {
    /**
     * A getter for this registered id.
     */
    ResourceLocation getId();

    /**
     * A getter for this rendering color.
     */
    VectorColor getColor();

    /**
     * A helper method to determine if this is the same as {@code matterType}.
     *
     * @param matterType The {@code IMatterType} to compare.
     * @return If the {@code matterType} is the same as this.
     */
    default boolean is(IMatterType matterType){
        return this.getId().equals(matterType.getId());
    }
}
