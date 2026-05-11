package net.mofusya.mechanical_ageing.data.recipes;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class MAgRecipeBuilder implements RecipeBuilder {
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    private final String name;

    public MAgRecipeBuilder(String name) {
        this.name = name;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(name, trigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String string) {
        return this;
    }

    @Override
    public void save(Consumer<FinishedRecipe> writer, ResourceLocation id) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);

        writer.accept(this.getRecipeJson(this.name, this.advancement, new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath())));
    }

    protected abstract MAgFinishedRecipe getRecipeJson(String name, Advancement.Builder advancement, ResourceLocation advancementId);
}
