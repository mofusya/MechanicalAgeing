package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterSlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.matter.MatterStack;
import net.mofusya.mechanical_ageing.matter.MatterTypes;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.recipe.FuelRecipe;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;

import java.util.Optional;

@MethodsReturnNonnullByDefault
public class BurningChamberTile extends MachineTile {
    public BurningChamberTile(ResourceLocation id) {
        super(id);
    }

    @Override
    public SlotList getSlots(SlotList slots) {
        return super.getSlots(slots)
                .createHorLine(next(16), 16, itemStack -> itemStack.is(Items.COAL), SlotType.NORMAL, 3);
    }

    @Override
    public MatterSlotList getMatterSlots(MatterSlotList slots) {
        return super.getMatterSlots(slots)
                .create(next(16, 2), 25, matterType -> matterType.is(MatterTypes.FUEL), SeptiLongValue.MILLION.get(), SeptiLongValue.THOUSAND.get(), SeptiLongValue.ZERO.get())
                .create(next(16, 6) + 9, 25, matterType -> matterType.is(MatterTypes.HEAT), new SeptiLong(2400), SeptiLongValue.ZERO.get(), new SeptiLong(2600));
    }

    @Override
    public Component getDisplayName() {
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

        if (matterHandler.canExtractFromInside(new MatterStack(MatterTypes.FUEL, 1), 0) &&
                matterHandler.canReceiveFromInside(new MatterStack(MatterTypes.HEAT, 2), 1)
        ) {
            matterHandler.extractFromInside(new MatterStack(MatterTypes.FUEL, 1), 0);
            matterHandler.receiveFromInside(new MatterStack(MatterTypes.HEAT, 2), 1);
        }
    }
}
