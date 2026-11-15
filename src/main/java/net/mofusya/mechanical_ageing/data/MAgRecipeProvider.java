package net.mofusya.mechanical_ageing.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

public class MAgRecipeProvider extends RecipeProvider {
    public MAgRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        for (IMAgDataProvider provider : MAgDataGenerator.getProviders()) {
            provider.generateRecipes(this, writer);
        }
    }
}