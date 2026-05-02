package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.machinetiles.IBgTileType;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowList;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowType;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.tiles.BgTileType;
import net.mofusya.ornatelib.lang.SeptiLong;

@MethodsReturnNonnullByDefault
public class BrickSmeltingChamber extends MachineTile {
    public BrickSmeltingChamber(ResourceLocation id) {
        super(id);
    }

    @Override
    public IBgTileType getBgTileType() {
        return BgTileType.BRICK;
    }

    @Override
    public SlotList getSlots(SlotList slots) {
        return super.getSlots(slots)
                .create(next(16, 2), next(16), itemStack -> true, SlotType.NORMAL)
                .create(next(16, 6) + 9, next(16), itemStack -> false, SlotType.NORMAL);
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(next(15), 25, matterType -> matterType.is(MAgMatterTypes.HEAT), new SeptiLong(2400), new SeptiLong(300), new SeptiLong());
    }

    @Override
    public ArrowList getArrows(ArrowList list) {
        return super.getArrows(list)
                .create(next(16, 3) + 1, next(25) - 4, 60, ArrowType.HORIZONTAL);
    }

    @Override
    public MutableComponent getDisplayName() {
        return Component.translatable("block.mechanical_ageing.brick_smelting_chamber.machine_name");
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {

    }
}
