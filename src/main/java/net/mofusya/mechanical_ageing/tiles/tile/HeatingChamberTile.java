package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.core.BlockPos;
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
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.recipe.HeatingRecipe;
import net.mofusya.mechanical_ageing.util.annotations.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.lang.SeptiLong;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@MethodsReturnNonNullByDefault
public class HeatingChamberTile extends MachineTile {
    @Nullable
    protected final MutableComponent displayName;
    protected final SeptiLong ingredientTankCapacity;
    protected final SeptiLong ingredientTankMaxReceive;
    protected final SeptiLong ingredientTankMaxExtract;
    protected final SeptiLong heatTankCapacity;
    protected final SeptiLong heatTankMaxReceive;
    protected final SeptiLong heatTankMaxExtract;
    protected final SeptiLong resultTankCapacity;
    protected final SeptiLong resultTankMaxReceive;
    protected final SeptiLong resultTankMaxExtract;
    protected final double upgradeMultiplier;

    public HeatingChamberTile(ResourceLocation id, SeptiLong ingredientTankCapacity, SeptiLong ingredientTankMaxReceive, SeptiLong ingredientTankMaxExtract, SeptiLong heatTankCapacity, SeptiLong heatTankMaxReceive, SeptiLong heatTankMaxExtract, SeptiLong resultTankCapacity, SeptiLong resultTankMaxReceive, SeptiLong resultTankMaxExtract) {
        this(id, null, ingredientTankCapacity, ingredientTankMaxReceive, ingredientTankMaxExtract, heatTankCapacity, heatTankMaxReceive, heatTankMaxExtract, resultTankCapacity, resultTankMaxReceive, resultTankMaxExtract, 1);
    }

    public HeatingChamberTile(ResourceLocation id, @Nullable MutableComponent displayName, SeptiLong ingredientTankCapacity, SeptiLong ingredientTankMaxReceive, SeptiLong ingredientTankMaxExtract, SeptiLong heatTankCapacity, SeptiLong heatTankMaxReceive, SeptiLong heatTankMaxExtract, SeptiLong resultTankCapacity, SeptiLong resultTankMaxReceive, SeptiLong resultTankMaxExtract) {
        this(id, displayName, ingredientTankCapacity, ingredientTankMaxReceive, ingredientTankMaxExtract, heatTankCapacity, heatTankMaxReceive, heatTankMaxExtract, resultTankCapacity, resultTankMaxReceive, resultTankMaxExtract, 1);
    }

    public HeatingChamberTile(ResourceLocation id, SeptiLong ingredientTankCapacity, SeptiLong ingredientTankMaxReceive, SeptiLong ingredientTankMaxExtract, SeptiLong heatTankCapacity, SeptiLong heatTankMaxReceive, SeptiLong heatTankMaxExtract, SeptiLong resultTankCapacity, SeptiLong resultTankMaxReceive, SeptiLong resultTankMaxExtract, double upgradeMultiplier) {
        this(id, null, ingredientTankCapacity, ingredientTankMaxReceive, ingredientTankMaxExtract, heatTankCapacity, heatTankMaxReceive, heatTankMaxExtract, resultTankCapacity, resultTankMaxReceive, resultTankMaxExtract, upgradeMultiplier);
    }

    public HeatingChamberTile(ResourceLocation id, @Nullable MutableComponent displayName, SeptiLong ingredientTankCapacity, SeptiLong ingredientTankMaxReceive, SeptiLong ingredientTankMaxExtract, SeptiLong heatTankCapacity, SeptiLong heatTankMaxReceive, SeptiLong heatTankMaxExtract, SeptiLong resultTankCapacity, SeptiLong resultTankMaxReceive, SeptiLong resultTankMaxExtract, double upgradeMultiplier) {
        super(id);
        this.displayName = displayName;
        this.ingredientTankCapacity = ingredientTankCapacity;
        this.ingredientTankMaxReceive = ingredientTankMaxReceive;
        this.ingredientTankMaxExtract = ingredientTankMaxExtract;
        this.heatTankCapacity = heatTankCapacity;
        this.heatTankMaxReceive = heatTankMaxReceive;
        this.heatTankMaxExtract = heatTankMaxExtract;
        this.resultTankCapacity = resultTankCapacity;
        this.resultTankMaxReceive = resultTankMaxReceive;
        this.resultTankMaxExtract = resultTankMaxExtract;
        this.upgradeMultiplier = upgradeMultiplier;
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(next(15), 25, matterType -> matterType.is(MAgMatterTypes.HEAT), this.heatTankCapacity, this.heatTankMaxReceive, this.heatTankMaxExtract)
                .create(next(15, 2), 25, matterType -> true, this.ingredientTankCapacity, this.ingredientTankMaxReceive, this.ingredientTankMaxExtract)
                .create(next(16, 6) + 9, 25, matterType -> true, this.resultTankCapacity, this.resultTankMaxReceive, this.resultTankMaxExtract);
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

        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        if (matterHandler == null) return;

        MAgContainer container = MAgContainer.builder()
                .matterSlots(2)
                .build(blockEntity);
        Optional<HeatingRecipe> recipe = level.getRecipeManager().getRecipeFor(HeatingRecipe.Type.INSTANCE, container, level);

        if (recipe.isEmpty()) return;

        MatterStack heat = recipe.get().getHeat();
        heat.modifyAmount(amount -> modifyByUpgrades(amount, blockEntity));
        MatterStack ingredient = recipe.get().getIngredient();
        ingredient.modifyAmount(amount -> modifyByUpgrades(amount, blockEntity));
        MatterStack result = recipe.get().getResult();
        result.modifyAmount(amount -> modifyByUpgrades(amount, blockEntity));

        if (matterHandler.canReceiveFromInside(result, 2) &&
                matterHandler.canExtractFromInside(heat, 0) &&
                matterHandler.canExtractFromInside(ingredient, 1)) {
            matterHandler.receiveFromInside(result, 2);
            matterHandler.extractFromInside(heat, 0);
            matterHandler.extract(ingredient, 1);
        } else if (matterHandler.canReceiveFromInside(recipe.get().getResult(), 2)) {
            matterHandler.receiveFromInside(recipe.get().getResult(), 2);
            matterHandler.extractFromInside(recipe.get().getHeat(), 0);
            matterHandler.extractFromInside(recipe.get().getIngredient(), 1);
        }
    }

    public SeptiLong modifyByUpgrades(SeptiLong amount, MachineBlockEntity blockEntity) {
        return amount.multiply((float) (getUpgradeMultiplier(blockEntity, 4) * this.upgradeMultiplier));
    }
}
