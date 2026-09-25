package net.mofusya.mechanical_ageing.recipes.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mofusya.mechanical_ageing.matter.LazyMatterStack;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.MAgRecipe;

public class MixingRecipe extends MAgRecipe {

    private final Ingredient itemIngredient;
    private final LazyMatterStack firstIngredient;
    private final LazyMatterStack secondIngredient;
    private final ItemStack itemResult;
    private final LazyMatterStack matterResult;

    public MixingRecipe(ResourceLocation id, Ingredient itemIngredient, LazyMatterStack firstIngredient, LazyMatterStack secondIngredient, ItemStack itemResult, LazyMatterStack matterResult) {
        super(id, Serializer.INSTANCE, Type.INSTANCE);
        this.itemIngredient = itemIngredient;
        this.firstIngredient = firstIngredient;
        this.secondIngredient = secondIngredient;
        this.itemResult = itemResult;
        this.matterResult = matterResult;
    }

    @Override
    public boolean matches(MAgContainer container, Level level) {
        var matterStacks = container.getMatters();
        if (matterStacks == null) return false;

        return test(this.getItemIngredient(), container.getItem(0)) &&
                test(this.getFirstIngredient(), matterStacks[0]) &&
                test(this.getSecondIngredient(), matterStacks[1]);
    }

    public MatterStack getFirstIngredient() {
        return firstIngredient.get();
    }

    public Ingredient getItemIngredient() {
        return itemIngredient;
    }

    public ItemStack getItemResult() {
        return itemResult;
    }

    public MatterStack getMatterResult() {
        return matterResult.get();
    }

    public MatterStack getSecondIngredient() {
        return secondIngredient.get();
    }

    public enum Type implements RecipeType<MixingRecipe> {
        INSTANCE
    }

    public enum Serializer implements RecipeSerializer<MixingRecipe> {
        INSTANCE;

        @Override
        public MixingRecipe fromJson(ResourceLocation id, JsonObject json) {
            return new MixingRecipe(id,
                    readIngredient(json, "itemIngredient", true),
                    readMatter(json, "firstIngredient", true),
                    readMatter(json, "secondIngredient", true),
                    readItem(json, "itemResult", true),
                    readMatter(json, "matterResult", true));
        }

        @Override
        public MixingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new MixingRecipe(id,
                    readIngredient(buf),
                    readMatter(buf),
                    readMatter(buf),
                    readItem(buf),
                    readMatter(buf));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MixingRecipe recipe) {
            writeToBuf(buf, recipe.itemIngredient);
            writeToBuf(buf, recipe.firstIngredient);
            writeToBuf(buf, recipe.secondIngredient);
            writeToBuf(buf, recipe.itemResult);
            writeToBuf(buf, recipe.matterResult);
        }
    }
}
