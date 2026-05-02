package net.mofusya.mechanical_ageing.recipes.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.MAgRecipe;
import net.mofusya.ornatelib.lang.SeptiLong;

public class FuelRecipe extends MAgRecipe {
    private final Ingredient ingredient;
    private final SeptiLong resultAmount;

    public FuelRecipe(ResourceLocation id, Ingredient ingredient, SeptiLong resultAmount) {
        super(id, Serializer.INSTANCE, Type.INSTANCE);
        this.ingredient = ingredient;
        this.resultAmount = resultAmount;
    }

    @Override
    public boolean matches(MAgContainer container, Level level) {
        return this.ingredient.test(container.getItem(0));
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public SeptiLong getResultAmount() {
        return this.resultAmount;
    }

    public MatterStack getResult() {
        return new MatterStack(MAgMatterTypes.FUEL, this.getResultAmount());
    }

    public enum Type implements RecipeType<FuelRecipe> {INSTANCE}

    public enum Serializer implements RecipeSerializer<FuelRecipe> {
        INSTANCE;

        @Override
        public FuelRecipe fromJson(ResourceLocation id, JsonObject json) {
            return new FuelRecipe(id,
                    readIngredient(json, "fuel"),
                    readSeptiLong(json, "resultAmount")
            );
        }

        @Override
        public FuelRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new FuelRecipe(id,
                    readIngredient(buf),
                    readSeptiLong(buf)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, FuelRecipe recipe) {
            writeToBuf(buf, recipe.getIngredient());
            writeToBuf(buf, recipe.getResultAmount());
        }
    }
}
