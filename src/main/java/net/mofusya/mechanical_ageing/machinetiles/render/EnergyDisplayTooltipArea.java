package net.mofusya.mechanical_ageing.machinetiles.render;


import net.flansflame.flans_star_forge.screens.helper.MouseUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
 *  Modified Version by: FlansFlame
 */
public class EnergyDisplayTooltipArea {

    private static final int[] BAR_SIZE = {6, 50};

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

    public void render(GuiGraphics guiGraphics, int energyStored, int maxEnergyStored) {
        int stored = (int) ((energyStored / (float) maxEnergyStored) * BAR_SIZE[1]);

        if (this.type.getGradientColor() == -404) {
            guiGraphics.fill(x + 1, y + 1 + (BAR_SIZE[1] - stored), x + BAR_SIZE[0],
                    y + BAR_SIZE[1], this.type.getColor());
            guiGraphics.fill(x + 11, y + 1 + (BAR_SIZE[1] - stored), x + BAR_SIZE[0],
                    y + BAR_SIZE[1], this.type.getColor());
        } else {
            guiGraphics.fillGradient(x + 1, y + 1 + (BAR_SIZE[1] - stored), x + BAR_SIZE[0],
                    y + BAR_SIZE[1], this.type.getColor(), this.type.getGradientColor());
            guiGraphics.fillGradient(x + 11, y + 1 + (BAR_SIZE[1] - stored), x + BAR_SIZE[0],
                    y + BAR_SIZE[1], this.type.getColor(), this.type.getGradientColor());
        }
    }
}