package net.mofusya.mechanical_ageing.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.jei.MAgCategory;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.recipes.recipe.WaterCoolingRecipe;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

public class WaterCoolingCategory extends MAgCategory<WaterCoolingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MAg.MOD_ID, "water_cooling");

    public static final RecipeType<WaterCoolingRecipe> TYPE = new RecipeType<>(UID, WaterCoolingRecipe.class);

    public WaterCoolingCategory(IGuiHelper helper) {
        super(helper);
    }

    @Override
    public void getElements(WaterCoolingRecipe recipe, ElementList elements) {
        super.getElements(recipe, elements);

        elements.addIngredient(Ingredient.EMPTY);
        elements.addMatter(recipe.getWaterAmount(), recipe.getIngredient());
        elements.addItem(recipe.getResult());
    }

    @Override
    protected MachineTile getMachineTile() {
        return MAgMachines.WATER_COOLING_CHAMBER.tile();
    }

    @Override
    protected ItemLike getIconItem() {
        return MAgMachines.WATER_COOLING_CHAMBER.block();
    }

    @Override
    public RecipeType<WaterCoolingRecipe> getRecipeType() {
        return TYPE;
    }
}
