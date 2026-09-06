package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowList;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowType;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.button.ButtonList;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;
import net.mofusya.ornatelib.lang.UnLong;

public class BasicMatterPumpTile extends MachineTile {
    public BasicMatterPumpTile(ResourceLocation id) {
        super(id);
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(25, 25, matterType -> matterType.is(MAgMatterTypes.ROTATION), new UnLong(1048576), new UnLong(1048576), UnLong.zero())
                .create(next(16, 6) + 9, 25, matterType -> matterType.is(MAgMatterTypes.WATER), UnLong.billion(), UnLong.zero(), UnLong.million().multi(10));
    }

    @Override
    public ArrowList getArrows(ArrowList list) {
        return super.getArrows(list)
                .create(next(25) + 1, next(25) + 5, 87, ArrowType.HORIZONTAL);
    }

    @Override
    public ButtonList getButtons(ButtonList list) {
        return super.getButtons(list)
                .create(next(16, 5), 25, "Pull", SlotType.NORMAL);
    }

    @Override
    public MutableComponent getDisplayName() {
        return Component.translatable("block.mechanical_ageing.basic_watter_pump.machine_name");
    }

    @Override
    public void onButtonPress(int type, ServerPlayer player, MachineBlockEntity blockEntity) {
        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        if(matterHandler == null) return;

        MatterStack result = new MatterStack(MAgMatterTypes.WATER, 1000);

        if (type == 0 && matterHandler.canReceiveFromInside(result, 1) && hasWaterSourcesUnder(player.level(), blockEntity.getBlockPos())) {
            player.playSound(SoundEvents.BUCKET_FILL_FISH);
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 80, 9));
            matterHandler.receiveFromInside(result, 1);
        } else {
            super.onButtonPress(type, player, blockEntity);
        }
    }

    public static boolean hasWaterSourcesUnder(Level level, BlockPos pos){
        return level.getFluidState(pos.below()).is(FluidTags.WATER);
    }
}
