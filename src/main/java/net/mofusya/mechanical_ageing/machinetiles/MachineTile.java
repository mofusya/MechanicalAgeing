package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import org.jetbrains.annotations.NotNull;

public abstract class MachineTile {

    private final @NotNull RegistryObject<Block> block;
    private final @NotNull RegistryObject<BlockEntityType<MachineBlockEntity>> blockEntity;
    private final @NotNull RegistryObject<MenuType<MachineMenu>> menu;

    public MachineTile(@NotNull RegistryObject<Block> block, @NotNull RegistryObject<BlockEntityType<MachineBlockEntity>> blockEntity, @NotNull RegistryObject<MenuType<MachineMenu>> menu) {
        this.block = block;
        this.blockEntity = blockEntity;
        this.menu = menu;
    }

    public abstract Component getDisplayName();

    public abstract void tick(Level level, BlockPos pos, BlockState state);

    public SlotList getSlots(SlotList slots){
        return slots.create(8, 6, itemStack -> false);
    }

    public final SlotList getSlots(){
        return this.getSlots(new SlotList());
    }

    public Item.Properties getItemBuild() {
        return new Item.Properties();
    }

    public BlockBehaviour.Properties getBlockBuild() {
        return BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(DyeColor.GRAY);
    }

    public @NotNull RegistryObject<Block> getBlock() {
        return this.block;
    }

    public @NotNull RegistryObject<BlockEntityType<MachineBlockEntity>> getBlockEntity() {
        return this.blockEntity;
    }

    public @NotNull RegistryObject<MenuType<MachineMenu>> getMenu() {
        return this.menu;
    }
}