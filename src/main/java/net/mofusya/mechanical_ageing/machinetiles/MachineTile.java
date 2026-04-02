package net.mofusya.mechanical_ageing.machinetiles;

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
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class MachineTile {

    private final @NotNull Supplier<RegistryObject<Block>> block;
    private final @NotNull Supplier<RegistryObject<BlockEntityType<MachineBlockEntity>>> blockEntity;
    private final @NotNull Supplier<RegistryObject<MenuType<MachineMenu>>> menu;

    public MachineTile(ResourceLocation location) {
        this.block = MachineRegister.getBlock(location);
        this.blockEntity = MachineRegister.getBlockEntity(location);
        this.menu = MachineRegister.getMenu(location);
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
        return this.block.get();
    }

    public @NotNull RegistryObject<BlockEntityType<MachineBlockEntity>> getBlockEntity() {
        return this.blockEntity.get();
    }

    public @NotNull RegistryObject<MenuType<MachineMenu>> getMenu() {
        return this.menu.get();
    }
}