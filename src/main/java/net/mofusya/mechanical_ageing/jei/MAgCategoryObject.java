package net.mofusya.mechanical_ageing.jei;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.mofusya.mechanical_ageing.recipes.MAgRecipe;

import java.util.function.Function;

public record MAgCategoryObject<T extends MAgRecipe>(Function<IGuiHelper, MAgCategory<?>> instance, RecipeType<?> jeiType,
                                                     net.minecraft.world.item.crafting.RecipeType<?> mcType, ItemLike... catalysts) {
}
