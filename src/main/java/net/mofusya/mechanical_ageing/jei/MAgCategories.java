package net.mofusya.mechanical_ageing.jei;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.mofusya.mechanical_ageing.jei.category.FuelCategory;
import net.mofusya.mechanical_ageing.jei.category.MatterBurningCategory;
import net.mofusya.mechanical_ageing.jei.category.SmeltingCategory;
import net.mofusya.mechanical_ageing.jei.category.TriDimCraftingCategory;
import net.mofusya.mechanical_ageing.recipes.recipe.FuelRecipe;
import net.mofusya.mechanical_ageing.recipes.recipe.MatterBurningRecipe;
import net.mofusya.mechanical_ageing.recipes.recipe.SmeltingRecipe;
import net.mofusya.mechanical_ageing.recipes.recipe.TriDimCraftingRecipe;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

import java.util.ArrayList;
import java.util.function.Function;

public class MAgCategories {
    public static final ArrayList<MAgCategoryObject> CATEGORIES = new ArrayList<>();

    static {
        create(TriDimCraftingCategory::new, TriDimCraftingCategory.TYPE, TriDimCraftingRecipe.Type.INSTANCE, MAgMachines.TRI_DIM_CRAFTING_TABLE.block());
        create(FuelCategory::new, FuelCategory.TYPE, FuelRecipe.Type.INSTANCE, MAgMachines.BRICK_BURNING_CHAMBER.block());
        create(MatterBurningCategory::new, MatterBurningCategory.TYPE, MatterBurningRecipe.Type.INSTANCE, MAgMachines.BRICK_BURNING_CHAMBER.block());
        create(SmeltingCategory::new, SmeltingCategory.TYPE, SmeltingRecipe.Type.INSTANCE, MAgMachines.BRICK_SMELTING_CHAMBER.block());
    }

    public static void create(Function<IGuiHelper, MAgCategory<?>> instance, RecipeType<?> jeiType, net.minecraft.world.item.crafting.RecipeType<?> mcType, ItemLike... catalysts) {
        CATEGORIES.add(new MAgCategoryObject(instance, jeiType, mcType, catalysts));
    }
}
