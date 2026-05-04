package net.mofusya.mechanical_ageing.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.jei.MAgCategory;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.recipes.recipe.SmeltingRecipe;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

public class SmeltingCategory extends MAgCategory<SmeltingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MAg.MOD_ID, "smelting");

    public static final RecipeType<SmeltingRecipe> TYPE = new RecipeType<>(UID, SmeltingRecipe.class);

    public SmeltingCategory(IGuiHelper helper) {
        super(helper);
    }

    @Override
    public void getElements(SmeltingRecipe recipe, ElementList elements) {
        elements.addIngredient(Ingredient.EMPTY, recipe.getIngredient());
        elements.addMatter(recipe.getHeat());
        elements.addItem(recipe.getResult());
    }

    @Override
    protected MachineTile getMachineTile() {
        return MAgMachines.BRICK_SMELTING_CHAMBER.tile();
    }

    @Override
    protected ItemLike getIconItem() {
        return MAgMachines.BRICK_SMELTING_CHAMBER.block();
    }

    @Override
    public RecipeType<SmeltingRecipe> getRecipeType() {
        return TYPE;
    }
}
