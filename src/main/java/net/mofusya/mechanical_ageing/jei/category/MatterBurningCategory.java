package net.mofusya.mechanical_ageing.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.jei.MAgCategory;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.recipes.recipe.MatterBurningRecipe;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

public class MatterBurningCategory extends MAgCategory<MatterBurningRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MAg.MOD_ID, "matter_burning");

    public static final RecipeType<MatterBurningRecipe> TYPE = new RecipeType<>(UID, MatterBurningRecipe.class);

    public MatterBurningCategory(IGuiHelper helper) {
        super(helper);
    }

    @Override
    public void getElements(MatterBurningRecipe recipe, ElementList elements) {
        elements.addMatter(recipe.getIngredient(), recipe.getResult());
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
    public RecipeType<MatterBurningRecipe> getRecipeType() {
        return TYPE;
    }
}
