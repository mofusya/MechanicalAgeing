package net.mofusya.mechanical_ageing.machinetiles.render;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.util.MouseUtil;
import net.mofusya.mechanical_ageing.machinetiles.watt.IWattEnergyStorage;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.SeptiLongHelper;
import net.mofusya.ornatelib.util.screen.Size;

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
public class WattEnergyDisplayTooltipArea {

    private static final Size SIZE = new Size(8, 72);
    private static final Size BAR_SIZE = new Size(7, 71);

    private final int x;
    private final int y;
    private final ResourceLocation bgTile;

    public WattEnergyDisplayTooltipArea(int x, int y, ResourceLocation bgTile) {
        this.x = x;
        this.y = y;
        this.bgTile = bgTile;
    }

    private List<Component> getTooltips(IWattEnergyStorage wattEnergyStorage) {
        var storedString = wattEnergyStorage.getStored().toComponent(true, false);
        var maxStorageString = wattEnergyStorage.getCapacity().toComponent(true, false);

        return List.of(
                storedString.append("mW /"),
                maxStorageString.append("mW")
        );
    }

    public void renderTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, IWattEnergyStorage energyStorage) {
        if (MouseUtil.isMouseOver(mouseX, mouseY, this.x + 1, this.y + 1, BAR_SIZE.x(), BAR_SIZE.y()) || MouseUtil.isMouseOver(mouseX, mouseY, this.x + 11, this.y + 1, BAR_SIZE.x(), BAR_SIZE.y())) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, this.getTooltips(energyStorage), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    public void render(GuiGraphics guiGraphics, IWattEnergyStorage energyStorage) {
        int stored = getStored(energyStorage);

        //Get tile texture
        RenderSystem.setShaderTexture(0, this.bgTile);

        //Write energy slot
        if (energyStorage.canReceive()) {
            guiGraphics.blit(this.bgTile, this.x, this.y, 36, 0, SIZE.x(), SIZE.y(), MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
            guiGraphics.blit(this.bgTile, this.x + 10, this.y, 36, 0, SIZE.x(), SIZE.y(), MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
        } else {
            guiGraphics.blit(this.bgTile, this.x, this.y, 62, 0, SIZE.x(), SIZE.y(), MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
            guiGraphics.blit(this.bgTile, this.x + 10, this.y, 62, 0, SIZE.x(), SIZE.y(), MachineTile.BG_TILE_WIDTH, MachineTile.BG_TILE_HEIGHT);
        }

        int baseColor = 0xD0ffee58;
        int gradientColor = 0xD02baf2b;

        //Write energy bar
        guiGraphics.fillGradient(x + 1, y + 1 + (BAR_SIZE.y() - stored), x + BAR_SIZE.x(),
                y + BAR_SIZE.y(), baseColor, gradientColor);
        guiGraphics.fillGradient(x + 11, y + 1 + (BAR_SIZE.y() - stored), x + 10 + BAR_SIZE.x(),
                y + BAR_SIZE.y(), baseColor, gradientColor);
    }

    private static int getStored(IWattEnergyStorage energyStorage) {
        UnLong energyStored = energyStorage.getStored();
        UnLong maxEnergyStored = energyStorage.getCapacity();

        return (int) (energyStored.simulateDivAndGetFloat(maxEnergyStored) * BAR_SIZE.y());
    }
}