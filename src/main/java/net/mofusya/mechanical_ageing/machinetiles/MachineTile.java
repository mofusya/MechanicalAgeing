package net.mofusya.mechanical_ageing.machinetiles;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineMenu;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineScreen;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergySlotList;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergySlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.render.EnergyDisplayTooltipArea;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class MachineTile {
    private final Supplier<RegistryObject<Block>> block;
    private final Supplier<RegistryObject<BlockEntityType<MachineBlockEntity>>> blockEntity;
    private final Supplier<RegistryObject<MenuType<MachineMenu>>> menu;
    private final ResourceLocation id;

    public MachineTile(ResourceLocation id) {
        this.id = id;
        this.block = MachineRegister.getBlock(id);
        this.blockEntity = MachineRegister.getBlockEntity(id);
        this.menu = MachineRegister.getMenu(id);
    }

    /*Overrides*/
    public abstract void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity);

    public Component getDisplayName() {
        return Component.translatable("block." + this.id.getNamespace() + "." + this.id.getPath());
    }

    public SlotList getSlots(SlotList slots) {
        return slots.create(7, 7, itemStack -> false, SlotType.SYSTEM);
    }

    public EnergySlotList getEnergySlots(EnergySlotList slots) {
        return slots;
    }

    public int getDataSlotCount() {
        return 0;
    }

    public Item.Properties getItemBuild() {
        return new Item.Properties();
    }

    public BlockBehaviour.Properties getBlockBuild() {
        return BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(DyeColor.GRAY);
    }

    public BgTileType getBgTileType() {
        return BgTileType.VANILLA;
    }

    public ResourceLocation getBgTileTypeFromLoc() {
        ResourceLocation id = this.getBgTileType().getId();
        return new ResourceLocation(id.getNamespace(), "textures/gui/bg_" + id.getPath() + ".png");
    }

    private static final int FRAME_WIDTH = 304;
    private static final int FRAME_HEIGHT = 182;
    public static final int BG_TILE_WIDTH = 144;
    public static final int BG_TILE_HEIGHT = 72;

    private List<EnergyDisplayTooltipArea> energyTooltips;

    public void screenInit(int x, int y, MachineMenu menu, MachineScreen screen) {
        ResourceLocation bgTile = this.getBgTileTypeFromLoc();

        this.energyTooltips = new ArrayList<>();

        for (EnergySlotProperties energy : this.getEnergySlots()) {
            this.energyTooltips.add(new EnergyDisplayTooltipArea(x + energy.x(), y + 6, energy.energyType(), bgTile));
        }
    }

    public void renderLabels(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, MachineMenu menu, MachineScreen screen) {
        for (int i = 0; i < this.getEnergySlots().size(); i++) {
            EnergyDisplayTooltipArea tooltip = this.energyTooltips.get(i);
            tooltip.renderTooltips(guiGraphics, mouseX, mouseY, x, y, menu.blockEntity.getEnergyStorage(i).getEnergyStored(), menu.blockEntity.getEnergyStorage(i).getMaxEnergyStored());
        }
    }

    public void renderBg(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, MachineMenu menu, MachineScreen screen) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        //Get tile texture
        ResourceLocation bgTile = this.getBgTileTypeFromLoc();
        RenderSystem.setShaderTexture(0, bgTile);

        //Write main bg
        guiGraphics.blit(bgTile, x + 4, y + 4, 168, 158, 0, 0, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
        //Write left tab bg
        guiGraphics.blit(bgTile, x - 61, y + 19, 61, 106, 0, 0, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
        //Write right tab bg
        guiGraphics.blit(bgTile, x + 176, y + 19, 61, 106, 0, 0, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
        //Write label bg
        guiGraphics.blit(bgTile, x + 50, y - 10, 76, 10, 0, 0, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);

        //Get frame texture
        ResourceLocation frame = new ResourceLocation(MechanicalAgeing.MOD_ID, "textures/gui/frame.png");
        RenderSystem.setShaderTexture(0, frame);

        //Write main screen
        guiGraphics.blit(frame, x, y, 64, 16, 176, 166, FRAME_WIDTH, FRAME_HEIGHT);
        //Write left tab
        guiGraphics.blit(frame, x - 64, y, 0, 16, 64, 166, FRAME_WIDTH, FRAME_HEIGHT);
        //Write right tab
        guiGraphics.blit(frame, x + 176, y, 240, 16, 64, 166, FRAME_WIDTH, FRAME_HEIGHT);
        //Write label tab
        guiGraphics.blit(frame, x - 64, y - 16, 0, 0, FRAME_WIDTH, 16, FRAME_WIDTH, FRAME_HEIGHT);

        //Get tile texture again
        RenderSystem.setShaderTexture(0, bgTile);

        //Write slots
        for (SlotProperties slotBuild : this.getSlots()) {

            if (slotBuild.type().is(SlotType.SYSTEM)) {
                guiGraphics.blit(bgTile, x + slotBuild.x() - 1, y + slotBuild.y() - 1, 0, 18, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
            } else {
                if (slotBuild.type().is(SlotType.NORMAL)) {
                    guiGraphics.blit(bgTile, x + slotBuild.x() - 1, y + slotBuild.y() - 1, 0, 54, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                } else {
                    guiGraphics.blit(bgTile, x + slotBuild.x() - 1, y + slotBuild.y() - 1, 44, 54, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                }
            }
        }
        //Write inventory slots
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                guiGraphics.blit(bgTile, x + (8 + l * 18) - 1, y + (84 + i * 18) - 1, 0, 54, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
            }
        }
        //Write hotbar slots
        for (int i = 0; i < 9; ++i) {
            guiGraphics.blit(bgTile, x + (8 + i * 18) - 1, y + (142) - 1, 0, 54, 18, 18, BG_TILE_WIDTH, BG_TILE_HEIGHT);
        }

        //Write energy slots
        for (int i = 0; i < this.getEnergySlots().size(); i++) {
            EnergyDisplayTooltipArea tooltip = this.energyTooltips.get(i);
            tooltip.render(guiGraphics, menu.blockEntity.getEnergyStorage(i));
        }

        //Write machine name
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, this.getDisplayName(), x + screen.getXSize() / 2, y - 9, 4210752);
    }

    /*Getter setters*/
    public final RegistryObject<Block> getBlock() {
        return this.block.get();
    }

    public final RegistryObject<BlockEntityType<MachineBlockEntity>> getBlockEntity() {
        return this.blockEntity.get();
    }

    public final RegistryObject<MenuType<MachineMenu>> getMenu() {
        return this.menu.get();
    }

    public final ResourceLocation getId() {
        return this.id;
    }

    public final SlotList getSlots() {
        SlotList toReturn = this.getSlots(new SlotList());
        if (toReturn.isEmpty()) return MachineTile.this.getSlots(new SlotList());
        for (EnergySlotProperties energy : this.getEnergySlots()) {
            if (energy.maxExtract() <= 0) {
                toReturn.create(energy.x() + 1, 62, itemStack -> false, SlotType.NORMAL);
            } else {
                toReturn.create(energy.x() + 1, 62, itemStack -> true, SlotType.EXTRACT_ONLY);
            }
        }
        return toReturn;
    }

    public final EnergySlotList getEnergySlots() {
        EnergySlotList toReturn = this.getEnergySlots(new EnergySlotList());
        if (toReturn.isEmpty()) return MachineTile.this.getEnergySlots(new EnergySlotList());
        return toReturn;
    }
}