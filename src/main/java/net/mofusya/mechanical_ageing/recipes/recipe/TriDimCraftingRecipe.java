package net.mofusya.mechanical_ageing.recipes.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.MAgRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class TriDimCraftingRecipe extends MAgRecipe {
    private static final int MAX_WIDTH = 9;
    private static final int MAX_HEIGHT = 3;

    private final int width;
    private final int height;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;

    public TriDimCraftingRecipe(ResourceLocation id, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
        super(id, Serializer.INSTANCE, Type.INSTANCE);
        this.width = width;
        this.height = height;
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public boolean matches(MAgContainer container, Level level) {
        for (int i = 0; i <= MAX_WIDTH - this.width; i++) {
            for (int j = 0; j <= MAX_HEIGHT - this.height; j++) {
                if (this.test(container, i, j, false)) return true;
                if (this.test(container, i, j, true)) return true;
            }
        }
        return false;
    }

    private boolean test(MAgContainer inventory, int xOffset, int yOffset, boolean mirrored) {
        for (int x = 0; x < MAX_WIDTH; x++) {
            for (int y = 0; y < MAX_HEIGHT; y++) {
                int subX = x - xOffset;
                int subY = y - yOffset;
                Ingredient ingredient;

                if (subX >= 0 && subY >= 0 && subX < this.width && subY < this.height) {
                    if (mirrored) {
                        ingredient = this.ingredients.get(this.width - subX - 1 + subY * this.width);
                    } else {
                        ingredient = this.ingredients.get(subX + subY * this.width);
                    }

                    if (!ingredient.test(inventory.getItem(x + y * MAX_WIDTH))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public enum Type implements RecipeType<TriDimCraftingRecipe> {INSTANCE}

    public enum Serializer implements RecipeSerializer<TriDimCraftingRecipe> {
        INSTANCE;

        @Override
        public TriDimCraftingRecipe fromJson(ResourceLocation id, JsonObject json) {
            /*
            NonNullList<Ingredient> ingredients = NonNullList.create();

            JsonArray ingredientList = GsonHelper.getAsJsonArray(json, "ingredients");
            for (JsonElement element : ingredientList) {
                ingredients.add(Ingredient.fromJson(element));
            }
            while (ingredients.size() < 27) {
                ingredients.add(Ingredient.EMPTY);
            }

            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            return new TriDimCraftingRecipe(id, ingredients, result);
             */
            JsonArray patternJson = GsonHelper.getAsJsonArray(json, "pattern");
            int width = patternJson.get(0).getAsString().length();
            int height = patternJson.size();

            String[] pattern = new String[height];
            for (int i = 0; i < height; i++) {
                pattern[i] = patternJson.get(i).getAsString();
            }

            Map<Character, Ingredient> key = new HashMap<>();
            JsonObject keuJson = GsonHelper.getAsJsonObject(json, "key");
            for (String pKey : keuJson.keySet()) {
                key.put(pKey.charAt(0), Ingredient.fromJson(keuJson.get(pKey)));
            }

            NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
            for (int i = 0; i < height; i++) {
                String row = pattern[i];
                for (int j = 0; j < width; j++) {
                    char c = row.charAt(j);
                    ingredients.set(j + i * width, key.getOrDefault(c, Ingredient.EMPTY));
                }
            }

            return new TriDimCraftingRecipe(id, width, height, ingredients, readItem(json, "result"));
        }

        @Override
        public TriDimCraftingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int width = buf.readInt();
            int height = buf.readInt();

            return new TriDimCraftingRecipe(id, width, height, readIngredients(buf), readItem(buf));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, TriDimCraftingRecipe recipe) {
            buf.writeInt(recipe.width);
            buf.writeInt(recipe.height);

            writeToBuf(buf, recipe.getIngredients());
            writeToBuf(buf, recipe.getResultItem(null));
        }
    }
}
