package net.mofusya.mechanical_ageing.recipes.category;

import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.button.ButtonProperties;
import net.mofusya.mechanical_ageing.machinetiles.render.EnergyDisplayTooltipArea;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.recipes.recipe.TriDimCraftingRecipe;
import net.mofusya.mechanical_ageing.tiles.ModMachines;
import org.jetbrains.annotations.Nullable;

import static net.mofusya.mechanical_ageing.machinetiles.MachineTile.*;

public class TriDimCraftingCategory implements IRecipeCategory<TriDimCraftingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MechanicalAgeing.MOD_ID, "tri_dimensional_crafting");
    public static final ResourceLocation JEI_FRAME = new ResourceLocation(MechanicalAgeing.MOD_ID, "textures/gui/frame_jei.png");

    public static final RecipeType<TriDimCraftingRecipe> TYPE = new RecipeType<>(UID, TriDimCraftingRecipe.class);

    private final IDrawable backGround;
    private final IDrawable icon;

    public TriDimCraftingCategory(IGuiHelper helper) {
        this.backGround = helper.createDrawable(JEI_FRAME, 0, 0, 176, 85);
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

    @SuppressWarnings("removal")
    @Override
    public @Nullable IDrawable getBackground() {
        return this.backGround;
    }

    @Override
    public void draw(TriDimCraftingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        //Get frame texture
        MachineTile machineTile = ModMachines.TRI_DIM_CRAFTING_TABLE.tile();
        ResourceLocation bgTile = machineTile.getBgTileTypeFromLoc();
        RenderSystem.setShaderTexture(0, bgTile);

        //Write main bg
        guiGraphics.blit(bgTile, 0, 0, 176, 85, 0, 0, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);

        //Get frame texture
        ResourceLocation frame = new ResourceLocation(MechanicalAgeing.MOD_ID, "textures/gui/frame.png");
        RenderSystem.setShaderTexture(0, frame);

        //Write main screen
        guiGraphics.blit(frame, 0, 0, 64, 16, 176, 85, FRAME_WIDTH, FRAME_HEIGHT);

        //Get tile texture again
        RenderSystem.setShaderTexture(0, bgTile);

        //Write slots
        for (SlotProperties slotBuild : machineTile.getSlots()) {
            if (slotBuild.type().is(SlotType.SYSTEM)) {
                guiGraphics.blit(bgTile, slotBuild.x() - 1, slotBuild.y() - 1, 0, 18, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
            } else {
                if (slotBuild.type().is(SlotType.NORMAL)) {
                    guiGraphics.blit(bgTile, slotBuild.x() - 1, slotBuild.y() - 1, 0, 54, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                } else {
                    guiGraphics.blit(bgTile, slotBuild.x() - 1, slotBuild.y() - 1, 44, 54, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                }
            }
        }

        //Write buttons
        for (ButtonProperties buttonBuild : machineTile.getButtons()){
            switch (buttonBuild.type()){
                case SYSTEM -> guiGraphics.blit(bgTile, buttonBuild.x(), buttonBuild.y(), 18, 18, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                case EXTRACT_ONLY -> guiGraphics.blit(bgTile, buttonBuild.x(), buttonBuild.y(), 44, 36, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                case NORMAL -> guiGraphics.blit(bgTile, buttonBuild.x(), buttonBuild.y(), 0, 36, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
            }
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TriDimCraftingRecipe recipe, IFocusGroup focus) {
        for (int i = 0; i < 27; i++) {
            var slot = ModMachines.TRI_DIM_CRAFTING_TABLE.tile().getSlots().get(i);
            builder.addSlot(RecipeIngredientRole.INPUT, slot.x(), slot.y()).addIngredients(recipe.getIngredients().get(i));
        }
        var output = ModMachines.TRI_DIM_CRAFTING_TABLE.tile().getSlots().get(27);
        builder.addSlot(RecipeIngredientRole.OUTPUT, output.x(),output.y()).addItemStack(recipe.getResultItem(null));
    }
}
