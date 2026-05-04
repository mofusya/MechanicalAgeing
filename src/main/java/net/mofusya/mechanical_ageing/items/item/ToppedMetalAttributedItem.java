package net.mofusya.mechanical_ageing.items.item;

import net.mofusya.mechanical_ageing.items.implemts.IMetalAttributed;

public class ToppedMetalAttributedItem extends ToppedAttributedItem implements IMetalAttributed {
    public ToppedMetalAttributedItem(Properties build, Builder builder) {
        super(build, builder);
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

    @Override
    public boolean isMagnetic() {
        return this.getBooleanAttribute("magnetic");
    }
}
