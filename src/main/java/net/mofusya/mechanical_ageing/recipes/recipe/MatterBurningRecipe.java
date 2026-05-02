package net.mofusya.mechanical_ageing.recipes.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mofusya.mechanical_ageing.matter.LazyMatterStack;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.MAgRecipe;

public class MatterBurningRecipe extends MAgRecipe {
    private final LazyMatterStack ingredient;
    private final LazyMatterStack result;

    public MatterBurningRecipe(ResourceLocation id, LazyMatterStack ingredient, LazyMatterStack result) {
        super(id, Serializer.INSTANCE, Type.INSTANCE);
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    public boolean matches(MAgContainer container, Level level) {
        var matterStacks = container.getMatters();
        if (matterStacks == null) return false;

        return test(this.getIngredient(), matterStacks[0]);
    }

    public MatterStack getIngredient() {
        return this.ingredient.get();
    }

    public MatterStack getResult() {
        return this.result.get();
    }

    public enum Type implements RecipeType<MatterBurningRecipe> {
        INSTANCE;
    }

    public enum Serializer implements RecipeSerializer<MatterBurningRecipe> {
        INSTANCE;

        @Override
        public MatterBurningRecipe fromJson(ResourceLocation id, JsonObject json) {
            return new MatterBurningRecipe(id, readMatter(json, "ingredient"), readMatter(json, "result"));
        }

        @Override
        public MatterBurningRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new MatterBurningRecipe(id, readMatter(buf), readMatter(buf));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MatterBurningRecipe recipe) {
            writeToBuf(buf, recipe.ingredient);
            writeToBuf(buf, recipe.result);
        }
    }
}
