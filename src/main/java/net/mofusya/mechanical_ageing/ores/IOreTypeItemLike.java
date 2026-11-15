package net.mofusya.mechanical_ageing.ores;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IOreTypeItemLike {

    @NotNull
    default MutableComponent getName(ResourceLocation id) {
        MutableComponent name = Component.empty();

        if (this.isMultiversal()) add(name, Component.translatable("oretype." + C.MOD_ID + ".type.multiversal"));
        if (this.getItemTypePrefix() != null) add(name, Component.translatable("oretype." + C.MOD_ID + ".type." + this.getItemTypePrefix()));
        add(name, Component.translatable("oretype." + id.getNamespace() + "." + this.getOreTypeId()));
        if (this.getItemTypeSuffix() != null) add(name, Component.translatable("oretype." + C.MOD_ID + ".type." + this.getItemTypeSuffix()));

        return name;
    }

    static void add(MutableComponent component, Component add){
        if (!component.equals(Component.empty())){
            component.append(" ");
        }
        component.append(add);
    }

    String getOreTypeId();

    @Nullable String getItemTypePrefix();

    @Nullable String getItemTypeSuffix();

    boolean isMultiversal();
}
