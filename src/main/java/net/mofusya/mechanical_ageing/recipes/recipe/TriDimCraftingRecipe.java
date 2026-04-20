package net.mofusya.mechanical_ageing.recipes.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TriDimCraftingRecipe implements Recipe<SimpleContainer> {
    private static final int MAX_WIDTH = 9;
    private static final int MAX_HEIGHT = 3;

    private final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;

    public TriDimCraftingRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack result) {
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        boolean matches = true;
        for (int i = 0; i < this.ingredients.size(); i++) {
            if (!this.ingredients.get(i).test(container.getItem(i))) {
                matches = false;
            }
        }
        return matches;
    }

    @Override
    public ItemStack assemble(SimpleContainer p_44001_, RegistryAccess p_267165_) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.result;
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
        private Type() {
        }

        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<TriDimCraftingRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        @Override
        public TriDimCraftingRecipe fromJson(ResourceLocation id, JsonObject json) {
            NonNullList<Ingredient> ingredients = NonNullList.create();

            JsonArray ingredientList = json.getAsJsonArray("ingredients");
            for (JsonElement element : ingredientList) {
                ingredients.add(Ingredient.fromJson(element));
            }

            ItemStack result = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("result"));

            return new TriDimCraftingRecipe(id, ingredients, result);
        }

        @Override
        public @Nullable TriDimCraftingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int size = buf.readInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);

            for (int i = 0; i < size; i++) {
                ingredients.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack result = buf.readItem();

            return new TriDimCraftingRecipe(id, ingredients, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, TriDimCraftingRecipe recipe) {
            buf.writeInt(recipe.ingredients.size());

            for (Ingredient ingredient : recipe.getIngredients()){
                ingredient.toNetwork(buf);
            }

            buf.writeItem(recipe.getResultItem(null));
        }
    }
}
