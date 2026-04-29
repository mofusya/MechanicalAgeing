package net.mofusya.mechanical_ageing.jei;

import mezz.jei.api.ingredients.IIngredientType;
import net.mofusya.mechanical_ageing.matter.MatterStack;

public class MAgIngredient {
    public static final IIngredientType<MatterStack> MATTER_TYPE = () -> MatterStack.class;
}