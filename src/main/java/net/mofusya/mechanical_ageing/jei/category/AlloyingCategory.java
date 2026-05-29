package net.mofusya.mechanical_ageing.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.jei.MAgCategory;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.recipes.recipe.AlloyingRecipe;
import net.mofusya.mechanical_ageing.recipes.recipe.SmeltingRecipe;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

public class AlloyingCategory extends MAgCategory<AlloyingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MAg.MOD_ID, "alloying");

    public static final RecipeType<AlloyingRecipe> TYPE = new RecipeType<>(UID, AlloyingRecipe.class);

    public AlloyingCategory(IGuiHelper helper) {
        super(helper);
    }

    @Override
    public void getElements(AlloyingRecipe recipe, ElementList elements) {
        elements.addIngredient(Ingredient.EMPTY, recipe.getIngredient(), recipe.getSubIngredient());
        elements.addMatter(recipe.getHeat());
        elements.addItem(recipe.getResult());
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe.mechanical_ageing.alloying");
    }

    @Override
    protected MachineTile getMachineTile() {
        return MAgMachines.STEEL_HEAT_COMBINING_CHAMBER.tile();
    }

    @Override
    protected ItemLike getIconItem() {
        return MAgMachines.STEEL_HEAT_COMBINING_CHAMBER.block();
    }

    @Override
    public RecipeType<AlloyingRecipe> getRecipeType() {
        return TYPE;
    }
}
