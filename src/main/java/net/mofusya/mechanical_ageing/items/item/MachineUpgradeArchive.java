package net.mofusya.mechanical_ageing.items.item;

import net.minecraft.world.item.Item;
import net.mofusya.mechanical_ageing.items.implemts.IMachineUpgradeArchive;

public class MachineUpgradeArchive extends Item implements IMachineUpgradeArchive {

    private final int upgradeValue;

    public MachineUpgradeArchive(Properties build, int upgradeValue) {
        super(build);
        this.upgradeValue = upgradeValue;
    }

    @Override
    public int getUpgradeValue() {
        return this.upgradeValue;
    }
}
