package net.mofusya.mechanical_ageing.machinetiles.machinetile;

import net.kairayk.yklibrary.util.scrpos.ScrPosI;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.components.slot.ItemSlot;
import net.mofusya.mechanical_ageing.machinetiles.components.slot.MatterSlot;
import net.mofusya.mechanical_ageing.machinetiles.components.slot.UnitSlot;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.util.TripleEntry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Test extends MachineTile {
    public Test(ResourceLocation id) {
        super(id);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        super.tick(level, pos, state, blockEntity);

        final int setSlot = 0;

        var kelvinHandler = blockEntity.getKelvinHandler();
        var raltsHandler = blockEntity.getRaltsHandler();
        var vaarrHandler = blockEntity.getVaarrHandler();
        var tsealvHandler = blockEntity.getTsealvHandler();
        kelvinHandler.receive(64d, false, true, setSlot);
        raltsHandler.receive(64d, false, true, setSlot);
        vaarrHandler.receive(64d, false, true, setSlot);
        tsealvHandler.receive(64d, false, true, setSlot);

        var matterHandler = blockEntity.getMatterHandler();
        matterHandler.receive(new MatterStack(MAgMatterTypes.WATER.get(), 32d), false, true, setSlot);
    }

    @Override
    public List<ItemSlot> getItemSlots() {
        return List.of(
                ItemSlot.of(ScrPosI.of(0, 0))
        );
    }

    @Override
    public List<MatterSlot> getMatterSlots() {
        return List.of(
                MatterSlot.of(ScrPosI.of(0, 0), new TripleEntry<>(2048d))
        );
    }

    @Override
    public List<UnitSlot> getUnitSlots(UnitType type) {
        return List.of(
                UnitSlot.of(ScrPosI.of(0, 0), new TripleEntry<>(4096d))
        );
    }

    @Override
    public BlockItem createItem(Block block, Item.Properties build) {
        return new BlockItem(block, build){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                super.appendHoverText(itemStack, level, tooltip, flag);

                tooltip.add(Component.literal("Testing :)"));
            }
        };
    }
}
