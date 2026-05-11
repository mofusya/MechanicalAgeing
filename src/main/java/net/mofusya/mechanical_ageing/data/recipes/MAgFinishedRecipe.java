package net.mofusya.mechanical_ageing.data.recipes;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.ornatelib.lang.SeptiLongValue;
import org.jetbrains.annotations.Nullable;
import org.openjdk.nashorn.internal.scripts.JO;

public abstract class MAgFinishedRecipe implements FinishedRecipe {
    private final String name;
    private final Advancement.Builder advancement;
    private final ResourceLocation advancementId;

    protected MAgFinishedRecipe(String name, Advancement.Builder advancement, ResourceLocation advancementId) {
        this.name = name;
        this.advancement = advancement;
        this.advancementId = advancementId;
    }


    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(MAg.MOD_ID, this.name);
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {
        return this.advancement.serializeToJson();
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return this.advancementId;
    }

    protected static void write(JsonObject json, String id, SeptiLongValue digit, int multiplier) {
        JsonObject septiLong = new JsonObject();
        septiLong.addProperty("digit", digit.name());
        septiLong.addProperty("multiplier", multiplier);
        json.add(id, septiLong);
    }

    protected static void write(JsonObject json, String id, ItemStack itemStack){
        JsonObject item = new JsonObject();
        item.addProperty("item", ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
        if (itemStack.getCount() > 1){
            item.addProperty("count", itemStack.getCount());
        }
        json.add(id, item);
    }
}
