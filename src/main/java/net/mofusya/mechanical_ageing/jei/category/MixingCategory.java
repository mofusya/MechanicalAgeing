package net.mofusya.mechanical_ageing.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.jei.MAgCategory;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.recipes.recipe.HeatingRecipe;
import net.mofusya.mechanical_ageing.recipes.recipe.MixingRecipe;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

public class MixingCategory extends MAgCategory<MixingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MAg.MOD_ID, "mixing");

    public static final RecipeType<MixingRecipe> TYPE = new RecipeType<>(UID, MixingRecipe.class);

    public MixingCategory(IGuiHelper helper) {
        super(helper);
    }

    @Override
    protected MachineTile getMachineTile() {
        return MAgMachines.MANUAL_MIXING_CHAMBER.tile();
    }

    @Override
    protected ItemLike getIconItem() {
        return MAgMachines.MANUAL_MIXING_CHAMBER.block();
    }

    @Override
    public void getElements(MixingRecipe recipe, ElementList elements) {
        super.getElements(recipe, elements);
        elements.addIngredient(Ingredient.EMPTY);
        elements.addMatter(recipe.getFirstIngredient(), recipe.getSecondIngredient());
        elements.addIngredient(recipe.getItemIngredient());
        elements.addMatter(recipe.getMatterResult());
        elements.addItem(recipe.getItemResult());
    }

    @Override
    public RecipeType<MixingRecipe> getRecipeType() {
        return TYPE;
    }
}
