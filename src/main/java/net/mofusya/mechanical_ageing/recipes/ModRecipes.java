package net.mofusya.mechanical_ageing.recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.recipes.recipe.FuelRecipe;
import net.mofusya.mechanical_ageing.recipes.recipe.TriDimCraftingRecipe;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MechanicalAgeing.MOD_ID);

    public static final RegistryObject<RecipeSerializer<TriDimCraftingRecipe>> TRI_DIM_CRAFTING_TABLE =
            SERIALIZERS.register("tri_dimensional_crafting", () -> TriDimCraftingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<FuelRecipe>> FUEL =
            SERIALIZERS.register("fuel", () -> FuelRecipe.Serializer.INSTANCE);
}
