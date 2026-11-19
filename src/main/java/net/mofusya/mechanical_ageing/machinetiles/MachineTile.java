package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlock;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineMenu;
import net.mofusya.mechanical_ageing.machinetiles.components.slot.ItemSlot;
import net.mofusya.mechanical_ageing.machinetiles.components.slot.MatterSlot;
import net.mofusya.mechanical_ageing.machinetiles.components.slot.UnitSlot;
import net.mofusya.ornatelib.util.annotation.EverythingAreNonNullByDefault;

import java.util.List;
import java.util.function.Supplier;

/**
 * A class for storing all the setting for a machine.
 */
@EverythingAreNonNullByDefault
public class MachineTile {
    /**
     * A field for storing the id of this {@code MachineTile}.
     */
    private final ResourceLocation id;
    /**
     * The fields for storing the {@code Block}s, {@code BlockEntityType<MachineBlockEntity>}s, and {@code MenuType<MachineMenu>}s.
     */
    private final Supplier<RegistryObject<Block>> block;
    private final Supplier<RegistryObject<BlockEntityType<MachineBlockEntity>>> blockEntityType;
    private final Supplier<RegistryObject<MenuType<MachineMenu>>> menuType;

    /**
     * The root constructor.
     *
     * @param id The id of this machine.
     */
    public MachineTile(ResourceLocation id) {
        this.id = id;
        this.block = MachineTileRegister.getMachineBlock(id);
        this.blockEntityType = MachineTileRegister.getMachineBlockEntityType(id);
        this.menuType = MachineTileRegister.getMachineMenuType(id);
    }

    //region Settings

    /**
     * A setting getter method to create this item slots.
     *
     * @return A {@code List} of {@code ItemSlot}s.
     */
    public List<ItemSlot> getItemSlots() {
        return List.of();
    }

    /**
     * A setting getter method to create this item slots.
     *
     * @return A {@code List} of {@code MatterSlot}s.
     */
    public List<MatterSlot> getMatterSlots() {
        return List.of();
    }

    /**
     * A setting getter method to create this all unit slots.
     *
     * @param type The unit type to get.
     * @return A {@code List} of {@code UnitSlot}s.
     */
    public List<UnitSlot> getUnitSlots(UnitType type) {
        return List.of();
    }

    /**
     * A setting getter method to create this's items properties.
     *
     * @return A new {@code Item.Properties}.
     */
    public Item.Properties getItemProperties() {
        return new Item.Properties();
    }

    /**
     * A setting getter method to create this's blocks properties.
     *
     * @return A new {@code BlockBehaviour.Properties}.
     */
    public BlockBehaviour.Properties getBlockProperties() {
        return BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK);
    }

    /**
     * A setting method to create this item.
     *
     * @return A new {@code BlockItem}.
     */
    public BlockItem createItem(Block block, Item.Properties build) {
        return new BlockItem(block, build);
    }

    /**
     * A setting method to create this block.
     *
     * @return A new {@code MachineBlock}.
     */
    public MachineBlock createBlock(BlockBehaviour.Properties build) {
        return new MachineBlock(build, this);
    }

    /**
     * A setting method to create this blockEntity.
     *
     * @return A new {@code MachineBlockEntity}.
     */
    public MachineBlockEntity createBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        return new MachineBlockEntity(type, pos, state, this);
    }

    /**
     * A setting method to create this menu
     *
     * @return A new {@code MachineMenu}.
     */
    public MachineMenu createMenu(int windowId, Inventory inventory, FriendlyByteBuf buf) {
        return new MachineMenu(windowId, inventory, buf, this);
    }
    //endregion

    //region Getters

    /**
     * A getter for this {@code id}.
     *
     * @return This {@code id}.
     */
    public ResourceLocation getId() {
        return id;
    }

    /**
     * A helper getter for this {@code block}.
     *
     * @return This {@code block}.
     */
    public RegistryObject<Block> getBlock() {
        return block.get();
    }

    /**
     * A helper getter for this {@code blockEntityType}.
     *
     * @return This {@code blockEntityType}.
     */
    public RegistryObject<BlockEntityType<MachineBlockEntity>> getBlockEntityType() {
        return blockEntityType.get();
    }

    /**
     * A helper getter for this {@code menuType}.
     *
     * @return This {@code menuType}.
     */
    public RegistryObject<MenuType<MachineMenu>> getMenuType() {
        return menuType.get();
    }
    //endregion

    public enum UnitType {
        KELVIN, RALTS, VAARR, TSEALV
    }
}
