package net.mofusya.mechanical_ageing.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.items.item.AlloyIngotItem;
import net.mofusya.ornatelib.item.AttributedItem;
import net.mofusya.ornatelib.registries.OrnateItemDeferredRegister;

public class ModItem {
    public static final OrnateItemDeferredRegister ITEMS = OrnateItemDeferredRegister.create(MechanicalAgeing.MOD_ID);

    public static final RegistryObject<Item> ALLOY_INGOT = ITEMS.register("alloy_ingot", (build, builder) -> new AlloyIngotItem(build, builder) {
        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (hand == InteractionHand.MAIN_HAND) {
                ItemStack offHand = player.getOffhandItem();
                if (offHand.getItem() instanceof AlloyIngotItem offHandItem) {
                    ItemStack itemStack = player.getMainHandItem();
                    this.setAttribute(itemStack, "density", this.getDoubleAttribute(itemStack, "density") + offHandItem.getDoubleAttribute(offHand, "density"));
                    offHand.shrink(1);
                }
            }
            return super.use(level, player, hand);
        }
    }, new AttributedItem.Builder());
}