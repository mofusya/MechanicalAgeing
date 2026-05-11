package net.mofusya.mechanical_ageing.data.recipes.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.mofusya.mechanical_ageing.data.recipes.MAgFinishedRecipe;
import net.mofusya.mechanical_ageing.data.recipes.MAgRecipeBuilder;
import net.mofusya.mechanical_ageing.recipes.recipe.TriDimCraftingRecipe;
import net.mofusya.mechanical_ageing.util.ArrayMap;

public class TriDimensionalCraftingBuilder extends MAgRecipeBuilder {

    private final String pattern1;
    private final String pattern2;
    private final String pattern3;
    private final ArrayMap<Character, Ingredient> keys = new ArrayMap<>();
    private final ItemStack result;

    public TriDimensionalCraftingBuilder(String name, String pattern1, String pattern2, String pattern3, ItemStack result) {
        super(name);
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;
        this.pattern3 = pattern3;
        this.result = result;
    }

    public TriDimensionalCraftingBuilder key(Character key, Ingredient ingredient) {
        this.keys.put(key, ingredient);
        return this;
    }

    @Override
    public Item getResult() {
        return result.getItem();
    }

    @Override
    protected MAgFinishedRecipe getRecipeJson(String name, Advancement.Builder advancement, ResourceLocation advancementId) {
        return new Recipe(name, advancement, advancementId, this.pattern1, this.pattern2, this.pattern3, this.keys, this.result);
    }

    public static class Recipe extends MAgFinishedRecipe {

        private final String pattern1;
        private final String pattern2;
        private final String pattern3;
        private final ArrayMap<Character, Ingredient> keys;
        private final ItemStack result;

        protected Recipe(String name, Advancement.Builder advancement, ResourceLocation advancementId, String pattern1, String pattern2, String pattern3, ArrayMap<Character, Ingredient> keys, ItemStack result) {
            super(name, advancement, advancementId);
            this.pattern1 = pattern1;
            this.pattern2 = pattern2;
            this.pattern3 = pattern3;
            this.keys = keys;
            this.result = result;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            //Writing patterns
            JsonArray pattern = new JsonArray();
            pattern.add(this.pattern1);
            pattern.add(this.pattern2);
            pattern.add(this.pattern3);
            json.add("pattern", pattern);
            //Writing keys
            JsonObject key = new JsonObject();
            for (var chara : this.keys.getKeys()) {
                key.add(String.valueOf(chara), this.keys.get(chara).toJson());
            }
            json.add("key", key);
            //Writing result
            write(json, "result", this.result);
        }

        @Override
        public RecipeSerializer<?> getType() {
            return TriDimCraftingRecipe.Serializer.INSTANCE;
        }
    }
}
