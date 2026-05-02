package net.mofusya.mechanical_ageing.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.mofusya.mechanical_ageing.matter.LazyMatterStack;
import net.mofusya.mechanical_ageing.matter.MatterManager;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;

public abstract class MAgRecipe implements Recipe<MAgContainer> {
    private final ResourceLocation id;
    private final RecipeSerializer<?> serializer;
    private final RecipeType<?> type;

    public MAgRecipe(ResourceLocation id, RecipeSerializer<?> serializer, RecipeType<?> type) {
        this.id = id;
        this.serializer = serializer;
        this.type = type;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public final ItemStack getResultItem(RegistryAccess access) {
        return null;
    }

    @Override
    public final ItemStack assemble(MAgContainer container, RegistryAccess access) {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    //Overrides
    public ItemStack getResultItem() {
        return null;
    }

    //Json Helpers
    protected static SeptiLong readSeptiLong(JsonObject json, String id) {
        var septiLong = GsonHelper.getAsJsonObject(json, id);
        SeptiLong digit = SeptiLongValue.valueOf(GsonHelper.getAsString(septiLong, "digit")).get();
        int multiplier = GsonHelper.getAsInt(septiLong, "multiplier");
        return digit.multiply(multiplier);
    }

    protected static SeptiLong readSeptiLong(FriendlyByteBuf buf) {
        return SeptiLong.createFromList(buf.readLongArray());
    }

    protected static ItemStack readItem(JsonObject json, String id) {
        return ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, id));
    }

    protected static ItemStack readItem(FriendlyByteBuf buf) {
        return buf.readItem();
    }

    protected static Ingredient readIngredient(JsonObject json, String id) {
        return Ingredient.fromJson(GsonHelper.getAsJsonObject(json, id));
    }

    protected static Ingredient readIngredient(FriendlyByteBuf buf) {
        return Ingredient.fromNetwork(buf);
    }

    protected static NonNullList<Ingredient> readIngredients(JsonObject json, String id) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        JsonArray ingredientList = GsonHelper.getAsJsonArray(json, id);
        for (JsonElement element : ingredientList) {
            ingredients.add(Ingredient.fromJson(element));
        }
        return ingredients;
    }

    protected static NonNullList<Ingredient> readIngredients(FriendlyByteBuf buf) {
        int size = buf.readInt();
        NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
        for (int i = 0; i < size; i++) {
            ingredients.set(i, readIngredient(buf));
        }
        return ingredients;
    }

    protected static LazyMatterStack readMatter(JsonObject json, String id) {
        JsonObject ingredient = GsonHelper.getAsJsonObject(json, id);
        ResourceLocation type = new ResourceLocation(GsonHelper.getAsString(ingredient, "type"));
        SeptiLong amount = readSeptiLong(ingredient, "amount");

        return new LazyMatterStack(type, amount);
    }

    protected static LazyMatterStack readMatter(FriendlyByteBuf buf) {
        ResourceLocation type = buf.readResourceLocation();
        SeptiLong amount = readSeptiLong(buf);

        return new LazyMatterStack(type, amount);
    }

    protected static int readInt(JsonObject json, String id) {
        return GsonHelper.getAsInt(json, id);
    }

    protected static void writeToBuf(FriendlyByteBuf buf, ItemStack itemStack) {
        buf.writeItemStack(itemStack, false);
    }

    protected static void writeToBuf(FriendlyByteBuf buf, Ingredient ingredient) {
        ingredient.toNetwork(buf);
    }

    protected static void writeToBuf(FriendlyByteBuf buf, NonNullList<Ingredient> ingredients) {
        buf.writeInt(ingredients.size());
        for (Ingredient ingredient : ingredients) {
            ingredient.toNetwork(buf);
        }
    }

    protected static void writeToBuf(FriendlyByteBuf buf, SeptiLong septiLong) {
        buf.writeLongArray(septiLong.getLayer());
    }

    protected static void writeToBuf(FriendlyByteBuf buf, LazyMatterStack matterStack) {
        buf.writeResourceLocation(matterStack.type());
        writeToBuf(buf, matterStack.amount());
    }

    //Match helper
    public static boolean test(Ingredient ingredient, ItemStack itemStack) {
        return ingredient.test(itemStack);
    }

    public static boolean test(MatterStack ingredient, MatterStack matterStack) {
        if (matterStack.getType() == null) return false;

        return ingredient.getType() == null || (matterStack.getType().is(ingredient.getType())) && matterStack.getAmount().isGreaterOrSameThan(ingredient.getAmount());
    }
}