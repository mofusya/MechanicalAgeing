package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.button.ButtonList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.recipes.MAgContainer;
import net.mofusya.mechanical_ageing.recipes.recipe.TriDimCraftingRecipe;

import java.util.Optional;

public class TriDimCraftingTableTile extends MachineTile {
    public TriDimCraftingTableTile(ResourceLocation id) {
        super(id);
    }

    @Override
    public SlotList getSlots(SlotList slots) {
        var toReturn = new SlotList();
        for (int i = 0; i < 3; i++) {
            toReturn.createVerLine(6, 25 + (18 * i), itemStack -> true, SlotType.NORMAL, 3)
                    .createVerLine(61, 16 + (18 * i), itemStack -> true, SlotType.NORMAL, 3)
                    .createVerLine(116, 7 + (18 * i), itemStack -> true, SlotType.NORMAL, 3);
        }
        return toReturn.create(133, 63, itemStack -> false, SlotType.EXTRACT_ONLY);
    }

    @Override
    public ButtonList getButtons(ButtonList list) {
        return super.getButtons(list)
                .create(152, 63, SlotType.EXTRACT_ONLY);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.mechanical_ageing.tri_dimensional_crafting_table.machine_name");
    }

    public static final int OUTPUT_SLOT = 27;

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
    }

    @Override
    public void onButtonPress(int type, ServerPlayer player, MachineBlockEntity blockEntity) {
        Level level = blockEntity.getLevel();
        switch (type) {
            case 0 -> {
                var itemHandler = blockEntity.getItemHandler();
                MAgContainer container = MAgContainer.builder().itemSlots(26).build(blockEntity);

                Optional<TriDimCraftingRecipe> recipe = level.getRecipeManager().getRecipeFor(TriDimCraftingRecipe.Type.INSTANCE, container, level);
                if (recipe.isPresent()) {

                    ItemStack result = recipe.get().getResultItem(null);
                    if (this.canItemInsertToSlot(blockEntity, OUTPUT_SLOT, result)) {
                        for (int i = 0; i < 27; i++) {
                            itemHandler.extractItem(i, 1, false);
                        }
                        this.insertItemToSlot(blockEntity, OUTPUT_SLOT, result);
                    }
                }
            }
        }
        player.playSound(SoundEvents.UI_BUTTON_CLICK.get());
    }
}
