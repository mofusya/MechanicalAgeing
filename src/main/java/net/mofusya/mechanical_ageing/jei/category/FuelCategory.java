package net.mofusya.mechanical_ageing.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.jei.MAgCategory;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.recipes.recipe.FuelRecipe;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

public class FuelCategory extends MAgCategory<FuelRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MAg.MOD_ID, "fuel");

    public static final RecipeType<FuelRecipe> TYPE = new RecipeType<>(UID, FuelRecipe.class);

    public FuelCategory(IGuiHelper helper) {
        super(helper);
    }

    @Override
    public void getElements(FuelRecipe recipe, ElementList elements) {
        elements.addIngredient(Ingredient.EMPTY, recipe.getIngredient());
        elements.addMatter(recipe.getResult());
    }

    @Override
    protected MachineTile getMachineTile() {
        return MAgMachines.BRICK_BURNING_CHAMBER.tile();
    }

    @Override
    protected ItemLike getIconItem() {
        return MAgMachines.BRICK_BURNING_CHAMBER.block();
    }

    @Override
    public RecipeType<FuelRecipe> getRecipeType() {
        return TYPE;
    }
}
