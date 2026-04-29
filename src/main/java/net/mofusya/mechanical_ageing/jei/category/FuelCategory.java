package net.mofusya.mechanical_ageing.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.jei.MAgCategory;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.recipes.recipe.FuelRecipe;
import net.mofusya.mechanical_ageing.recipes.recipe.TriDimCraftingRecipe;
import net.mofusya.mechanical_ageing.tiles.ModMachines;
import org.jetbrains.annotations.Nullable;

public class FuelCategory extends MAgCategory<FuelRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MechanicalAgeing.MOD_ID, "fuel");

    public static final RecipeType<FuelRecipe> TYPE = new RecipeType<>(UID, FuelRecipe.class);

    private final IDrawable backGround;
    private final IDrawable icon;

    public FuelCategory(IGuiHelper helper) {
        super(helper);
        this.backGround = helper.createDrawable(JEI_FRAME, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModMachines.BRICK_BURNING_CHAMBER.block()));
    }

    @Override
    public void getElements(FuelRecipe recipe, ElementList elements) {
        elements.addIngredient(Ingredient.EMPTY, recipe.getIngredient());
        elements.addMatter(recipe.getResult());
    }

    @Override
    protected MachineTile getMachineTile() {
        return ModMachines.BRICK_BURNING_CHAMBER.tile();
    }

    @Override
    public RecipeType<FuelRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return this.getMachineTile().getDisplayName();
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @SuppressWarnings("removal")
    @Override
    public @Nullable IDrawable getBackground() {
        return this.backGround;
    }
}
