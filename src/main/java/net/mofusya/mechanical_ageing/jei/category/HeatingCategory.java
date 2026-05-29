package net.mofusya.mechanical_ageing.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.jei.MAgCategory;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.recipes.recipe.HeatingRecipe;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

public class HeatingCategory extends MAgCategory<HeatingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MAg.MOD_ID, "heating");

    public static final RecipeType<HeatingRecipe> TYPE = new RecipeType<>(UID, HeatingRecipe.class);

    public HeatingCategory(IGuiHelper helper) {
        super(helper);
    }

    @Override
    public void getElements(HeatingRecipe recipe, ElementList elements) {
        elements.addMatter(recipe.getHeat());
        elements.addMatter(recipe.getIngredient());
        elements.addMatter(recipe.getResult());
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe.mechanical_ageing.heating");
    }

    @Override
    protected MachineTile getMachineTile() {
        return MAgMachines.STEEL_HEATING_CHAMBER.tile();
    }

    @Override
    protected ItemLike getIconItem() {
        return MAgMachines.STEEL_HEATING_CHAMBER.block();
    }

    @Override
    public RecipeType<HeatingRecipe> getRecipeType() {
        return TYPE;
    }
}
