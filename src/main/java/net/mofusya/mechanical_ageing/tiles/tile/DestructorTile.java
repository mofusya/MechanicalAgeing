package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.energy.EnergySlotList;
import net.mofusya.mechanical_ageing.tiles.energy.ForgeEnergyType;
import net.mofusya.mechanical_ageing.tiles.energy.StarDustEnergyType;

public class DestructorTile extends MachineTile {
    public DestructorTile(ResourceLocation location) {
        super(location);
    }

    @Override
    public EnergySlotList getEnergySlots(EnergySlotList slots) {
        return super.getEnergySlots(slots)
                .create(34, ForgeEnergyType::new, 1000)
                .create(151, StarDustEnergyType::new, 1000);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {

    }
}