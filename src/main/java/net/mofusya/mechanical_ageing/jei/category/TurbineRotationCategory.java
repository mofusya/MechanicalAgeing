package net.mofusya.mechanical_ageing.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.jei.MAgCategory;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.recipes.recipe.TurbineRotatingRecipe;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

public class TurbineRotationCategory extends MAgCategory<TurbineRotatingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MAg.MOD_ID, "turbine_rotating");

    public static final RecipeType<TurbineRotatingRecipe> TYPE = new RecipeType<>(UID, TurbineRotatingRecipe.class);

    public TurbineRotationCategory(IGuiHelper helper) {
        super(helper);
    }

    @Override
    public void getElements(TurbineRotatingRecipe recipe, ElementList elements) {
        elements.addMatter(recipe.getIngredient(), recipe.getResult());
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe.mechanical_ageing.turbine_rotating");
    }

    @Override
    protected MachineTile getMachineTile() {
        return MAgMachines.IMPULSE_TURBINE.tile();
    }

    @Override
    protected ItemLike getIconItem() {
        return MAgMachines.IMPULSE_TURBINE.block();
    }

    @Override
    public RecipeType<TurbineRotatingRecipe> getRecipeType() {
        return TYPE;
    }
}
