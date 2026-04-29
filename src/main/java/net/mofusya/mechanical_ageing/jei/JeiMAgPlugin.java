package net.mofusya.mechanical_ageing.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.jei.category.FuelCategory;
import net.mofusya.mechanical_ageing.jei.category.TriDimCraftingCategory;
import net.mofusya.mechanical_ageing.jei.ingredient.MatterHelper;
import net.mofusya.mechanical_ageing.jei.ingredient.MatterRenderer;
import net.mofusya.mechanical_ageing.matter.MatterManager;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.mechanical_ageing.recipes.recipe.FuelRecipe;
import net.mofusya.mechanical_ageing.recipes.recipe.TriDimCraftingRecipe;
import net.mofusya.mechanical_ageing.tiles.ModMachines;
import net.mofusya.ornatelib.lang.SeptiLongValue;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JeiMAgPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MechanicalAgeing.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new TriDimCraftingCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FuelCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var recipeManager = Minecraft.getInstance().level.getRecipeManager();

        registration.addRecipes(TriDimCraftingCategory.TYPE,
                recipeManager.getAllRecipesFor(TriDimCraftingRecipe.Type.INSTANCE));
        registration.addRecipes(FuelCategory.TYPE,
                recipeManager.getAllRecipesFor(FuelRecipe.Type.INSTANCE));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(TriDimCraftingCategory.TYPE, ModMachines.TRI_DIM_CRAFTING_TABLE.block());
        registration.addRecipeCatalysts(FuelCategory.TYPE, ModMachines.BRICK_BURNING_CHAMBER.block());
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        ArrayList<MatterStack> matterStackList = new ArrayList<>();
        for (var type : MatterManager.get().values()){
            matterStackList.add(new MatterStack(type, SeptiLongValue.HUNDRED.get().multiply(5)));
        }

        registration.register(MAgIngredient.MATTER_TYPE, matterStackList, new MatterHelper(), new MatterRenderer());
    }
}
