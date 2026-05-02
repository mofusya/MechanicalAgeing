package net.mofusya.mechanical_ageing.recipes.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.MAgRecipe;
import net.mofusya.ornatelib.lang.SeptiLong;

public class SmeltingRecipe extends MAgRecipe {
    private final Ingredient ingredient;
    private final SeptiLong heatAmount;
    private final ItemStack result;


    public SmeltingRecipe(ResourceLocation id, Ingredient ingredient, SeptiLong heatAmount, ItemStack result) {
        super(id, Serializer.INSTANCE, Type.INSTANCE);
        this.ingredient = ingredient;
        this.heatAmount = heatAmount;
        this.result = result;
    }

    @Override
    public boolean matches(MAgContainer container, Level level) {
        var matterStacks = container.getMatters();
        if (matterStacks == null) return false;

        return this.getIngredient().test(container.getItem(0)) && test(this.getHeat(), matterStacks[0]);
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public MatterStack getHeat() {
        return new MatterStack(MAgMatterTypes.HEAT, this.heatAmount);
    }

    public ItemStack getResult() {
        return this.result;
    }

    public enum Type implements RecipeType<SmeltingRecipe> {
        INSTANCE;
    }

    public enum Serializer implements RecipeSerializer<SmeltingRecipe> {
        INSTANCE;

        @Override
        public SmeltingRecipe fromJson(ResourceLocation id, JsonObject json) {
            return new SmeltingRecipe(id,
                    readIngredient(json, "ingredient"),
                    readSeptiLong(json, "heatAmount"),
                    readItem(json, "result")
            );
        }

        @Override
        public SmeltingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new SmeltingRecipe(id,
                    readIngredient(buf),
                    readSeptiLong(buf),
                    readItem(buf)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SmeltingRecipe recipe) {
            writeToBuf(buf, recipe.getIngredient());
            writeToBuf(buf, recipe.heatAmount);
            writeToBuf(buf, recipe.getResult());
        }
    }
}
