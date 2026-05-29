package net.mofusya.mechanical_ageing.recipes;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.recipes.recipe.*;

public class MAgRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MAg.MOD_ID);

    public static final RegistryObject<RecipeSerializer<TriDimCraftingRecipe>> TRI_DIM_CRAFTING_TABLE =
            SERIALIZERS.register("tri_dimensional_crafting", () -> TriDimCraftingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<FuelRecipe>> FUEL =
            SERIALIZERS.register("fuel", () -> FuelRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<MatterBurningRecipe>> MATTER_BURNING =
            SERIALIZERS.register("matter_burning", () -> MatterBurningRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SmeltingRecipe>> SMELTING =
            SERIALIZERS.register("smelting", () -> SmeltingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<AlloyingRecipe>> ALLOYING =
            SERIALIZERS.register("alloying", () -> AlloyingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<HeatingRecipe>> HEATING =
            SERIALIZERS.register("heating", () -> HeatingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<TurbineRotatingRecipe>> TURBINE_ROTATING =
            SERIALIZERS.register("turbine_rotating", () -> TurbineRotatingRecipe.Serializer.INSTANCE);
}
