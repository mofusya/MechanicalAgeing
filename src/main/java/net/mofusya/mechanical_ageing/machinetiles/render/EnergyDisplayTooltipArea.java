package net.mofusya.mechanical_ageing.machinetiles.render;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergyType;
import net.mofusya.mechanical_ageing.machinetiles.util.MouseUtil;
import net.mofusya.ornatelib.lang.UnLong;
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
public class EnergyDisplayTooltipArea {

    private static final Size SIZE = new Size(8, 72);
    private static final Size BAR_SIZE = new Size(7, 71);

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

    private List<Component> getTooltips(IEnergyStorage energyStorage) {
        String storedString;
        String maxStorageString;

        storedString = UnLong.addComma(String.valueOf(energyStorage.getEnergyStored()));
        maxStorageString = UnLong.addComma(String.valueOf(energyStorage.getMaxEnergyStored()));

        return List.of(
                Component.literal(storedString + this.type.suffix() + " /"),
                Component.literal(maxStorageString + this.type.suffix())
        );
    }

    public void renderTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, IEnergyStorage energyStorage) {
        if (MouseUtil.isMouseOver(mouseX, mouseY, this.x + 1, this.y + 1, BAR_SIZE.x(), BAR_SIZE.y()) || MouseUtil.isMouseOver(mouseX, mouseY, this.x + 11, this.y + 1, BAR_SIZE.x(), BAR_SIZE.y())) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, this.getTooltips(energyStorage), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    public void render(GuiGraphics guiGraphics, IEnergyStorage energyStorage) {
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

        //Write energy bar
        //guiGraphics.fillGradient(this.x + 1, this.y + 1, this.x + BAR_SIZE[0], this.y + BAR_SIZE[1], this.type.getColor(), this.type.getColor());
        guiGraphics.fillGradient(x + 1, y + 1 + (BAR_SIZE.y() - stored), x + BAR_SIZE.x(),
                y + BAR_SIZE.y(), this.type.getColor(), this.type.getGradientColor() == -404 ? this.type.getColor() : this.type.getGradientColor());
        guiGraphics.fillGradient(x + 11, y + 1 + (BAR_SIZE.y() - stored), x + 10 + BAR_SIZE.x(),
                y + BAR_SIZE.y(), this.type.getColor(), this.type.getGradientColor() == -404 ? this.type.getColor() : this.type.getGradientColor());
    }

    private static int getStored(IEnergyStorage energyStorage) {
        int stored;
        int energyStored = energyStorage.getEnergyStored();
        int maxEnergyStored = energyStorage.getMaxEnergyStored();

        stored = (int) ((energyStored / (float) maxEnergyStored) * BAR_SIZE.y());
        return stored;
    }
}