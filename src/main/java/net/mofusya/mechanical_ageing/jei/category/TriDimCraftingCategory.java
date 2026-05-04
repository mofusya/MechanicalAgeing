package net.mofusya.mechanical_ageing.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.jei.MAgCategory;
import net.mofusya.mechanical_ageing.recipes.recipe.TriDimCraftingRecipe;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

public class TriDimCraftingCategory extends MAgCategory<TriDimCraftingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MAg.MOD_ID, "tri_dimensional_crafting");

    public static final RecipeType<TriDimCraftingRecipe> TYPE = new RecipeType<>(UID, TriDimCraftingRecipe.class);

    public TriDimCraftingCategory(IGuiHelper helper) {
        super(helper);
    }

    @Override
    protected MachineTile getMachineTile() {
        return MAgMachines.TRI_DIM_CRAFTING_TABLE.tile();
    }

    @Override
    protected ItemLike getIconItem() {
        return MAgMachines.TRI_DIM_CRAFTING_TABLE.block();
    }

    @Override
    public void getElements(TriDimCraftingRecipe recipe, ElementList elements) {
        elements.addIngredient(recipe.getIngredients());
        elements.addItem(recipe.getResultItem());
    }

    @Override
    public RecipeType<TriDimCraftingRecipe> getRecipeType() {
        return TYPE;
    }
}
