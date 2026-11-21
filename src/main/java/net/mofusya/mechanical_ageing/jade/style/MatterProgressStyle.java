package net.mofusya.mechanical_ageing.jade.style;

import net.kairayk.yklibrary.util.VectorColor;
import net.kairayk.yklibrary.util.scrpos.ScrPosF;
import net.kairayk.yklibrary.util.scrpos.ScrPosI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.C;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IProgressStyle;

public class MatterProgressStyle implements IProgressStyle {

    private static final ResourceLocation BAR_TEXTURE = new ResourceLocation("textures/block/water_still.png");

    private final VectorColor color;
    private int textColor = 0xFF888888;

    public MatterProgressStyle(VectorColor color) {
        this.color = color;
    }

    @Override
    public final IProgressStyle color(int startColor, int endColor) {
        return this;
    }

    @Override
    public IProgressStyle textColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    @Override
    public IProgressStyle vertical(boolean b) {
        return this;
    }

    @Override
    public IProgressStyle overlay(IElement iElement) {
        return this;
    }

    @Override
    public void render(GuiGraphics guiGraphics, float pX, float pY, float pWidth, float pHeight, float progress, @Nullable Component text) {
        ScrPosI pos = ScrPosI.of(pX, pY);
        ScrPosI size = ScrPosI.of(pWidth, pHeight).mul(new ScrPosF(progress, 1f));

        guiGraphics.setColor(this.color.r(), this.color.g(), this.color.b(), this.color.a());
        guiGraphics.blit(BAR_TEXTURE, pos.getX(), pos.getY(), 0, 0, size.getX(), size.getY(), 16, 512);
        guiGraphics.setColor(1f, 1f, 1f, 1f);

        if (text != null) {
            ScrPosI textPos = pos.add(ScrPosI.of(1, 1));
            guiGraphics.drawString(Minecraft.getInstance().font, text, textPos.getX(), textPos.getY(), this.textColor);
        }
    }
}
