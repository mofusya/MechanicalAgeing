package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
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
import net.mofusya.mechanical_ageing.recipes.recipe.AlloyingRecipe;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@MethodsReturnNonNullByDefault
public class HeatCombingChamberTile extends MachineTile {

    @Nullable
    protected final MutableComponent displayName;
    protected final UnLong heatTankCapacity;
    protected final UnLong heatTankMaxReceive;
    protected final UnLong heatTankMaxExtract;
    protected final double speedMultiplier;

    public HeatCombingChamberTile(ResourceLocation id, UnLong heatTankCapacity, UnLong heatTankMaxReceive, UnLong heatTankMaxExtract) {
        this(id, null, heatTankCapacity, heatTankMaxReceive, heatTankMaxExtract, 1);
    }

    public HeatCombingChamberTile(ResourceLocation id, @Nullable MutableComponent displayName, UnLong heatTankCapacity, UnLong heatTankMaxReceive, UnLong heatTankMaxExtract) {
        this(id, displayName, heatTankCapacity, heatTankMaxReceive, heatTankMaxExtract, 1);
    }

    public HeatCombingChamberTile(ResourceLocation id, UnLong heatTankCapacity, UnLong heatTankMaxReceive, UnLong heatTankMaxExtract, double speedMultiplier) {
        this(id, null, heatTankCapacity, heatTankMaxReceive, heatTankMaxExtract, speedMultiplier);
    }

    public HeatCombingChamberTile(ResourceLocation id, @Nullable MutableComponent displayName, UnLong heatTankCapacity, UnLong heatTankMaxReceive, UnLong heatTankMaxExtract, double speedMultiplier) {
        super(id);
        this.displayName = displayName;
        this.heatTankCapacity = heatTankCapacity;
        this.heatTankMaxReceive = heatTankMaxReceive;
        this.heatTankMaxExtract = heatTankMaxExtract;
        this.speedMultiplier = speedMultiplier;
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
                .create(next(16, 2), 16, itemStack -> true, SlotType.INPUT)
                .create(next(16, 2), next(16, 2), itemStack -> true, SlotType.INPUT)
                .create(next(16, 6) + 9, next(16), itemStack -> false, SlotType.OUTPUT);
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(next(15), 25, matterType -> matterType.is(MAgMatterTypes.HEAT), this.heatTankCapacity, this.heatTankMaxReceive, this.heatTankMaxExtract);
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
    public MutableComponent getDisplayName() {
        return this.displayName == null ? super.getDisplayName() : this.displayName;
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        super.tick(level, pos, state, blockEntity);

        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        var itemHandler = blockEntity.getItemHandler();

        if (matterHandler == null) return;
        if (itemHandler == null) return;

        final int progressDataSlot = 0;
        final int maxProgressDataSlot = 1;
        final int ingredientItemSlot = 1;
        final int subIngredientItemSlot = 2;
        final int resultItemSlot = 3;
        final int heatMatterSlot = 0;

        MAgContainer container = MAgContainer.builder()
                .itemSlotsList(ingredientItemSlot, subIngredientItemSlot, resultItemSlot)
                .matterSlotList(heatMatterSlot)
                .build(blockEntity);

        Optional<AlloyingRecipe> recipe = level.getRecipeManager().getRecipeFor(AlloyingRecipe.Type.INSTANCE, container, level);
        if (recipe.isPresent()){
            int smeltTime = recipe.get().getSmeltTime();
            ItemStack result = recipe.get().getResult();

            if (canItemInsertToSlot(blockEntity, resultItemSlot, result)){
                if (blockEntity.getData(maxProgressDataSlot) != smeltTime * 10)
                    blockEntity.setData(maxProgressDataSlot, smeltTime * 10);

                blockEntity.addData(progressDataSlot, modifyIntByUpgradeMultiplier(blockEntity, 10, (float) (0.6f * this.speedMultiplier)));

                if (blockEntity.getData(progressDataSlot) >= blockEntity.getData(maxProgressDataSlot)) {
                    itemHandler.extractItem(ingredientItemSlot, 1, false);
                    itemHandler.extractItem(subIngredientItemSlot, 1, false);
                    matterHandler.extractFromInside(recipe.get().getHeat(), heatMatterSlot);
                    insertItemToSlot(blockEntity, resultItemSlot, result);
                    blockEntity.setData(progressDataSlot, 0);
                }
            }
        }
    }
}
