package net.mofusya.mechanical_ageing.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.items.item.MachineUpgradeArchive;
import net.mofusya.ornatelib.registries.OrnateItemDeferredRegister;

import java.util.ArrayList;

public class MAgItem {
    public static final OrnateItemDeferredRegister ITEMS = OrnateItemDeferredRegister.create(MAg.MOD_ID, 2);

    public static final ArrayList<RegistryObject<Item>> MACHINE_UPGRADE_ARCHIVES = new ArrayList<>();

    static {
        createUpgradeArchives(20);
    }

    private static void createUpgradeArchives(int count) {
        for (int i = 0; i < count; i++) {
            int finalI = i;
            MACHINE_UPGRADE_ARCHIVES.add(ITEMS.register("machine_upgrade_archive_tier_" + (i + 1), () -> new MachineUpgradeArchive(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), finalI), 1));
        }
    }
}