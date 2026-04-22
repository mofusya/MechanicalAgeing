package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.button.ButtonList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotList;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import net.mofusya.mechanical_ageing.recipes.recipe.TriDimCraftingRecipe;

import java.util.Optional;

public class TriDimCraftingTable extends MachineTile {
    public TriDimCraftingTable(ResourceLocation id) {
        super(id);
    }

    @Override
    public SlotList getSlots(SlotList slots) {
        return new SlotList()
                .createCube(6, 25, itemStack -> true, SlotType.NORMAL, 3)
                .createCube(61, 16, itemStack -> true, SlotType.NORMAL, 3)
                .createCube(116, 7, itemStack -> true, SlotType.NORMAL, 3)
                .create(133, 63, itemStack -> false, SlotType.EXTRACT_ONLY);
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
                SimpleContainer inventory = new SimpleContainer(27);
                for (int i = 0; i < 27; i++) {
                    inventory.setItem(i, itemHandler.getStackInSlot(i));
                }

                Optional<TriDimCraftingRecipe> recipe = level.getRecipeManager().getRecipeFor(TriDimCraftingRecipe.Type.INSTANCE, inventory, level);
                if (recipe.isPresent()) {

                    ItemStack result = recipe.get().assemble(inventory, null);
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
