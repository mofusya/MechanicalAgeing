package net.mofusya.mechanical_ageing.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.jei.MAgCategory;
import net.mofusya.mechanical_ageing.recipes.recipe.TriDimCraftingRecipe;
import net.mofusya.mechanical_ageing.tiles.ModMachines;
import org.jetbrains.annotations.Nullable;

public class TriDimCraftingCategory extends MAgCategory<TriDimCraftingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MechanicalAgeing.MOD_ID, "tri_dimensional_crafting");

    public static final RecipeType<TriDimCraftingRecipe> TYPE = new RecipeType<>(UID, TriDimCraftingRecipe.class);

    private final IDrawable backGround;
    private final IDrawable icon;

    public TriDimCraftingCategory(IGuiHelper helper) {
        this.backGround = helper.createDrawable(JEI_FRAME, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModMachines.TRI_DIM_CRAFTING_TABLE.block()));
    }

    @Override
    protected MachineTile getMachineTile() {
        return ModMachines.TRI_DIM_CRAFTING_TABLE.tile();
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

    @Override
    public Component getTitle() {
        return Component.translatable("block.mechanical_ageing.tri_dimensional_crafting_table.machine_name");
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
