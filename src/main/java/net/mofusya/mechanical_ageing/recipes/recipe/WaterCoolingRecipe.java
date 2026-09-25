package net.mofusya.mechanical_ageing.recipes.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mofusya.mechanical_ageing.matter.LazyMatterStack;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.MAgRecipe;
import net.mofusya.ornatelib.lang.UnLong;

public class WaterCoolingRecipe extends MAgRecipe {
    private final LazyMatterStack ingredient;
    private final UnLong waterAmount;
    private final int processingTick;
    private final ItemStack result;

    public WaterCoolingRecipe(ResourceLocation id, LazyMatterStack ingredient, UnLong waterAmount, int processingTick, ItemStack result) {
        super(id, Serializer.INSTANCE, Type.INSTANCE);
        this.ingredient = ingredient;
        this.waterAmount = waterAmount;
        this.processingTick = processingTick;
        this.result = result;
    }

    public MatterStack getIngredient() {
        return ingredient.get();
    }

    public int getProcessingTick() {
        return processingTick;
    }

    public ItemStack getResult() {
        return result.copy();
    }

    public MatterStack getWaterAmount() {
        return new MatterStack(MAgMatterTypes.WATER, this.waterAmount);
    }

    @Override
    public boolean matches(MAgContainer container, Level level) {
        var matters = container.getMatters();
        if (matters == null) return false;

        return test(this.getWaterAmount(), matters[0]) &&
                test(this.getIngredient(), matters[1]);
    }

    public enum Type implements RecipeType<WaterCoolingRecipe> {
        INSTANCE
    }

    public enum Serializer implements RecipeSerializer<WaterCoolingRecipe> {
        INSTANCE;

        @Override
        public WaterCoolingRecipe fromJson(ResourceLocation id, JsonObject json) {
            return new WaterCoolingRecipe(id,
                    readMatter(json, "ingredient"),
                    readUnLong(json, "waterAmount"),
                    readInt(json, "processingTick"),
                    readItem(json, "result"));
        }

        @Override
        public WaterCoolingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new WaterCoolingRecipe(id,
                    readMatter(buf),
                    readUnLong(buf),
                    buf.readInt(),
                    readItem(buf));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, WaterCoolingRecipe recipe) {
            writeToBuf(buf, recipe.ingredient);
            writeToBuf(buf, recipe.waterAmount);
            buf.writeInt(recipe.processingTick);
            writeToBuf(buf, recipe.result);
        }
    }
}
