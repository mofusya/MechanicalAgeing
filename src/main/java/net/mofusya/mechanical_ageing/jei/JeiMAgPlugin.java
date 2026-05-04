package net.mofusya.mechanical_ageing.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.jei.ingredient.MatterHelper;
import net.mofusya.mechanical_ageing.jei.ingredient.MatterRenderer;
import net.mofusya.mechanical_ageing.matter.MatterManager;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.ornatelib.lang.SeptiLongValue;

import java.util.ArrayList;

@JeiPlugin
public class JeiMAgPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MAg.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        for (var category : MAgCategories.CATEGORIES){
            registration.addRecipeCategories((IRecipeCategory<?>) category.instance().apply(registration.getJeiHelpers().getGuiHelper()));
        }
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var recipeManager = Minecraft.getInstance().level.getRecipeManager();

        for (var category : MAgCategories.CATEGORIES){
            registration.addRecipes(category.jeiType(),
                    recipeManager.getAllRecipesFor(category.mcType()));
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        for (var category : MAgCategories.CATEGORIES){
            registration.addRecipeCatalysts(category.jeiType(), category.catalysts());
        }
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        ArrayList<MatterStack> matterStackList = new ArrayList<>();
        for (var type : MatterManager.get().values()){
            matterStackList.add(new MatterStack(type, SeptiLongValue.HUNDRED.get().multiply(5)));
        }

        registration.register(MAgIngredient.MATTER_TYPE, matterStackList, new MatterHelper(), new MatterRenderer());
    }

    protected static IJeiRuntime jeiRuntime;

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JeiMAgPlugin.jeiRuntime = jeiRuntime;
    }
}
