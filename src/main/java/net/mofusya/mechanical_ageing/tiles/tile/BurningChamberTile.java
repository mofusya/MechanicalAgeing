package net.mofusya.mechanical_ageing.tiles.tile;

import mekanism.common.integration.computer.computercraft.CCMethodCaller;
import net.minecraft.ChatFormatting;
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
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.matter.MAgMatterTypes;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.recipe.FuelRecipe;
import net.mofusya.mechanical_ageing.recipes.recipe.MatterBurningRecipe;
import net.mofusya.mechanical_ageing.tiles.BgTileType;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;

import java.util.Optional;

@MethodsReturnNonnullByDefault
public class BurningChamberTile extends MachineTile {
    public BurningChamberTile(ResourceLocation id) {
        super(id);
    }

    @Override
    public IBgTileType getBgTileType() {
        return BgTileType.BRICK;
    }

    @Override
    public SlotList getSlots(SlotList slots) {
        return super.getSlots(slots)
                .createHorLine(next(16), 16, itemStack -> itemStack.is(Items.COAL), SlotType.NORMAL, 3);
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(next(16, 2), 25, matterType -> matterType.is(MAgMatterTypes.FUEL), SeptiLongValue.MILLION.get(), SeptiLongValue.THOUSAND.get(), SeptiLongValue.ZERO.get())
                .create(next(16, 6) + 9, 25, matterType -> matterType.is(MAgMatterTypes.HEAT), new SeptiLong(2400), SeptiLongValue.ZERO.get(), new SeptiLong(2600));
    }

    @Override
    public ArrowList getArrows(ArrowList list) {
        return super.getArrows(list)
                .create(next(16, 3) + 1, next(25) - 4, 60, ArrowType.HORIZONTAL);
    }

    @Override
    public MutableComponent getDisplayName() {
        return Component.translatable("block.mechanical_ageing.brick_burning_chamber.machine_name");
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        var itemHandler = blockEntity.getItemHandler();
        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();

        if (matterHandler == null) return;


        for (int i = 0; i < 3; i++) {
            MAgContainer container = MAgContainer.builder()
                    .itemSlotsList(1 + i)
                    .build(blockEntity);
            Optional<FuelRecipe> recipe = level.getRecipeManager().getRecipeFor(FuelRecipe.Type.INSTANCE, container, level);
            if (recipe.isPresent()) {
                MatterStack result = recipe.get().getResult();
                if (matterHandler.canReceiveFromInside(result, 0)) {
                    itemHandler.extractItem(1 + i, 1, false);
                    matterHandler.receiveFromInside(result, 0);
                }
            }
        }

        {
            MAgContainer container = MAgContainer.builder()
                    .matterSlotList(0)
                    .build(blockEntity);
            Optional<MatterBurningRecipe> recipe = level.getRecipeManager().getRecipeFor(MatterBurningRecipe.Type.INSTANCE, container, level);
            if (recipe.isPresent()) {
                MatterStack result = recipe.get().getResult();
                if (matterHandler.canReceiveFromInside(result, 1)) {
                    matterHandler.extractFromInside(recipe.get().getIngredient(), 0);
                    matterHandler.receiveFromInside(result, 1);
                }
            }
        }
    }
}
