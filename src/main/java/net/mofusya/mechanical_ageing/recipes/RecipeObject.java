package net.mofusya.mechanical_ageing.recipes;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegistryObject;

public record RecipeObject<T extends Recipe<?>>(RegistryObject<RecipeSerializer<T>> serializer, RegistryObject<RecipeType<T>> type) {
    public RecipeSerializer<T> getSerializer(){
        return this.serializer().get();
    }

    public RecipeType<T> getType() {
        return this.type().get();
    }
}
