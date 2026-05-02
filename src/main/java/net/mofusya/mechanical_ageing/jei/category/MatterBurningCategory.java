package net.mofusya.mechanical_ageing.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.jei.MAgCategory;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.recipes.recipe.FuelRecipe;
import net.mofusya.mechanical_ageing.recipes.recipe.MatterBurningRecipe;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;
import org.jetbrains.annotations.Nullable;

public class MatterBurningCategory extends MAgCategory<MatterBurningRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MechanicalAgeing.MOD_ID, "matter_burning");

    public static final RecipeType<MatterBurningRecipe> TYPE = new RecipeType<>(UID, MatterBurningRecipe.class);

    private final IDrawable backGround;
    private final IDrawable icon;

    public MatterBurningCategory(IGuiHelper helper) {
        super(helper);
        this.backGround = helper.createDrawable(JEI_FRAME, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(MAgMachines.BRICK_BURNING_CHAMBER.block()));
    }

    @Override
    public void getElements(MatterBurningRecipe recipe, ElementList elements) {
        elements.addMatter(recipe.getIngredient(), recipe.getResult());
    }

    @Override
    protected MachineTile getMachineTile() {
        return MAgMachines.BRICK_BURNING_CHAMBER.tile();
    }

    @Override
    public RecipeType<MatterBurningRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return this.getMachineTile().getDisplayName();
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
