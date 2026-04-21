package net.mofusya.mechanical_ageing.recipes;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.recipes.recipe.TriDimCraftingRecipe;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MechanicalAgeing.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, MechanicalAgeing.MOD_ID);

    public static final RecipeObject<TriDimCraftingRecipe> TRI_DIM_CRAFTING_TABLE = create("tri_dimensional_crafting", TriDimCraftingRecipe.Serializer::new);

    public static <T extends Recipe<?>> RecipeObject<T> create(String id, Supplier<RecipeSerializer<T>> serializer) {
        return new RecipeObject<>(SERIALIZERS.register(id, serializer), TYPES.register(id, () -> new RecipeType<>() {
        }));
    }
}
