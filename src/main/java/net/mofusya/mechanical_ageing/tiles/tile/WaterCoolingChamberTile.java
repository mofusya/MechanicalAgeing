package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowList;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowType;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.recipe.WaterCoolingRecipe;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;

import java.util.Optional;

@MethodsReturnNonNullByDefault
public class WaterCoolingChamberTile extends MachineTile {
    public WaterCoolingChamberTile(ResourceLocation id) {
        super(id);
    }

    @Override
    public MutableComponent getDisplayName() {
        return Component.translatable("block.mechanical_ageing.water_cooling_chamber.machine_name");
    }

    @Override
    public SlotList getSlots(SlotList slots) {
        return super.getSlots(slots)
                .create(next(16, 6) + 9, 34, itemStack -> false, SlotType.OUTPUT);
    }

    @Override
    public int getDataSlotCount() {
        return 2;
    }

    @Override
    public int getDefaultDataSlotAmount(int index) {
        return switch (index) {
            case 0 -> 0;
            case 1 -> 1600;
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(next(15), 25, matterType -> matterType.is(MAgMatterTypes.WATER), UnLong.billion(), UnLong.billion(), UnLong.zero())
                .create(next(15, 2), 25, matterType -> true, UnLong.million(), UnLong.million(), UnLong.zero());
    }

    @Override
    public ArrowList getArrows(ArrowList list) {
        return super.getArrows(list)
                .create(next(16, 3) + 1, next(25) - 4, 60, menu -> {
                    int progress = menu.getData(0);
                    int maxProgress = menu.getData(1) * 10;
                    return progress / (float) maxProgress;
                }, ArrowType.HORIZONTAL);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        super.tick(level, pos, state, blockEntity);

        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        if (matterHandler == null) return;

        MAgContainer container = MAgContainer.builder()
                .matterSlotList(0, 1)
                .build(blockEntity);
        Optional<WaterCoolingRecipe> recipe = level.getRecipeManager().getRecipeFor(WaterCoolingRecipe.Type.INSTANCE, container, level);
        if (recipe.isEmpty()) {
            if (blockEntity.getData(0) > 0) blockEntity.setData(0, 0);
            return;
        }

        blockEntity.modifyData(0, value -> value + this.modifyIntByUpgradeMultiplier(blockEntity, 10, 0.2f));
        if (blockEntity.getData(1) != recipe.get().getProcessingTick()) {
            blockEntity.setData(1, recipe.get().getProcessingTick());
        }

        if (blockEntity.getData(0) >= blockEntity.getData(1) * 10 &&
                canItemInsertToSlot(blockEntity, 1, recipe.get().getResult())) {
            matterHandler.extractFromInside(recipe.get().getWaterAmount(), 0);
            matterHandler.extractFromInside(recipe.get().getIngredient(), 1);
            insertItemToSlot(blockEntity, 1, recipe.get().getResult());
            blockEntity.setData(0, 0);
        }
    }
}
