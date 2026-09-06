package net.mofusya.mechanical_ageing.recipes.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mofusya.mechanical_ageing.matter.LazyMatterStack;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.MAgRecipe;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.UnLong;

public class HeatingRecipe extends MAgRecipe {
    private final LazyMatterStack ingredient;
    private final UnLong heatAmount;
    private final LazyMatterStack result;

    public HeatingRecipe(ResourceLocation id, LazyMatterStack ingredient, UnLong heatAmount, LazyMatterStack result) {
        super(id, Serializer.INSTANCE, Type.INSTANCE);
        this.ingredient = ingredient;
        this.heatAmount = heatAmount;
        this.result = result;
    }

    @Override
    public boolean matches(MAgContainer container, Level level) {
        var matterStacks = container.getMatters();
        if (matterStacks == null) return false;

        return test(this.getHeat(), matterStacks[0]) && test(this.getIngredient(), matterStacks[1]);
    }

    public MatterStack getIngredient() {
        return this.ingredient.get();
    }

    public MatterStack getHeat() {
        return new MatterStack(MAgMatterTypes.HEAT, this.heatAmount);
    }

    public MatterStack getResult() {
        return this.result.get();
    }

    public enum Type implements RecipeType<HeatingRecipe> {
        INSTANCE;
    }

    public enum Serializer implements RecipeSerializer<HeatingRecipe> {
        INSTANCE;

        @Override
        public HeatingRecipe fromJson(ResourceLocation id, JsonObject json) {
            return new HeatingRecipe(id,
                    readMatter(json, "ingredient"),
                    readUnLong(json, "heatAmount"),
                    readMatter(json, "result")
            );
        }

        @Override
        public HeatingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new HeatingRecipe(id,
                    readMatter(buf),
                    readUnLong(buf),
                    readMatter(buf)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, HeatingRecipe recipe) {
            writeToBuf(buf, recipe.ingredient);
            writeToBuf(buf, recipe.heatAmount.copy());
            writeToBuf(buf, recipe.result);
        }
    }
}
