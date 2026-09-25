package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowList;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowType;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.button.ButtonList;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.recipe.MixingRecipe;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;

import java.util.Optional;

@MethodsReturnNonNullByDefault
public class ManualMixingChamberTile extends MachineTile {
    public ManualMixingChamberTile(ResourceLocation id) {
        super(id);
    }

    @Override
    public MutableComponent getDisplayName() {
        return Component.translatable("block.mechanical_ageing.manual_mixing_chamber.machine_name");
    }

    @Override
    public int getDataSlotCount() {
        return 2;
    }

    @Override
    public int getDefaultDataSlotAmount(int index) {
        if (index == 1){
            return 80;
        }
        return super.getDefaultDataSlotAmount(index);
    }

    @Override
    public ArrowList getArrows(ArrowList list) {
        return super.getArrows(list)
                .create(next(16, 3) + 1, next(25) - 4, 60, menu -> {
                    int progress = menu.getData(0);
                    int maxProgress = menu.getData(1);
                    return progress / (float) maxProgress;
                }, ArrowType.HORIZONTAL);
    }

    @Override
    public SlotList getSlots(SlotList slots) {
        return super.getSlots(slots)
                .create(next(17, 2), 34, itemStack -> true, SlotType.NORMAL)
                .create(next(17, 6) + 9, 34, itemStack -> false, SlotType.EXTRACT_ONLY);
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(16, 25, matterType -> true, tags -> true, UnLong.thousand().multi(UnLong.hundred()), UnLong.thousand().multi(UnLong.hundred()), UnLong.zero())
                .create(next(16), 25, matterType -> true, tags -> true, UnLong.thousand().multi(UnLong.hundred()), UnLong.thousand().multi(UnLong.hundred()), UnLong.zero())
                .create(next(16, 7) + 9, 25, matterType -> true, tags -> true, UnLong.thousand().multi(UnLong.hundred()), UnLong.zero(), UnLong.thousand().multi(UnLong.hundred()));
    }

    @Override
    public ButtonList getButtons(ButtonList list) {
        return super.getButtons(list)
                .create(176 / 2 + 4, 16, SlotType.NORMAL);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        super.tick(level, pos, state, blockEntity);
    }

    @Override
    public void onButtonPress(int type, ServerPlayer player, MachineBlockEntity blockEntity) {
        super.onButtonPress(type, player, blockEntity);

        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        if (matterHandler == null) return;

        if (type == 0) {
            MAgContainer container = MAgContainer.builder()
                    .itemSlotsList(1)
                    .matterSlotList(0, 1)
                    .build(blockEntity);
            Optional<MixingRecipe> recipe = player.level().getRecipeManager().getRecipeFor(MixingRecipe.Type.INSTANCE, container, player.level());
            if (recipe.isEmpty()){
                blockEntity.setData(0, 0);
                return;
            }

            if (canItemInsertToSlot(blockEntity, 2, recipe.get().getItemResult()) && matterHandler.canReceiveFromInside(recipe.get().getMatterResult(), 2)) {
                int addingProgress = this.modifyIntByUpgradeMultiplier(blockEntity, 10, 0.1f);
                blockEntity.modifyData(0, progress -> progress + addingProgress);

                if (blockEntity.getData(0) >= blockEntity.getData(1)) {
                    ItemStackHandler itemStackHandler = blockEntity.getItemHandler();

                    itemStackHandler.extractItem(1, 1, false);
                    matterHandler.extractFromInside(recipe.get().getFirstIngredient(), 0);
                    matterHandler.extractFromInside(recipe.get().getSecondIngredient(), 1);
                    insertItemToSlot(blockEntity, 2, recipe.get().getItemResult());
                    matterHandler.receiveFromInside(recipe.get().getMatterResult(), 2);

                    blockEntity.setData(0, 0);
                }
            }
        }
    }
}
