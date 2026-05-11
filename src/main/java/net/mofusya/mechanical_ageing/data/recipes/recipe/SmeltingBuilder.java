package net.mofusya.mechanical_ageing.data.recipes.recipe;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.mofusya.mechanical_ageing.data.recipes.MAgFinishedRecipe;
import net.mofusya.mechanical_ageing.data.recipes.MAgRecipeBuilder;
import net.mofusya.mechanical_ageing.recipes.recipe.SmeltingRecipe;
import net.mofusya.ornatelib.lang.SeptiLongValue;

public class SmeltingBuilder extends MAgRecipeBuilder {

    private final Ingredient ingredient;
    private final SeptiLongValue heatAmountDigit;
    private final int heatAmountMultiplier;
    private final int smeltTime;
    private final ItemStack result;

    public SmeltingBuilder(String name, Ingredient ingredient, SeptiLongValue heatAmountDigit, int heatAmountMultiplier, int smeltTime, ItemStack result) {
        super(name);
        this.ingredient = ingredient;
        this.heatAmountDigit = heatAmountDigit;
        this.heatAmountMultiplier = heatAmountMultiplier;
        this.smeltTime = smeltTime;
        this.result = result;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public MAgFinishedRecipe getRecipeJson(String nane, Advancement.Builder advancement, ResourceLocation advancementId) {
        return new Recipe(nane, advancement, advancementId, this.ingredient, this.heatAmountDigit, this.heatAmountMultiplier, this.smeltTime, this.result);
    }

    public static class Recipe extends MAgFinishedRecipe {
        private final Ingredient ingredient;
        private final SeptiLongValue heatAmountDigit;
        private final int heatAmountMultiplier;
        private final int smeltTime;
        private final ItemStack result;

        protected Recipe(String name, Advancement.Builder advancement, ResourceLocation advancementId, Ingredient ingredient, SeptiLongValue heatAmountDigit, int heatAmountMultiplier, int smeltTime, ItemStack result) {
            super(name, advancement, advancementId);
            this.ingredient = ingredient;
            this.heatAmountDigit = heatAmountDigit;
            this.heatAmountMultiplier = heatAmountMultiplier;
            this.smeltTime = smeltTime;
            this.result = result;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            //Writing ingredient
            json.add("ingredient", this.ingredient.toJson());
            //Writing heatAmount
            write(json, "heatAmount", this.heatAmountDigit, this.heatAmountMultiplier);
            //Writing smeltTime
            json.addProperty("smeltTime", smeltTime);
            //Writing result
            write(json, "result", this.result);
        }

        @Override
        public RecipeSerializer<?> getType() {
            return SmeltingRecipe.Serializer.INSTANCE;
        }
    }
}
