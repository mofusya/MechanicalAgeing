package net.mofusya.mechanical_ageing.items.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.mofusya.ornatelib.item.AttributedBlockItem;
import net.mofusya.ornatelib.item.AttributedItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ToppedAttributedBlockItem extends AttributedBlockItem {
    public ToppedAttributedBlockItem(Block block, Properties build, AttributedItem.Builder builder) {
        super(block, build, builder);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> component, TooltipFlag flag) {
        for (String attribute : this.display) {
            String value = "";
            if (this.integerAttribute.containsKey(attribute)) {
                value = String.valueOf(this.getIntegerAttribute(attribute) == Integer.MAX_VALUE ? "ℵ₀" : this.getIntegerAttribute(attribute));
            } else if (this.doubleAttribute.containsKey(attribute)) {
                value = String.valueOf(this.getDoubleAttribute(attribute) == Double.MAX_VALUE ? "ℵ₀" : this.getDoubleAttribute(attribute));
            } else if (this.floatAttribute.containsKey(attribute)) {
                value = String.valueOf(this.getFloatAttribute(attribute) == Float.MAX_VALUE ? "ℵ₀" : this.getFloatAttribute(attribute));
            } else if (this.booleanAttribute.containsKey(attribute)) {
                value = String.valueOf(this.getBooleanAttribute(attribute));
            } else if (this.stringAttribute.containsKey(attribute)) {
                value = this.getStringAttribute(attribute);
            }

            component.add(Component.translatable("item." + this.getModId() + ".attributed_item." + attribute).append(": ").append(Component.literal(value).withStyle(ChatFormatting.DARK_GRAY)));
        }
    }
}
