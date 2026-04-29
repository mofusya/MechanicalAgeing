package net.mofusya.mechanical_ageing.jei;

import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.button.ButtonProperties;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergySlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.fluid.FluidSlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.recipes.MAgRecipe;

import java.util.ArrayList;
import java.util.List;

import static net.mofusya.mechanical_ageing.machinetiles.MachineTile.*;

public abstract class MAgCategory<T extends MAgRecipe> implements IRecipeCategory<T> {
    protected static final ResourceLocation JEI_FRAME = new ResourceLocation(MechanicalAgeing.MOD_ID, "textures/gui/frame_jei.png");

    public MAgCategory(IGuiHelper helper) {
    }

    public void getElements(T recipe, ElementList elements) {
    }

    public final ElementList getElements(T recipe) {
        ElementList toReturn = new ElementList();
        this.getElements(recipe, toReturn);
        return toReturn;
    }

    protected abstract MachineTile getMachineTile();

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        //Get frame texture
        MachineTile machineTile = this.getMachineTile();
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

        //Write energy slot
        for (EnergySlotProperties slotBuild : machineTile.getEnergySlots()) {
            if (slotBuild.maxReceive() > 0) {
                guiGraphics.blit(bgTile, slotBuild.x(), 6, 36, 20, 8, 52, MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
                guiGraphics.blit(bgTile, slotBuild.x() + 10, 6, 36, 20, 8, 52, MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
            } else {
                guiGraphics.blit(bgTile, slotBuild.x(), 6, 62, 20, 8, 52, MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
                guiGraphics.blit(bgTile, slotBuild.x() + 10, 6, 62, 20, 8, 52, MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
            }
        }

        //Write matter slots
        for (MatterSlotProperties slotBuild : machineTile.getMatterSlots()) {
            if (slotBuild.maxReceive().isGreaterThan(0)) {
                guiGraphics.blit(bgTile, slotBuild.x(), slotBuild.y(), 18, 36, 18, 36, MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
            } else {
                guiGraphics.blit(bgTile, slotBuild.x(), slotBuild.y(), 44, 0, 18, 36, MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
            }
        }

        //Write fluid slot
        FluidSlotProperties fluidSlotProperties = machineTile.getFluidSlot();
        if (fluidSlotProperties != null) {
            guiGraphics.blit(bgTile, fluidSlotProperties.x(), fluidSlotProperties.y(), 36, 20, 8, 52, BG_TILE_WIDTH, BG_TILE_HEIGHT);
        }

        //Write buttons
        for (ButtonProperties buttonBuild : machineTile.getButtons()) {
            switch (buttonBuild.type()) {
                case SYSTEM ->
                        guiGraphics.blit(bgTile, buttonBuild.x(), buttonBuild.y(), 18, 18, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                case EXTRACT_ONLY ->
                        guiGraphics.blit(bgTile, buttonBuild.x(), buttonBuild.y(), 44, 36, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                case NORMAL ->
                        guiGraphics.blit(bgTile, buttonBuild.x(), buttonBuild.y(), 0, 36, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
            }
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focus) {
        var elements = this.getElements(recipe);
        var ingredients = elements.ingredients;
        var itemStacks = elements.itemStacks;
        var matterStacks = elements.matterStacks;

        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).isEmpty()) continue;

            var slot = this.getMachineTile().getSlots().get(i);
            builder.addSlot(slot.type().is(SlotType.NORMAL) ? RecipeIngredientRole.INPUT : RecipeIngredientRole.OUTPUT,
                    slot.x(), slot.y()).addIngredients(ingredients.get(i));
        }

        for (int i = 0; i < itemStacks.size(); i++) {
            if (itemStacks.get(i).isEmpty()) continue;

            var slot = this.getMachineTile().getSlots().get(i);
            builder.addSlot(slot.type().is(SlotType.NORMAL) ? RecipeIngredientRole.INPUT : RecipeIngredientRole.OUTPUT,
                    slot.x(), slot.y()).addItemStack(itemStacks.get(i));
        }

        for (int i = 0; i < matterStacks.size(); i++) {
            var slot = this.getMachineTile().getMatterSlots().get(i);
            builder.addSlot(slot.maxExtract().isGreaterThan(0) ? RecipeIngredientRole.INPUT : RecipeIngredientRole.OUTPUT,
                    slot.x() + 1, slot.y() + 1).addIngredient(MAgIngredient.MATTER_TYPE, matterStacks.get(i));
            builder.addSlot(slot.maxExtract().isGreaterThan(0) ? RecipeIngredientRole.INPUT : RecipeIngredientRole.OUTPUT,
                    slot.x() + 1, slot.y() + 17).addIngredient(MAgIngredient.MATTER_TYPE, matterStacks.get(i));
        }
    }

    public static class ElementList {
        private final ArrayList<ItemStack> itemStacks = new ArrayList<>();
        private final ArrayList<Ingredient> ingredients = new ArrayList<>();
        private final ArrayList<MatterStack> matterStacks = new ArrayList<>();
        private final ArrayList<EnergyStack> energyStacks = new ArrayList<>();
        private FluidStack fluidStack = null;

        private ElementList() {
        }

        public void addItem(List<ItemStack> itemStacks) {
            this.itemStacks.addAll(itemStacks);

            for (int i = 0; i < itemStacks.size(); i++) {
                this.ingredients.add(Ingredient.EMPTY);
            }
        }

        public void addIngredient(List<Ingredient> ingredients) {
            this.ingredients.addAll(ingredients);

            for (int i = 0; i < ingredients.size(); i++) {
                this.itemStacks.add(ItemStack.EMPTY);
            }
        }

        public void addMatter(List<MatterStack> matterStacks) {
            this.matterStacks.addAll(matterStacks);
        }

        public void addEnergy(List<EnergyStack> energyStacks) {
            this.energyStacks.addAll(energyStacks);
        }

        public void addItem(ItemStack... itemStacks) {
            this.addItem(List.of(itemStacks));
        }

        public void addIngredient(Ingredient... ingredients) {
            this.addIngredient(List.of(ingredients));
        }

        public void addMatter(MatterStack... matterStacks) {
            this.addMatter(List.of(matterStacks));
        }

        public void addEnergy(EnergyStack... energyStacks) {
            this.addEnergy(List.of(energyStacks));
        }

        public void set(int slot, ItemStack itemStack) {
            this.itemStacks.set(slot, itemStack);
            this.ingredients.set(slot, Ingredient.EMPTY);
        }

        public void set(int slot, Ingredient ingredient) {
            this.ingredients.set(slot, ingredient);
            this.itemStacks.set(slot, ItemStack.EMPTY);
        }

        public void set(int slot, MatterStack matterStack) {
            this.matterStacks.set(slot, matterStack);
        }

        public void set(int slot, EnergyStack energyStack) {
            this.energyStacks.set(slot, energyStack);
        }

        public void set(FluidStack fluidStack) {
            this.fluidStack = fluidStack;
        }
    }
}