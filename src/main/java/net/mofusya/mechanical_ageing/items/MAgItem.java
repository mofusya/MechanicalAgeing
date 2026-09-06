package net.mofusya.mechanical_ageing.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.items.item.BatteryCoreItem;
import net.mofusya.mechanical_ageing.items.item.BatteryItem;
import net.mofusya.mechanical_ageing.items.item.MachineUpgradeArchive;
import net.mofusya.mechanical_ageing.metalset.IMetalLike;
import net.mofusya.mechanical_ageing.metalset.MAgMetalSets;
import net.mofusya.mechanical_ageing.metalset.MetalSet;
import net.mofusya.ornatelib.registries.OrnateItemDeferredRegister;

import java.util.ArrayList;

public class MAgItem {
    /*
     * 0: Main
     * 1: UpgradeArchives
     * 2: BatteryAndCores
     */
    public static final OrnateItemDeferredRegister ITEMS = OrnateItemDeferredRegister.create(MAg.MOD_ID, 3);

    public static final ArrayList<RegistryObject<Item>> MACHINE_UPGRADE_ARCHIVES = new ArrayList<>();
    public static final ArrayList<RegistryObject<Item>> BATTERY_AND_CORES = new ArrayList<>();

    static {
        createUpgradeArchives(20);
        createBatteries(MAgMetalSets.COBALT, MAgMetalSets.NICKEL);
    }

    private static void createUpgradeArchives(int count) {
        for (int i = 0; i < count; i++) {
            int finalI = i;
            MACHINE_UPGRADE_ARCHIVES.add(ITEMS.register("machine_upgrade_archive_tier_" + (i + 1), () -> new MachineUpgradeArchive(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), finalI), 1));
        }
    }

    private static void createBatteries(IMetalLike... metalLikes){
        for (IMetalLike metalSet : metalLikes) {
            BATTERY_AND_CORES.add(ITEMS.register(metalSet.getId() + "_lithium_battery_core", build -> new BatteryCoreItem(build, metalSet.color()), 2));
            BATTERY_AND_CORES.add(ITEMS.register(metalSet.getId() + "_lithium_battery", build -> new BatteryItem(build, metalSet.color()), 2));
        }
    }
}