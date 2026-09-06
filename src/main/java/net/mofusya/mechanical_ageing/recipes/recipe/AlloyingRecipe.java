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
import net.mofusya.ornatelib.lang.UnLong;

public class AlloyingRecipe extends MAgRecipe {
    private final Ingredient ingredient;
    private final Ingredient subIngredient;
    private final UnLong heatAmount;
    private final int smeltTime;
    private final ItemStack result;

    public AlloyingRecipe(ResourceLocation id, Ingredient ingredient, Ingredient subIngredient, UnLong heatAmount, int smeltTime, ItemStack result) {
        super(id, Serializer.INSTANCE, Type.INSTANCE);
        this.ingredient = ingredient;
        this.subIngredient = subIngredient;
        this.heatAmount = heatAmount;
        this.smeltTime = smeltTime;
        this.result = result;
    }

    @Override
    public boolean matches(MAgContainer container, Level level) {
        var matterStacks = container.getMatters();
        if (matterStacks == null) return false;

        return this.getIngredient().test(container.getItem(0)) && this.getSubIngredient().test(container.getItem(1)) && test(this.getHeat(), matterStacks[0]);
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public Ingredient getSubIngredient() {
        return this.subIngredient;
    }

    public MatterStack getHeat() {
        return new MatterStack(MAgMatterTypes.HEAT, this.heatAmount);
    }

    public int getSmeltTime() {
        return this.smeltTime;
    }

    public ItemStack getResult() {
        return this.result.copy();
    }

    public enum Type implements RecipeType<AlloyingRecipe> {
        INSTANCE;
    }

    public enum Serializer implements RecipeSerializer<AlloyingRecipe> {
        INSTANCE;

        @Override
        public AlloyingRecipe fromJson(ResourceLocation id, JsonObject json) {
            return new AlloyingRecipe(id,
                    readIngredient(json, "ingredient"),
                    readIngredient(json, "subIngredient"),
                    readUnLong(json, "heatAmount"),
                    readInt(json, "smeltTime"),
                    readItem(json, "result")
            );
        }

        @Override
        public AlloyingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new AlloyingRecipe(id,
                    readIngredient(buf),
                    readIngredient(buf),
                    readUnLong(buf),
                    buf.readInt(),
                    readItem(buf)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AlloyingRecipe recipe) {
            writeToBuf(buf, recipe.getIngredient());
            writeToBuf(buf, recipe.getSubIngredient());
            writeToBuf(buf, recipe.heatAmount);
            buf.writeInt(recipe.getSmeltTime());
            writeToBuf(buf, recipe.getResult());
        }
    }
}
