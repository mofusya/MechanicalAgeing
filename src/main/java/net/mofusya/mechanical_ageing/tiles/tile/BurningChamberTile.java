package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowList;
import net.mofusya.mechanical_ageing.machinetiles.arrow.ArrowType;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.recipe.FuelRecipe;
import net.mofusya.mechanical_ageing.recipes.recipe.MatterBurningRecipe;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.lang.SeptiLong;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@MethodsReturnNonNullByDefault
public class BurningChamberTile extends MachineTile {
    @Nullable
    protected final MutableComponent displayName;
    protected final SeptiLong fuelTankCapacity;
    protected final SeptiLong fuelTankMaxReceive;
    protected final SeptiLong fuelTankMaxExtract;
    protected final SeptiLong heatTankCapacity;
    protected final SeptiLong heatTankMaxReceive;
    protected final SeptiLong heatTankMaxExtract;
    protected final double upgradeMultiplier;

    public BurningChamberTile(ResourceLocation id, SeptiLong fuelTankCapacity, SeptiLong fuelTankMaxReceive, SeptiLong fuelTankMaxExtract, SeptiLong heatTankCapacity, SeptiLong heatTankMaxReceive, SeptiLong heatTankMaxExtract) {
        this(id, null, fuelTankCapacity, fuelTankMaxReceive, fuelTankMaxExtract, heatTankCapacity, heatTankMaxReceive, heatTankMaxExtract, 1);
    }

    public BurningChamberTile(ResourceLocation id, @Nullable MutableComponent displayName, SeptiLong fuelTankCapacity, SeptiLong fuelTankMaxReceive, SeptiLong fuelTankMaxExtract, SeptiLong heatTankCapacity, SeptiLong heatTankMaxReceive, SeptiLong heatTankMaxExtract) {
        this(id, displayName, fuelTankCapacity, fuelTankMaxReceive, fuelTankMaxExtract, heatTankCapacity, heatTankMaxReceive, heatTankMaxExtract, 1);
    }

    public BurningChamberTile(ResourceLocation id, SeptiLong fuelTankCapacity, SeptiLong fuelTankMaxReceive, SeptiLong fuelTankMaxExtract, SeptiLong heatTankCapacity, SeptiLong heatTankMaxReceive, SeptiLong heatTankMaxExtract, double upgradeMultiplier) {
        this(id, null, fuelTankCapacity, fuelTankMaxReceive, fuelTankMaxExtract, heatTankCapacity, heatTankMaxReceive, heatTankMaxExtract, upgradeMultiplier);
    }

    public BurningChamberTile(ResourceLocation id, @Nullable MutableComponent displayName, SeptiLong fuelTankCapacity, SeptiLong fuelTankMaxReceive, SeptiLong fuelTankMaxExtract, SeptiLong heatTankCapacity, SeptiLong heatTankMaxReceive, SeptiLong heatTankMaxExtract, double upgradeMultiplier) {
        super(id);
        this.displayName = displayName;
        this.fuelTankCapacity = fuelTankCapacity;
        this.fuelTankMaxReceive = fuelTankMaxReceive;
        this.fuelTankMaxExtract = fuelTankMaxExtract;
        this.heatTankCapacity = heatTankCapacity;
        this.heatTankMaxReceive = heatTankMaxReceive;
        this.heatTankMaxExtract = heatTankMaxExtract;
        this.upgradeMultiplier = upgradeMultiplier;
    }

    @Override
    public SlotList getSlots(SlotList slots) {
        return super.getSlots(slots)
                .createHorLine(next(16), 16, itemStack -> itemStack.is(Items.COAL), SlotType.NORMAL, 3);
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(next(16, 2), 25, matterType -> matterType.is(MAgMatterTypes.FUEL), this.fuelTankCapacity, this.fuelTankMaxReceive, this.fuelTankMaxExtract)
                .create(next(16, 6) + 9, 25, matterType -> matterType.is(MAgMatterTypes.HEAT), this.heatTankCapacity, this.heatTankMaxReceive, this.heatTankMaxExtract);
    }

    @Override
    public ArrowList getArrows(ArrowList list) {
        return super.getArrows(list)
                .create(next(16, 3) + 1, next(25) - 4, 60, ArrowType.HORIZONTAL);
    }

    @Override
    public MutableComponent getDisplayName() {
        return this.displayName == null ? super.getDisplayName() : this.displayName;
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        super.tick(level, pos, state, blockEntity);

        var itemHandler = blockEntity.getItemHandler();
        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();

        if (matterHandler == null) return;

        this.fuelRecipeTick(level, blockEntity, matterHandler, itemHandler);
        this.burningRecipeTick(level, blockEntity, matterHandler);
    }

    private void fuelRecipeTick(Level level, MachineBlockEntity blockEntity, MatterHandler matterHandler, ItemStackHandler itemHandler) {
        for (int i = 0; i < 3; i++) {
            MAgContainer container = MAgContainer.builder()
                    .itemSlotsList(1 + i)
                    .build(blockEntity);
            Optional<FuelRecipe> recipe = level.getRecipeManager().getRecipeFor(FuelRecipe.Type.INSTANCE, container, level);

            if (recipe.isEmpty()) return;

            MatterStack result = recipe.get().getResult();
            if (matterHandler.canReceiveFromInside(result, 0)) {
                itemHandler.extractItem(1 + i, 1, false);
                matterHandler.receiveFromInside(result, 0);
            }
        }
    }

    private void burningRecipeTick(Level level, MachineBlockEntity blockEntity, MatterHandler matterHandler) {
        MAgContainer container = MAgContainer.builder()
                .matterSlotList(0)
                .build(blockEntity);
        Optional<MatterBurningRecipe> recipe = level.getRecipeManager().getRecipeFor(MatterBurningRecipe.Type.INSTANCE, container, level);

        if (recipe.isEmpty()) return;

        MatterStack ingredient = recipe.get().getIngredient();
        ingredient.modifyAmount(amount -> amount.multiply((float) (getUpgradeMultiplier(blockEntity, 4) * this.upgradeMultiplier)));
        MatterStack result = recipe.get().getResult();
        result.modifyAmount(amount -> amount.multiply((float) (getUpgradeMultiplier(blockEntity, 4) * this.upgradeMultiplier)));

        if (matterHandler.canExtractFromInside(ingredient, 0) &&
                matterHandler.canReceiveFromInside(result, 1)) {
            matterHandler.extractFromInside(ingredient, 0);
            matterHandler.receiveFromInside(result, 1);
        } else if (matterHandler.canExtractFromInside(recipe.get().getIngredient(), 0) &&
                matterHandler.canReceiveFromInside(recipe.get().getResult(), 1)) {
            matterHandler.extractFromInside(recipe.get().getIngredient(), 0);
            matterHandler.receiveFromInside(recipe.get().getResult(), 1);
        }
    }
}
