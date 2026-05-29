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
import net.mofusya.mechanical_ageing.recipes.recipe.TurbineRotatingRecipe;
import net.mofusya.mechanical_ageing.util.annotations.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.lang.SeptiLong;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@MethodsReturnNonNullByDefault
public class ImpulseTurbineChamber extends MachineTile {
    @Nullable
    protected final MutableComponent displayName;
    protected final SeptiLong vaporTankCapacity;
    protected final SeptiLong vaporTankMaxReceive;
    protected final SeptiLong vaporTankMaxExtract;
    protected final SeptiLong rotationTankCapacity;
    protected final SeptiLong rotationTankMaxReceive;
    protected final SeptiLong rotationTankMaxExtract;
    protected final double upgradeMultiplier;

    public ImpulseTurbineChamber(ResourceLocation id, SeptiLong vaporTankCapacity, SeptiLong vaporTankMaxReceive, SeptiLong vaporTankMaxExtract, SeptiLong rotationTankCapacity, SeptiLong rotationTankMaxReceive, SeptiLong rotationTankMaxExtract) {
        this(id, null, vaporTankCapacity, vaporTankMaxReceive, vaporTankMaxExtract, rotationTankCapacity, rotationTankMaxReceive, rotationTankMaxExtract, 1);
    }

    public ImpulseTurbineChamber(ResourceLocation id, @Nullable MutableComponent displayName, SeptiLong vaporTankCapacity, SeptiLong vaporTankMaxReceive, SeptiLong vaporTankMaxExtract, SeptiLong rotationTankCapacity, SeptiLong rotationTankMaxReceive, SeptiLong rotationTankMaxExtract) {
        this(id, displayName, vaporTankCapacity, vaporTankMaxReceive, vaporTankMaxExtract, rotationTankCapacity, rotationTankMaxReceive, rotationTankMaxExtract, 1);
    }

    public ImpulseTurbineChamber(ResourceLocation id, SeptiLong vaporTankCapacity, SeptiLong vaporTankMaxReceive, SeptiLong vaporTankMaxExtract, SeptiLong rotationTankCapacity, SeptiLong rotationTankMaxReceive, SeptiLong rotationTankMaxExtract, double upgradeMultiplier) {
        this(id, null, vaporTankCapacity, vaporTankMaxReceive, vaporTankMaxExtract, rotationTankCapacity, rotationTankMaxReceive, rotationTankMaxExtract, upgradeMultiplier);
    }

    public ImpulseTurbineChamber(ResourceLocation id, @Nullable MutableComponent displayName, SeptiLong vaporTankCapacity, SeptiLong vaporTankMaxReceive, SeptiLong vaporTankMaxExtract, SeptiLong rotationTankCapacity, SeptiLong rotationTankMaxReceive, SeptiLong rotationTankMaxExtract, double upgradeMultiplier) {
        super(id);
        this.displayName = displayName;
        this.vaporTankCapacity = vaporTankCapacity;
        this.vaporTankMaxReceive = vaporTankMaxReceive;
        this.vaporTankMaxExtract = vaporTankMaxExtract;
        this.rotationTankCapacity = rotationTankCapacity;
        this.rotationTankMaxReceive = rotationTankMaxReceive;
        this.rotationTankMaxExtract = rotationTankMaxExtract;
        this.upgradeMultiplier = upgradeMultiplier;
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(25, 25, matterType -> true, this.vaporTankCapacity, this.vaporTankMaxReceive, this.vaporTankMaxExtract)
                .create(next(16, 6) + 9, 25, matterType -> matterType.is(MAgMatterTypes.ROTATION), this.rotationTankCapacity, this.rotationTankMaxReceive, this.rotationTankMaxExtract);
    }

    @Override
    public ArrowList getArrows(ArrowList list) {
        return super.getArrows(list)
                .create(next(25) + 1, next(25) - 4, 87, ArrowType.HORIZONTAL);
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
                .matterSlotList(0)
                .build(blockEntity);
        Optional<TurbineRotatingRecipe> recipe = level.getRecipeManager().getRecipeFor(TurbineRotatingRecipe.Type.INSTANCE, container, level);

        if (recipe.isEmpty()) return;

        MatterStack ingredient = recipe.get().getIngredient();
        ingredient.modifyAmount(amount -> modifyByUpgrades(amount, blockEntity));
        MatterStack result = recipe.get().getResult();
        result.modifyAmount(amount -> modifyByUpgrades(amount, blockEntity));

        if (matterHandler.canReceiveFromInside(result, 1) &&
                matterHandler.canExtractFromInside(ingredient, 0)){
            matterHandler.receiveFromInside(result, 1);
            matterHandler.extractFromInside(ingredient, 0);
        } else if (matterHandler.canExtractFromInside(recipe.get().getResult(), 1)){
            matterHandler.receiveFromInside(recipe.get().getResult(), 1);
            matterHandler.extractFromInside(recipe.get().getIngredient(), 0);
        }
    }

    public SeptiLong modifyByUpgrades(SeptiLong amount, MachineBlockEntity blockEntity) {
        return amount.multiply((float) (getUpgradeMultiplier(blockEntity, 4) * this.upgradeMultiplier));
    }
}
