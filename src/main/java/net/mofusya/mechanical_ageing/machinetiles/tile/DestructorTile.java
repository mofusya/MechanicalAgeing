package net.mofusya.mechanical_ageing.machinetiles.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.ModMachines;

public class DestructorTile extends MachineTile {
    public DestructorTile() {
        super(ModMachines.DESTROYER, ModMachines.DESTROYER_BE, ModMachines.DESTROYER_MENU);
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {

    }
}