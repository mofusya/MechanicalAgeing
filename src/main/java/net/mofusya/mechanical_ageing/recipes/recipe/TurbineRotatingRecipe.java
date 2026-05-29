package net.mofusya.mechanical_ageing.recipes.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mofusya.mechanical_ageing.matter.LazyMatterType;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.MAgRecipe;
import net.mofusya.ornatelib.lang.SeptiLong;

public class TurbineRotatingRecipe extends MAgRecipe {
    protected final LazyMatterType ingredient;
    protected final SeptiLong result;

    public TurbineRotatingRecipe(ResourceLocation id, LazyMatterType ingredient, SeptiLong result) {
        super(id, Serializer.INSTANCE, Type.INSTANCE);
        this.ingredient = ingredient;
        this.result = result;
    }

    public MatterStack getIngredient() {
        return this.ingredient.get();
    }

    public MatterStack getResult() {
        return new MatterStack(MAgMatterTypes.ROTATION, this.result.copy());
    }

    @Override
    public boolean matches(MAgContainer container, Level level) {
        MatterStack[] matterStacks = container.getMatters();
        if (matterStacks == null) return false;

        return test(this.getIngredient(), matterStacks[0]);
    }

    public enum Type implements RecipeType<TurbineRotatingRecipe> {
        INSTANCE;
    }

    public enum Serializer implements RecipeSerializer<TurbineRotatingRecipe> {
        INSTANCE;

        @Override
        public TurbineRotatingRecipe fromJson(ResourceLocation id, JsonObject json) {
            return new TurbineRotatingRecipe(id,
                    readMatterType(json, "ingredient"),
                    readSeptiLong(json, "result")
            );
        }

        @Override
        public TurbineRotatingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new TurbineRotatingRecipe(id,
                    readMatterType(buf),
                    readSeptiLong(buf)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, TurbineRotatingRecipe recipe) {
            writeToBuf(buf, recipe.ingredient);
            writeToBuf(buf, recipe.result.copy());
        }
    }
}
