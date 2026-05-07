package net.mofusya.mechanical_ageing.machinetiles;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.items.implemts.IMachineUpgradeArchive;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowList;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowProperties;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlock;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineMenu;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineScreen;
import net.mofusya.mechanical_ageing.machinetiles.button.ButtonList;
import net.mofusya.mechanical_ageing.machinetiles.button.OnButtonPressPacket;
import net.mofusya.mechanical_ageing.machinetiles.direction.DirectionType;
import net.mofusya.mechanical_ageing.machinetiles.direction.MachineDirectionHandler;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergySlotList;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergySlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.fluid.FluidSlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.render.EnergyDisplayTooltipArea;
import net.mofusya.mechanical_ageing.machinetiles.render.FluidTankRenderer;
import net.mofusya.mechanical_ageing.machinetiles.render.MatterDisplayTooltipArea;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotProperties;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.machinetiles.util.MouseUtil;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.tag.MAgTags;
import net.mofusya.mechanical_ageing.tiles.BgTileType;
import net.mofusya.mechanical_ageing.util.annotations.FieldsAreNonNullByDefault;
import net.mofusya.ornatelib.lang.SeptiLong;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@FieldsAreNonNullByDefault
@MethodsReturnNonnullByDefault
public abstract class MachineTile {
    private final Supplier<RegistryObject<Block>> block;
    private final Supplier<RegistryObject<BlockEntityType<MachineBlockEntity>>> blockEntity;
    private final Supplier<RegistryObject<MenuType<MachineMenu>>> menu;
    private final ResourceLocation id;
    @Nullable
    private OnButtonPressPacket buttonPacket;
    private OnButtonPressPacket ioButtonPacket;

    public MachineTile(ResourceLocation id) {
        this.id = id;
        this.block = MachineRegister.getBlock(id);
        this.blockEntity = MachineRegister.getBlockEntity(id);
        this.menu = MachineRegister.getMenu(id);
    }

    /*Overrides*/
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        MachineDirectionHandler directionHandler = blockEntity.getDirectionHandler();

        if (matterHandler != null) {
            for (int i = 0; i < matterHandler.size(); i++) {
                if (matterHandler.canReceive(i)) {
                    Direction direction = getCombinedDirection(state.getValue(MachineBlock.FACING), directionHandler.getMatterDirection(i));
                    if (direction == null) continue;

                    BlockPos pPos = pos.relative(direction, 1);
                    BlockEntity pBlockEntity = level.getBlockEntity(pPos);

                    if (pBlockEntity instanceof MachineBlockEntity pMachine) {
                        MatterHandler pMatterHandler = (MatterHandler) pMachine.getMatterHandler();
                        if (pMatterHandler == null) continue;

                        for (int j = 0; j < pMatterHandler.size(); j++) {
                            int finalI = i;
                            int finalJ = j;
                            pMachine.getCapability(MAgCapabilities.MATTER, direction.getOpposite()).ifPresent(handler -> {
                                SeptiLong space = matterHandler.getSpace(finalI);

                                if (space.isSmallerOrSameThan(0)) return;

                                MatterStack maxExtract = handler.extract(new MatterStack(null, space), finalJ, true);
                                if (maxExtract.getAmount().isGreaterThan(0)) {
                                    MatterStack receive = matterHandler.receive(maxExtract, finalI).copy();
                                    handler.extract(receive, finalJ);
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    public MutableComponent getDisplayName() {
        return Component.translatable("block." + this.id.getNamespace() + "." + this.id.getPath());
    }

    public SlotList getSlots(SlotList slots) {
        return slots.create(7, 7, itemStack -> itemStack.is(MAgTags.Items.MACHINE_UPGRADE_ARCHIVE), SlotType.SYSTEM);
    }

    public int getUpgradeArchiveSlot() {
        return 0;
    }

    public EnergySlotList getEnergySlots(EnergySlotList slots) {
        return slots;
    }

    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return slots;
    }

    @Nullable
    public FluidSlotProperties getFluidSlot() {
        return null;
    }

    public ButtonList getButtons(ButtonList list) {
        return list;
    }

    public ArrowList getArrows(ArrowList list) {
        return list;
    }

    public int getDataSlotCount() {
        return 0;
    }

    public int getDefaultDataSlotAmount(int index) {
        return 0;
    }

    public Item.Properties getItemBuild() {
        return new Item.Properties();
    }

    public BlockBehaviour.Properties getBlockBuild() {
        return BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(DyeColor.GRAY);
    }

    public IBgTileType getBgTileType() {
        return BgTileType.VANILLA;
    }

    public final ResourceLocation getBgTileTypeLoc() {
        ResourceLocation id = this.getBgTileType().getId();
        return new ResourceLocation(id.getNamespace(), "textures/gui/bg_" + id.getPath() + ".png");
    }

    public static final int FRAME_WIDTH = 304;
    public static final int FRAME_HEIGHT = 182;
    public static final int BG_TILE_WIDTH = 256;
    public static final int BG_TILE_HEIGHT = 256;

    private List<EnergyDisplayTooltipArea> energyTooltips;
    private List<MatterDisplayTooltipArea> matterTooltips;
    @Nullable
    private FluidTankRenderer fluidTankRenderer = null;

    public void screenInit(int x, int y, MachineMenu menu, MachineScreen screen) {
        ResourceLocation bgTile = this.getBgTileTypeLoc();

        this.energyTooltips = new ArrayList<>();
        for (EnergySlotProperties energy : this.getEnergySlots()) {
            this.energyTooltips.add(new EnergyDisplayTooltipArea(x + energy.x(), y + 6, energy.energyType(), bgTile));
        }

        this.matterTooltips = new ArrayList<>();
        for (MatterSlotProperties matter : this.getMatterSlots()) {
            this.matterTooltips.add(new MatterDisplayTooltipArea(x + matter.x(), y + matter.y(), bgTile));
        }

        var fluidTankProperties = this.getFluidSlot();
        if (fluidTankProperties != null) {
            this.fluidTankRenderer = new FluidTankRenderer(fluidTankProperties.capacity(), true, 6, 50);
        }

        if (this.buttonPacket != null) {
            for (int i = 0; i < this.getButtons().size(); i++) {
                var button = this.getButtons().get(i);
                int finalI = i;
                if (button.type().is(SlotType.SYSTEM)) {
                    screen.addRenderableWidget(new ImageButton(x + button.x(), y + button.y(), 18, 18, 18, 18, 0, bgTile, BG_TILE_WIDTH, BG_TILE_HEIGHT, pButton -> buttonPacket.send2Server(finalI, menu.blockEntity.getBlockPos())));
                } else if (button.type().is(SlotType.NORMAL)) {
                    screen.addRenderableWidget(new ImageButton(x + button.x(), y + button.y(), 18, 18, 0, 36, 0, bgTile, BG_TILE_WIDTH, BG_TILE_HEIGHT, pButton -> buttonPacket.send2Server(finalI, menu.blockEntity.getBlockPos())));
                } else {
                    screen.addRenderableWidget(new ImageButton(x + button.x(), y + button.y(), 18, 18, 44, 36, 0, bgTile, BG_TILE_WIDTH, BG_TILE_HEIGHT, pButton -> buttonPacket.send2Server(finalI, menu.blockEntity.getBlockPos())));
                }
            }
        }

        ResourceLocation ioButton = new ResourceLocation(MAg.MOD_ID, "textures/gui/io_buttons.png");

        for (int i = 0; i < this.getMatterSlots().size(); i++) {
            var matter = this.getMatterSlots().get(i);
            int modX = x - 64;
            int modY = y + 16;

            int finalI = i;
            screen.addRenderableWidget(new ImageButton(modX + 6, modY + 7 + (i * 14), 12, 12, 36, 0, 0, ioButton, pButton -> ioButtonPacket.send2Server(finalI + this.getSlots().size(), menu.blockEntity.getBlockPos())));
        }
    }

    public void renderLabels(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, MachineMenu menu, MachineScreen screen) {
        for (int i = 0; i < this.getEnergySlots().size(); i++) {
            EnergyDisplayTooltipArea tooltip = this.energyTooltips.get(i);
            tooltip.renderTooltips(guiGraphics, mouseX, mouseY, x, y, menu.blockEntity.getEnergyStorage(i).getEnergyStored(), menu.blockEntity.getEnergyStorage(i).getMaxEnergyStored());
        }

        for (int i = 0; i < this.getMatterSlots().size(); i++) {
            var tooltip = this.matterTooltips.get(i);
            tooltip.renderTooltips(guiGraphics, mouseX, mouseY, x, y, menu.blockEntity.getMatterHandler(), i);
        }

        var fluidTankProperties = this.getFluidSlot();
        FluidTank fluidTank = menu.blockEntity.getFluidTank();
        if (fluidTank != null && fluidTankProperties != null && this.fluidTankRenderer != null &&
                isMouseAboveArea(mouseX, mouseY, x, y, fluidTankProperties.x() + 1, fluidTankProperties.y() + 1, this.fluidTankRenderer)) {
            guiGraphics.renderTooltip(screen.getMinecraft().font, this.fluidTankRenderer.getTooltip(
                    fluidTank.getFluid(), TooltipFlag.Default.NORMAL), Optional.empty(), mouseX - x, mouseY - y
            );
        }
    }

    public void renderBg(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, MachineMenu menu, MachineScreen screen) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        //Get tile texture
        ResourceLocation bgTile = this.getBgTileTypeLoc();
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
        ResourceLocation frame = new ResourceLocation(MAg.MOD_ID, "textures/gui/frame.png");
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

        //Write matter slots
        if (menu.blockEntity.getMatterHandler() != null) {
            for (int i = 0; i < this.getMatterSlots().size(); i++) {
                var tooltip = this.matterTooltips.get(i);
                tooltip.render(guiGraphics, menu.blockEntity.getMatterHandler(), i);
            }
        }

        //Write fluid slot
        var fluidTankProperties = this.getFluidSlot();
        FluidTank fluidTank = menu.blockEntity.getFluidTank();
        if (fluidTank != null && this.fluidTankRenderer != null && fluidTankProperties != null) {
            guiGraphics.blit(bgTile, x + fluidTankProperties.x(), y + fluidTankProperties.y(), 36, 20, 8, 52, BG_TILE_WIDTH, BG_TILE_HEIGHT);

            this.fluidTankRenderer.render(guiGraphics, x + fluidTankProperties.x() + 1, y + fluidTankProperties.y() + 1, fluidTank.getFluid());
        }

        //Write Arrow
        for (ArrowProperties arrow : this.getArrows()) {
            var type = arrow.type();
            float func = arrow.showPercentageFunc().apply(menu);
            int aX = x + arrow.x();
            int aY = y + arrow.y();
            int size = arrow.size();
            int midSize = size - 6;
            int coverSize = (int) (size * func);
            int midCoverSize = coverSize - 6;
            switch (type) {
                case HORIZONTAL -> {
                    //Write base
                    guiGraphics.blit(bgTile, aX, aY, 18, 0, 1, 7, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                    guiGraphics.blit(bgTile, aX + 1, aY, midSize, 7, 19, 0, 1, 7, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                    guiGraphics.blit(bgTile, aX + midSize + 1, aY, 20, 0, 5, 7, BG_TILE_WIDTH, BG_TILE_HEIGHT);

                    //Write cover
                    if (coverSize >= 1) {
                        guiGraphics.blit(bgTile, aX, aY, 18, 7, 1, 7, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                    }
                    if (coverSize >= 2) {
                        guiGraphics.blit(bgTile, aX + 1, aY, midCoverSize, 7, 19, 7, 1, 7, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                    }
                    if (coverSize >= midCoverSize + 1 && func > 0) {
                        guiGraphics.blit(bgTile, aX + Math.max(midCoverSize + 1, 0), aY, 20, 7, 5, 7, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                    }
                }
                case VERTICAL -> {
                    //Write base
                    guiGraphics.blit(bgTile, aX, aY, 25, 0, 7, 1, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                    guiGraphics.blit(bgTile, aX, aY + 1, 7, midSize, 25, 1, 7, 1, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                    guiGraphics.blit(bgTile, aX, aY + midSize + 1, 25, 2, 7, 5, BG_TILE_WIDTH, BG_TILE_HEIGHT);

                    //Write cover
                    if (coverSize >= 1) {
                        guiGraphics.blit(bgTile, aX, aY, 25, 7, 7, 1, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                    }
                    if (coverSize >= 2) {
                        guiGraphics.blit(bgTile, aX, aY + 1, 7, midCoverSize, 25, 8, 7, 1, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                    }
                    if (coverSize >= midCoverSize + 1 && func > 0) {
                        guiGraphics.blit(bgTile, aX, aY + Math.max(midCoverSize + 1, 0), 25, 9, 7, 5, BG_TILE_WIDTH, BG_TILE_HEIGHT);
                    }
                }
            }

            //Write machine name
            var displayName = this.getDisplayName();
            ChatFormatting format = this.getBgTileType().getFormat();
            if (format != null) {
                displayName.withStyle(format);
            }
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, displayName, x + screen.getXSize() / 2, y - 9, 4210752);
        }
    }

    public void onButtonPress(int type, ServerPlayer player, MachineBlockEntity blockEntity) {
        player.playSound(SoundEvents.UI_BUTTON_CLICK.get());
    }

    public void onIOButtonPress(int type, ServerPlayer player, MachineBlockEntity blockEntity) {
        MachineDirectionHandler directionHandler = blockEntity.getDirectionHandler();
        if (type >= this.getSlots().size()) {
            int modType = type - this.getSlots().size();
            int direction = directionHandler.getMatterDirection(modType).ordinal();
            if (direction >= DirectionType.values().length - 1) {
                direction = 0;
            } else {
                direction++;
            }
            directionHandler.setMatterDirection(modType, DirectionType.values()[direction]);
        }
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
                toReturn.create(energy.x() + 1, 62, itemStack -> true, SlotType.NORMAL);
            } else {
                toReturn.create(energy.x() + 1, 62, itemStack -> true, SlotType.EXTRACT_ONLY);
            }
        }
        return toReturn;
    }

    public final EnergySlotList getEnergySlots() {
        return this.getEnergySlots(new EnergySlotList());
    }

    public final MatterSlotList getMatterSlots() {
        return this.getMatterSlots(new MatterSlotList());
    }

    public final ButtonList getButtons() {
        return this.getButtons(new ButtonList());
    }

    public final ArrowList getArrows() {
        return this.getArrows(new ArrowList());
    }

    public final int getUpgradeAmount(MachineBlockEntity blockEntity) {
        int upgradeSlot = this.getUpgradeArchiveSlot();
        if (upgradeSlot >= 0 && upgradeSlot < this.getSlots().size()) {
            ItemStack itemStack = blockEntity.getItemHandler().getStackInSlot(upgradeSlot);
            if (itemStack.getItem() instanceof IMachineUpgradeArchive archive) {
                return archive.getUpgradeValue();
            }
        }
        return 0;
    }

    public final void setButtonPacket(@NotNull OnButtonPressPacket buttonPacket, @NotNull OnButtonPressPacket ioButtonPacket) {
        this.buttonPacket = buttonPacket;
        this.ioButtonPacket = ioButtonPacket;
    }

    //Helper
    private static boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX,
                                            int offsetY, FluidTankRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    protected static int next(int pos) {
        return pos + 18;
    }

    protected static int next(int pos, int count) {
        return pos + (18 * count);
    }

    protected boolean canItemInsertToSlot(MachineBlockEntity blockEntity, int slot, ItemStack pItemStack) {
        var itemHandler = blockEntity.getItemHandler();
        ItemStack itemStack = itemHandler.getStackInSlot(slot);
        return (itemStack.is(pItemStack.getItem()) && itemStack.getCount() + pItemStack.getCount() <= itemStack.getItem().getMaxStackSize()) || itemStack.isEmpty();
    }

    protected void insertItemToSlot(MachineBlockEntity blockEntity, int slot, ItemStack pItemStack) {
        var itemHandler = blockEntity.getItemHandler();
        ItemStack itemStack = itemHandler.getStackInSlot(slot).copy();
        if (itemStack.isEmpty()) {
            itemHandler.setStackInSlot(slot, pItemStack);
        } else {
            itemStack.grow(pItemStack.getCount());
            itemHandler.setStackInSlot(slot, itemStack);
        }
    }

    protected int modifyIntByUpgradeMultiplier(MachineBlockEntity blockEntity, int base, float multiplier) {
        return (int) (base * (1f + multiplier * this.getUpgradeAmount(blockEntity)));
    }

    protected int getUpgradeMultiplier(MachineBlockEntity blockEntity, float multiplier) {
        return (int) (this.getUpgradeAmount(blockEntity) * multiplier + 1);
    }

    @Nullable
    protected static Direction getCombinedDirection(Direction baseDirection, DirectionType direction) {
        return switch (baseDirection) {
            case NORTH -> switch (direction) {
                case NONE -> null;
                case UP -> Direction.UP;
                case DOWN -> Direction.DOWN;
                case RIGHT -> Direction.WEST;
                case LEFT -> Direction.EAST;
                case FRONT -> Direction.SOUTH;
                case BACK -> Direction.NORTH;
            };
            case SOUTH -> switch (direction) {
                case NONE -> null;
                case UP -> Direction.UP;
                case DOWN -> Direction.DOWN;
                case RIGHT -> Direction.EAST;
                case LEFT -> Direction.WEST;
                case FRONT -> Direction.NORTH;
                case BACK -> Direction.SOUTH;
            };
            case WEST -> switch (direction) {
                case NONE -> null;
                case UP -> Direction.UP;
                case DOWN -> Direction.DOWN;
                case RIGHT -> Direction.SOUTH;
                case LEFT -> Direction.NORTH;
                case FRONT -> Direction.EAST;
                case BACK -> Direction.WEST;
            };
            case EAST -> switch (direction) {
                case NONE -> null;
                case UP -> Direction.UP;
                case DOWN -> Direction.DOWN;
                case RIGHT -> Direction.NORTH;
                case LEFT -> Direction.SOUTH;
                case FRONT -> Direction.WEST;
                case BACK -> Direction.EAST;
            };
            default -> throw new IllegalStateException("Unexpected value: " + baseDirection);
        };
    }
}