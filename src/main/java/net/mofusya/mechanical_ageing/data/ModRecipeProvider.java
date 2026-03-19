package net.mofusya.mechanical_ageing.data;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.metalset.MetalSet;
import net.mofusya.mechanical_ageing.metalset.ModMetalSet;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        for (MetalSet metalSet : ModMetalSet.METAL_SET.getEntries()) {
            metalSetRecipes(metalSet, writer);
        }
    }

    public static void metalSetRecipes(MetalSet metalSet, Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, metalSet.compressedBlock())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', metalSet.block())
                .unlockedBy(getHasName(metalSet.block()), inventoryTrigger(ItemPredicate.Builder.item().
                        of(metalSet.block()).build()))
                .save(writer, MechanicalAgeing.MOD_ID + ":" + getItemName(metalSet.compressedBlock()) + "_from_compressing");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, metalSet.block(), 9)
                .requires(metalSet.compressedBlock())
                .unlockedBy(getHasName(metalSet.compressedBlock()), inventoryTrigger(ItemPredicate.Builder.item().
                        of(metalSet.compressedBlock()).build()))
                .save(writer, MechanicalAgeing.MOD_ID + ":" + getItemName(metalSet.block()) + "_from_separating");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, metalSet.block())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', metalSet.ingot())
                .unlockedBy(getHasName(metalSet.ingot()), inventoryTrigger(ItemPredicate.Builder.item().
                        of(metalSet.ingot()).build()))
                .save(writer, MechanicalAgeing.MOD_ID + ":" + getItemName(metalSet.block()) + "_from_compressing");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, metalSet.ingot(), 9)
                .requires(metalSet.block())
                .unlockedBy(getHasName(metalSet.block()), inventoryTrigger(ItemPredicate.Builder.item().
                        of(metalSet.block()).build()))
                .save(writer, MechanicalAgeing.MOD_ID + ":" + getItemName(metalSet.ingot()) + "_from_separating");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, metalSet.ingot())
                .pattern("AA")
                .define('A', metalSet.chunk())
                .unlockedBy(getHasName(metalSet.chunk()), inventoryTrigger(ItemPredicate.Builder.item().
                        of(metalSet.chunk()).build()))
                .save(writer, MechanicalAgeing.MOD_ID + ":" + getItemName(metalSet.ingot()) + "_from_compressing");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, metalSet.chunk(), 2)
                .requires(metalSet.ingot())
                .unlockedBy(getHasName(metalSet.ingot()), inventoryTrigger(ItemPredicate.Builder.item().
                        of(metalSet.ingot()).build()))
                .save(writer, MechanicalAgeing.MOD_ID + ":" + getItemName(metalSet.chunk()) + "_from_separating");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, metalSet.chunk())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', metalSet.nugget())
                .unlockedBy(getHasName(metalSet.nugget()), inventoryTrigger(ItemPredicate.Builder.item().
                        of(metalSet.nugget()).build()))
                .save(writer, MechanicalAgeing.MOD_ID + ":" + getItemName(metalSet.chunk()) + "_from_compressing");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, metalSet.nugget(), 9)
                .requires(metalSet.chunk())
                .unlockedBy(getHasName(metalSet.chunk()), inventoryTrigger(ItemPredicate.Builder.item().
                        of(metalSet.chunk()).build()))
                .save(writer, MechanicalAgeing.MOD_ID + ":" + getItemName(metalSet.nugget()) + "_from_separating");

        final List<ItemLike> rawMaterial = List.of(metalSet.ore(), metalSet.deepslateOre(), metalSet.raw());

        oreSmelting(writer, rawMaterial, RecipeCategory.MISC, metalSet.chunk(), 0.25f, metalSet.getId());
        oreBlasting(writer, rawMaterial, RecipeCategory.MISC, metalSet.chunk(), 0.25f, metalSet.getId());
    }

    private static void oreSmelting(Consumer<FinishedRecipe> writer, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, String pGroup) {
        oreCooking(writer, RecipeSerializer.SMELTING_RECIPE, ingredients, category, result, experience, 200, pGroup, "_from_smelting");
    }

    private static void oreBlasting(Consumer<FinishedRecipe> writer, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, String pGroup) {
        oreCooking(writer, RecipeSerializer.BLASTING_RECIPE, ingredients, category, result, experience, 100, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> writer, RecipeSerializer<? extends AbstractCookingRecipe> cookingSerializer, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group, String recipeName) {
        for (ItemLike itemlike : ingredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), category, result, experience, cookingTime, cookingSerializer).group(group).unlockedBy(getHasName(itemlike), has(itemlike)).save(writer, MechanicalAgeing.MOD_ID + ":" + getItemName(result) + recipeName + "_" + getItemName(itemlike));
        }
    }
}
