package net.mofusya.mechanical_ageing.jei;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.mofusya.mechanical_ageing.jei.category.*;
import net.mofusya.mechanical_ageing.recipes.recipe.*;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

import java.util.ArrayList;
import java.util.function.Function;

public class MAgCategories {
    public static final ArrayList<MAgCategoryObject> CATEGORIES = new ArrayList<>();

    static {
        create(TriDimCraftingCategory::new, TriDimCraftingCategory.TYPE, TriDimCraftingRecipe.Type.INSTANCE, MAgMachines.TRI_DIM_CRAFTING_TABLE.block());
        create(FuelCategory::new, FuelCategory.TYPE, FuelRecipe.Type.INSTANCE,
                MAgMachines.BRICK_BURNING_CHAMBER.block(),
                MAgMachines.STEEL_BURNING_CHAMBER.block()
        );
        create(MatterBurningCategory::new, MatterBurningCategory.TYPE, MatterBurningRecipe.Type.INSTANCE,
                MAgMachines.BRICK_BURNING_CHAMBER.block(),
                MAgMachines.STEEL_BURNING_CHAMBER.block()
        );
        create(SmeltingCategory::new, SmeltingCategory.TYPE, SmeltingRecipe.Type.INSTANCE,
                MAgMachines.BRICK_SMELTING_CHAMBER.block(),
                MAgMachines.STEEL_SMELTING_CHAMBER.block()
        );
        create(AlloyingCategory::new, AlloyingCategory.TYPE, AlloyingRecipe.Type.INSTANCE,
                MAgMachines.BRICK_HEAT_COMBINING_CHAMBER.block(),
                MAgMachines.STEEL_HEAT_COMBINING_CHAMBER.block()
        );
        create(HeatingCategory::new, HeatingCategory.TYPE, HeatingRecipe.Type.INSTANCE,
                MAgMachines.STEEL_HEATING_CHAMBER.block()
        );
        create(TurbineRotationCategory::new, TurbineRotationCategory.TYPE, TurbineRotatingRecipe.Type.INSTANCE,
                MAgMachines.IMPULSE_TURBINE.block()
        );
        create(MixingCategory::new, MixingCategory.TYPE, MixingRecipe.Type.INSTANCE,
                MAgMachines.MANUAL_MIXING_CHAMBER.block()
        );
        create(WaterCoolingCategory::new, WaterCoolingCategory.TYPE, WaterCoolingRecipe.Type.INSTANCE,
                MAgMachines.WATER_COOLING_CHAMBER.block()
        );
    }

    public static void create(Function<IGuiHelper, MAgCategory<?>> instance, RecipeType<?> jeiType, net.minecraft.world.item.crafting.RecipeType<?> mcType, ItemLike... catalysts) {
        CATEGORIES.add(new MAgCategoryObject(instance, jeiType, mcType, catalysts));
    }
}
