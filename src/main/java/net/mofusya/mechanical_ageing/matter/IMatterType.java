package net.mofusya.mechanical_ageing.matter;

import net.kairayk.yklibrary.util.VectorColor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
     * A helper getter for this translation key.
     * @return A new {@code String} translation key.
     */
    default String getTranslationKey() {
        return "matter." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    /**
     * A helper getter for this translated component.
     * @return A new {@code MutableComponent} translated component.
     */
    default MutableComponent getTranslatedComponent(){
        return Component.translatable(this.getTranslationKey());
    }

    /**
     * A helper method to determine if this is the same as {@code matterType}.
     *
     * @param matterType The {@code IMatterType} to compare.
     * @return If the {@code matterType} is the same as this.
     */
    default boolean is(IMatterType matterType) {
        return this.getId().equals(matterType.getId());
    }
}
