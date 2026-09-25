package net.mofusya.mechanical_ageing.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.matter.LazyMatterStack;
import net.mofusya.mechanical_ageing.matter.LazyMatterType;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.ArrayMap;

import java.util.Arrays;

public abstract class MAgRecipe implements Recipe<MAgContainer> {
    private static final UnLongJsonReader unLongJsonReader = new UnLongJsonReader(C.DIGIT, C.MULTIPLIER);

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
    protected static UnLong readUnLong(JsonObject json, String id) {
        var unLongJson = json.getAsJsonObject(id);
        if (unLongJson == null) return UnLong.one();

        return unLongJsonReader.get(unLongJson);
    }

    protected static UnLong readUnLong(FriendlyByteBuf buf) {
        return UnLong.createWithoutReverse(Arrays.stream(buf.readLongArray()).boxed().toList());
    }

    protected static ItemStack readItem(JsonObject json, String id) {
        return ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, id));
    }

    protected static ItemStack readItem(JsonObject json, String id, boolean mayBeEmpty) {
        var itemJson = json.getAsJsonObject(id);
        if (itemJson == null && mayBeEmpty) return ItemStack.EMPTY;

        return readItem(json, id);
    }

    protected static ItemStack readItem(FriendlyByteBuf buf) {
        return buf.readItem();
    }

    protected static Ingredient readIngredient(JsonObject json, String id) {
        return Ingredient.fromJson(GsonHelper.getAsJsonObject(json, id));
    }

    protected static Ingredient readIngredient(JsonObject json, String id, boolean mayBeEmpty) {
        var ingredientJson = json.getAsJsonObject(id);
        if (ingredientJson == null && mayBeEmpty) return Ingredient.EMPTY;

        return readIngredient(json, id);
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

    protected static NonNullList<Ingredient> readIngredients(JsonObject json, String id, boolean mayBeEmpty) {
        JsonArray ingredientsJson = json.getAsJsonArray(id);
        if (ingredientsJson == null && mayBeEmpty) return NonNullList.create();

        return readIngredients(json,id);
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
        JsonObject matter = GsonHelper.getAsJsonObject(json, id);
        ResourceLocation type = new ResourceLocation(GsonHelper.getAsString(matter, "type"));
        UnLong amount = readUnLong(matter, "amount");

        ArrayMap<String, String> tags = new ArrayMap<>();
        JsonArray tagsJson = matter.getAsJsonArray("tags");
        if (tagsJson != null) {
            tagsJson.forEach(tag -> {
                JsonObject tagJson = tag.getAsJsonObject();
                tags.put(GsonHelper.getAsString(tagJson, "key"), GsonHelper.getAsString(tagJson, "value"));
            });
        }

        return new LazyMatterStack(type, amount, tags);
    }

    protected static LazyMatterStack readMatter(JsonObject json, String id, boolean mayBeEmpty) {
        JsonObject matterJson = json.getAsJsonObject(id);
        if (matterJson == null && mayBeEmpty) return new LazyMatterStack(null, UnLong.zero());

        return readMatter(json, id);
    }

    protected static LazyMatterStack readMatter(FriendlyByteBuf buf) {
        ResourceLocation type = buf.readResourceLocation();
        if (type.getPath().equals("null_404") && type.getPath().equals("empty")) return new LazyMatterStack(null, UnLong.zero());

        UnLong amount = readUnLong(buf);
        int tagsSize = buf.readInt();
        ArrayMap<String, String> tags = new ArrayMap<>();
        for (int i = 0; i < tagsSize; i++) {
            tags.put(buf.readResourceLocation().getPath(), buf.readResourceLocation().getPath());
        }

        return new LazyMatterStack(type, amount, tags);
    }

    protected static LazyMatterType readMatterType(JsonObject json, String id) {
        JsonObject matter = GsonHelper.getAsJsonObject(json, id);
        ResourceLocation type = new ResourceLocation(GsonHelper.getAsString(matter, "type"));

        ArrayMap<String, String> tags = new ArrayMap<>();
        JsonArray tagsJson = matter.getAsJsonArray("tags");
        if (tagsJson != null) {
            tagsJson.forEach(tag -> {
                JsonObject tagJson = tag.getAsJsonObject();
                tags.put(GsonHelper.getAsString(tagJson, "key"), GsonHelper.getAsString(tagJson, "value"));
            });
        }

        return new LazyMatterType(type, tags);
    }

    protected static LazyMatterType readMatterType(JsonObject json, String id, boolean mayBeEmpty) {
        JsonObject matterJson = json.getAsJsonObject(id);
        if (matterJson == null && mayBeEmpty) return new LazyMatterType(null);

        return readMatterType(json, id);
    }

    protected static LazyMatterType readMatterType(FriendlyByteBuf buf) {
        ResourceLocation type = buf.readResourceLocation();
        if (type.getPath().equals("null_404") && type.getPath().equals("empty")) return new LazyMatterType(null);

        int tagsSize = buf.readInt();
        ArrayMap<String, String> tags = new ArrayMap<>();
        for (int i = 0; i < tagsSize; i++) {
            tags.put(buf.readResourceLocation().getPath(), buf.readResourceLocation().getPath());
        }

        return new LazyMatterType(type, tags);
    }

    protected static int readInt(JsonObject json, String id) {
        JsonPrimitive valueJson = json.getAsJsonPrimitive(id);
        if (valueJson == null) return 1;

        return valueJson.getAsInt();
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

    protected static void writeToBuf(FriendlyByteBuf buf, UnLong unLong) {
        long[] unLongValues = new long[unLong.getLayerSize()];
        unLong.forEachI((value, index) -> {
            unLongValues[index] = value;
        });
        buf.writeLongArray(unLongValues);
    }

    protected static void writeToBuf(FriendlyByteBuf buf, LazyMatterStack matterStack) {
        buf.writeResourceLocation(matterStack.type() == null ? new ResourceLocation("null_404", "empty") : matterStack.type());
        writeToBuf(buf, matterStack.amount());
        buf.writeInt(matterStack.tags().size());
        matterStack.tags().forEach((key, value) -> {
            buf.writeResourceLocation(new ResourceLocation("null_404", key));
            buf.writeResourceLocation(new ResourceLocation("null_404", value));
        });
    }

    protected static void writeToBuf(FriendlyByteBuf buf, LazyMatterType matterStack) {
        buf.writeResourceLocation(matterStack.type() == null ? new ResourceLocation("null_404", "empty") : matterStack.type());
        buf.writeInt(matterStack.tags().size());
        matterStack.tags().forEach((key, value) -> {
            buf.writeResourceLocation(new ResourceLocation("null_404", key));
            buf.writeResourceLocation(new ResourceLocation("null_404", value));
        });
    }

    //Match helper
    public static boolean test(Ingredient ingredient, ItemStack itemStack) {
        if (ingredient.isEmpty()) return true;
        return ingredient.test(itemStack);
    }

    public static boolean test(MatterStack ingredient, MatterStack matterStack) {
        if (ingredient.isEmpty()) return true;
        if (matterStack.isEmpty()) return false;

        return ingredient.getType() == null || (matterStack.getType().is(ingredient.getType()) && matterStack.getAmount().isGreaterOrSameAs(ingredient.getAmount()) && MatterStack.checkIngredientTags(ingredient, matterStack));
    }
}