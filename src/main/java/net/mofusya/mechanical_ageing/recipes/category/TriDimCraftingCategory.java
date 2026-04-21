package net.mofusya.mechanical_ageing.recipes.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.recipes.recipe.TriDimCraftingRecipe;
import net.mofusya.mechanical_ageing.tiles.ModMachines;
import org.jetbrains.annotations.Nullable;

public class TriDimCraftingCategory implements IRecipeCategory<TriDimCraftingRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(MechanicalAgeing.MOD_ID, "tri_dimensional_crafting");

    public static final RecipeType<TriDimCraftingRecipe> TYPE = new RecipeType<>(UID, TriDimCraftingRecipe.class);

    //private final IDrawable backGround;
    private final IDrawable icon;

    public TriDimCraftingCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModMachines.TRI_DIM_CRAFTING_TABLE.block()));
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

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TriDimCraftingRecipe recipe, IFocusGroup focus) {
        for (int i = 0; i < ModMachines.TRI_DIM_CRAFTING_TABLE.tile().getSlots().size() - 1; i++) {
            var slot = ModMachines.TRI_DIM_CRAFTING_TABLE.tile().getSlots().get(i);
            builder.addSlot(RecipeIngredientRole.INPUT, slot.x(), slot.y()).addIngredients(recipe.getIngredients().get(i));
        }
        var output = ModMachines.TRI_DIM_CRAFTING_TABLE.tile().getSlots().get(27);
        builder.addSlot(RecipeIngredientRole.OUTPUT, output.x(),output.y()).addItemStack(recipe.getResultItem(null));
    }
}
