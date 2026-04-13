package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import org.jetbrains.annotations.NotNull;

public class TriDimCraftingTable extends MachineTile {
    public TriDimCraftingTable(ResourceLocation id) {
        super(id);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.mechanical_ageing.tri_dimensional_crafting_table.machine_name");
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {

    }
}
