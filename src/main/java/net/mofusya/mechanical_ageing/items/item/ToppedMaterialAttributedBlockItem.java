package net.mofusya.mechanical_ageing.items.item;

import net.minecraft.world.level.block.Block;
import net.mofusya.mechanical_ageing.items.implemts.IMaterialAttributed;
import net.mofusya.mechanical_ageing.items.implemts.IMetalAttributed;
import net.mofusya.ornatelib.item.AttributedItem;

public class ToppedMaterialAttributedBlockItem extends ToppedAttributedBlockItem implements IMaterialAttributed {
    public ToppedMaterialAttributedBlockItem(Block block, Properties build, AttributedItem.Builder builder) {
        super(block, build, builder);
    }

    @Override
    public double getDensity() {
        return this.getDoubleAttribute("density");
    }

    @Override
    public double getHardness() {
        return this.getDoubleAttribute("hardness");
    }

    @Override
    public int getMeltingPoint() {
        return this.getIntegerAttribute("melting_point");
    }

    @Override
    public int getBoilingPoint() {
        return this.getIntegerAttribute("boiling_point");
    }

    @Override
    public float getRadiationMultiplier() {
        return this.getFloatAttribute("radiation_multiplier");
    }
}
