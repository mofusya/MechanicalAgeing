package net.mofusya.mechanical_ageing.machinetiles.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.flansflame.flans_star_forge.screens.helper.MouseUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.matter.IMatterHandler;
import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.mechanical_ageing.util.SeptiLongHelper;
import net.mofusya.ornatelib.lang.SeptiLong;

import java.util.List;
import java.util.Optional;

public class MatterDisplayTooltipArea {
    private static final int[] BAR_SIZE = {16, 34};
    private static final int[] WATER_TEXTURE_SIZE = {16, 16};

    private final int x;
    private final int y;
    private final ResourceLocation bgTile;

    public MatterDisplayTooltipArea(int x, int y, ResourceLocation bgTile) {
        this.x = x;
        this.y = y;
        this.bgTile = bgTile;
    }

    public void renderTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, IMatterHandler matterHandler, int slot) {
        if (MouseUtil.isMouseOver(mouseX, mouseY, this.x + 1, this.y + 1, BAR_SIZE[0], BAR_SIZE[1])) {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            if (player.isShiftKeyDown()) {
                var type = matterHandler.getStored(slot).getType();
                guiGraphics.renderTooltip(Minecraft.getInstance().font,
                        List.of(Component.translatable(type == null ? "block.minecraft.air" : type.getTranslationId()),
                                Component.literal(" §8- " + matterHandler.getStored(slot).getAmount() + (type == null ? "mB" : type.getSuffix()) + " §r§f/"),
                                Component.literal(" §8- " + matterHandler.getMaxStored(slot) + (type == null ? "mB" : type.getSuffix()))
                        ), Optional.empty(), mouseX - x, mouseY - y);
            } else {
                var type = matterHandler.getStored(slot).getType();
                guiGraphics.renderTooltip(Minecraft.getInstance().font,
                        List.of(Component.translatable(type == null ? "block.minecraft.air" : type.getTranslationId()),
                                Component.literal(" §8- " + SeptiLongHelper.convertToStringAndAddSuffix(matterHandler.getStored(slot).getAmount()) + (type == null ? "mB" : type.getSuffix()) + " /"),
                                Component.literal(" §8- " + SeptiLongHelper.convertToStringAndAddSuffix(matterHandler.getMaxStored(slot)) + (type == null ? "mB" : type.getSuffix()))
                        ), Optional.empty(), mouseX - x, mouseY - y);
            }
        }
    }

    public void render(GuiGraphics guiGraphics, IMatterHandler matterHandler, int slot) {
        SeptiLong storedMatter = matterHandler.getStored(slot).getAmount();
        SeptiLong maxCapacity = matterHandler.getMaxStored(slot);
        MatterType matterType = matterHandler.getStored(slot).getType();
        int stored = (int) (storedMatter.copy().divideAndGetFloat(maxCapacity) * BAR_SIZE[1]);

        //Get tile texture
        RenderSystem.setShaderTexture(0, this.bgTile);

        //Write slots
        if (matterHandler.canReceive(slot)) {
            guiGraphics.blit(this.bgTile, this.x, this.y, 18, 36, 18, 36, MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
        } else {
            guiGraphics.blit(this.bgTile, this.x, this.y, 44, 0, 18, 36, MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
        }

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

            //Get matter texture values
            final int posX = this.x + 1;
            final int posY = this.y + 1 + BAR_SIZE[1] - stored;

            //Write matter texture
            guiGraphics.blit(waterTexture, posX, posY, 0, 0, BAR_SIZE[0], stored, WATER_TEXTURE_SIZE[0], WATER_TEXTURE_SIZE[1]);
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }
    }
}
