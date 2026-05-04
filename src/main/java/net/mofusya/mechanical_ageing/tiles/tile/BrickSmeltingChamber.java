package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.machinetiles.IBgTileType;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowList;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowType;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.button.ButtonList;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.recipe.SmeltingRecipe;
import net.mofusya.mechanical_ageing.tiles.BgTileType;
import net.mofusya.ornatelib.lang.SeptiLong;

import java.util.Optional;

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
    public int getDataSlotCount() {
        return 2;
    }

    @Override
    public int getDefaultDataSlotAmount(int index) {
        if (index == 1) {
            return 2000;
        }
        return super.getDefaultDataSlotAmount(index);
    }

    @Override
    public SlotList getSlots(SlotList slots) {
        return super.getSlots(slots)
                .create(next(16, 2), next(16), itemStack -> true, SlotType.NORMAL)
                .create(next(16, 6) + 9, next(16), itemStack -> false, SlotType.EXTRACT_ONLY);
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(next(15), 25, matterType -> matterType.is(MAgMatterTypes.HEAT), new SeptiLong(2400), new SeptiLong(300), new SeptiLong());
    }

    @Override
    public ArrowList getArrows(ArrowList list) {
        return super.getArrows(list)
                .create(next(16, 3) + 1, next(25) - 4, 60, menu -> {
                    int maxProgress = menu.getData(1);
                    if (maxProgress <= 0) return 0f;

                    return menu.getData(0) / (float) maxProgress;
                }, ArrowType.HORIZONTAL);
    }

    @Override
    public ButtonList getButtons(ButtonList list) {
        return super.getButtons(list)
                .create(7, next(7), SlotType.NORMAL);
    }

    @Override
    public MutableComponent getDisplayName() {
        return Component.translatable("block.mechanical_ageing.brick_smelting_chamber.machine_name");
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        var itemHandler = blockEntity.getItemHandler();

        if (matterHandler == null) return;
        if (itemHandler == null) return;

        final int progressDataSlot = 0;
        final int maxProgressDataSlot = 1;
        final int ingredientItemSlot = 1;
        final int resultItemSlot = 2;
        final int heatMatterSlot = 0;

        MAgContainer container = MAgContainer.builder()
                .itemSlotsList(ingredientItemSlot, resultItemSlot)
                .matterSlotList(heatMatterSlot)
                .build(blockEntity);

        Optional<SmeltingRecipe> recipe = level.getRecipeManager().getRecipeFor(SmeltingRecipe.Type.INSTANCE, container, level);
        if (recipe.isPresent()) {
            int smeltTime = recipe.get().getSmeltTime();
            ItemStack result = recipe.get().getResult();

            if (canItemInsertToSlot(blockEntity, resultItemSlot, result)) {
                if (blockEntity.getData(maxProgressDataSlot) != smeltTime * 10)
                    blockEntity.setData(maxProgressDataSlot, smeltTime * 10);

                blockEntity.addData(progressDataSlot, modifyIntByMultiplier(blockEntity, 10, 0.2f));

                if (blockEntity.getData(progressDataSlot) >= blockEntity.getData(maxProgressDataSlot)) {
                    itemHandler.extractItem(ingredientItemSlot, 1, false);
                    matterHandler.extractFromInside(recipe.get().getHeat(), heatMatterSlot);
                    insertItemToSlot(blockEntity, resultItemSlot, result);
                    blockEntity.setData(progressDataSlot, 0);
                }
            }
        }
    }

    @Override
    public void onButtonPress(int type, ServerPlayer player, MachineBlockEntity blockEntity) {
        switch (type) {
            case 0 -> {
                blockEntity.getMatterHandler().receive(new MatterStack(MAgMatterTypes.HEAT, 1000), 0);
            }
        }
    }
}
