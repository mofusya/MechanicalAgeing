package net.mofusya.mechanical_ageing.machinetiles.baseclass;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import org.jetbrains.annotations.NotNull;

public class MachineScreen extends AbstractContainerScreen<MachineMenu> {
    private final MachineTile tile;

    public MachineScreen(MachineMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.tile = menu.blockEntity.getMachineTile();
    }

    @Override
    protected void init() {
        super.init();
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        this.tile.screenInit(x, y, this.getMenu(), this);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        this.tile.renderLabels(guiGraphics, x, y, mouseX, mouseY, this.getMenu(), this);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        this.tile.renderBg(guiGraphics, x, y, mouseX, mouseY, this.getMenu(), this);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public <T extends GuiEventListener & Renderable & NarratableEntry> @NotNull T addRenderableWidget(@NotNull T listener) {
        return super.addRenderableWidget(listener);
    }

    public MachineBlockEntity getBlockEntity() {
        return this.getMenu().blockEntity;
    }
}