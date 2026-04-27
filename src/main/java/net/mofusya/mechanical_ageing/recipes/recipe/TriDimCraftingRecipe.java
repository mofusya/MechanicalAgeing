package net.mofusya.mechanical_ageing.recipes.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class TriDimCraftingRecipe implements Recipe<Container> {
    private static final int MAX_WIDTH = 9;
    private static final int MAX_HEIGHT = 3;

    private final ResourceLocation id;
    private final int width;
    private final int height;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;

    public TriDimCraftingRecipe(ResourceLocation id, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public boolean matches(Container container, Level level) {
        for (int i = 0; i <= MAX_WIDTH - this.width; i++) {
            for (int j = 0; j <= MAX_HEIGHT - this.height; j++) {
                if (this.test(container, i, j, false)) return true;
                if (this.test(container, i, j, true)) return true;
            }
        }
        return false;
    }

    private boolean test(Container inventory, int xOffset, int yOffset, boolean mirrored) {
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
    public ItemStack assemble(Container container, @Nullable RegistryAccess access) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<TriDimCraftingRecipe> {
        public static final Type INSTANCE = new Type();

        private Type() {
        }
    }

    public static class Serializer implements RecipeSerializer<TriDimCraftingRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

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

            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            return new TriDimCraftingRecipe(id, width, height, ingredients, result);
        }

        @Override
        public @Nullable TriDimCraftingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int width = buf.readInt();
            int height = buf.readInt();
            int size = buf.readInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);

            for (int i = 0; i < size; i++) {
                ingredients.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack result = buf.readItem();

            return new TriDimCraftingRecipe(id, width, height, ingredients, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, TriDimCraftingRecipe recipe) {
            buf.writeInt(recipe.width);
            buf.writeInt(recipe.height);
            buf.writeInt(recipe.ingredients.size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buf);
            }

            buf.writeItemStack(recipe.getResultItem(null), false);
        }
    }
}
