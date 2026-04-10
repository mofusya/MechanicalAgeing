package net.mofusya.mechanical_ageing.machinetiles.render;


import com.mojang.blaze3d.systems.RenderSystem;
import net.flansflame.flans_star_forge.screens.helper.MouseUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergyType;
import net.mofusya.ornatelib.lang.SeptiLong;

import java.util.List;
import java.util.Optional;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  https://github.com/BluSunrize/ImmersiveEngineering/blob/1.19.2/LICENSE
 *
 *  Modified Version by: Kaupenjoe
 *  Modified Version by: Omotinomoti
 */
public class EnergyDisplayTooltipArea {

    private static final int[] BAR_SIZE = {7, 51};

    private final int x;
    private final int y;
    private final EnergyType<?> type;
    private final ResourceLocation bgTile;

    public EnergyDisplayTooltipArea(int x, int y, EnergyType<?> type, ResourceLocation bgTile) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.bgTile = bgTile;
    }

    private List<Component> getTooltips(int energyStored, int maxEnergyStored) {
        return List.of(Component.literal(SeptiLong.addComma(String.valueOf(energyStored)) + " /"), Component.literal(SeptiLong.addComma(String.valueOf(maxEnergyStored)) + " " + this.type.suffix()));
    }

    public void renderTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, int energyStored, int maxEnergyStored) {
        if (MouseUtil.isMouseOver(mouseX, mouseY, this.x + 1, this.y + 1, BAR_SIZE[0], BAR_SIZE[1]) || MouseUtil.isMouseOver(mouseX, mouseY, this.x + 11, this.y + 1, BAR_SIZE[0], BAR_SIZE[1])) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, this.getTooltips(energyStored, maxEnergyStored), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    public void render(GuiGraphics guiGraphics, IEnergyStorage energyStorage) {
        int energyStored = energyStorage.getEnergyStored();
        int maxEnergyStored = energyStorage.getMaxEnergyStored();
        int stored = (int) ((energyStored / (float) maxEnergyStored) * BAR_SIZE[1]);

        //Get tile texture
        RenderSystem.setShaderTexture(0, this.bgTile);

        //Write energy slot
        if (energyStorage.canReceive()) {
            guiGraphics.blit(this.bgTile, this.x, this.y, 36, 20, 8, 52, MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
            guiGraphics.blit(this.bgTile, this.x + 10, this.y, 36, 20, 8, 52, MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
        } else {
            guiGraphics.blit(this.bgTile, this.x, this.y, 62, 20, 8, 52, MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
            guiGraphics.blit(this.bgTile, this.x + 10, this.y, 62, 20, 8, 52, MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
        }

        //Write energy bar
        //guiGraphics.fillGradient(this.x + 1, this.y + 1, this.x + BAR_SIZE[0], this.y + BAR_SIZE[1], this.type.getColor(), this.type.getColor());
        guiGraphics.fillGradient(x + 1, y + 1 + (BAR_SIZE[1] - stored), x + BAR_SIZE[0],
                y + BAR_SIZE[1], this.type.getColor(), this.type.getGradientColor() == -404 ? this.type.getColor() : this.type.getGradientColor());
        guiGraphics.fillGradient(x + 11, y + 1 + (BAR_SIZE[1] - stored), x + 10 + BAR_SIZE[0],
                y + BAR_SIZE[1], this.type.getColor(), this.type.getGradientColor() == -404 ? this.type.getColor() : this.type.getGradientColor());
    }
}