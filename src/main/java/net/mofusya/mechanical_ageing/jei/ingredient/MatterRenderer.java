package net.mofusya.mechanical_ageing.jei.ingredient;

import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.matter.MatterType;

import java.util.List;

public class MatterRenderer implements IIngredientRenderer<MatterStack> {
    @Override
    public void render(GuiGraphics guiGraphics, MatterStack matterStack) {
        MatterType matterType = matterStack.getType();

        //Null check
        if (matterType != null) {
            //Get colors
            final int color = matterType.getColor();
            final float red = (color >> 16 & 0xFF) / 255.0F;
            final float green = (color >> 8 & 0xFF) / 255.0F;
            final float blue = (color & 0xFF) / 255.0F;

            //Get water texture
            ResourceLocation waterTexture = new ResourceLocation("textures/block/water_still.png");
            RenderSystem.setShaderTexture(0, waterTexture);
            RenderSystem.setShaderColor(red, green, blue, 0.75f);

            //Write matter texture
            guiGraphics.blit(waterTexture, 0, 0, 0, 0, 16, 16, 16, 16);
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }
    }

    @SuppressWarnings("removel")
    @Override
    public List<Component> getTooltip(MatterStack matterStack, TooltipFlag tooltipFlag) {
        if (matterStack.getType() == null) return List.of();
        return List.of(Component.translatable(matterStack.getType().getTranslationId()));
    }
}