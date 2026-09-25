package net.mofusya.mechanical_ageing.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.alloyset.AlloySet;
import net.mofusya.mechanical_ageing.alloyset.MAgAlloySets;
import net.mofusya.mechanical_ageing.crystalset.CrystalSet;
import net.mofusya.mechanical_ageing.crystalset.MAgCrystalSets;
import net.mofusya.mechanical_ageing.items.MAgItems;
import net.mofusya.mechanical_ageing.items.item.BatteryItem;
import net.mofusya.mechanical_ageing.metalset.MAgMetalSets;
import net.mofusya.mechanical_ageing.metalset.MetalSet;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MAg.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ArrayList<RegistryObject<Item>> registries = new ArrayList<>();

        registries.addAll(MAgItems.ITEMS.getItems(0));

        for (RegistryObject<Item> item : registries) {
            this.simpleItem(item);
        }

        for (int i = 0; i < MAgItems.ITEMS.getItems(1).size(); i++) {
            RegistryObject<Item> archive = MAgItems.ITEMS.getItems(1).get(i);
            machineUpgradeArchiveItem(archive, i + 1);
        }
        for (RegistryObject<Item> batteryOrCore : MAgItems.ITEMS.getItems(2)) {
            if (batteryOrCore.get() instanceof BatteryItem) {
                layeredSimpleItem(batteryOrCore, "lithium_battery", "lithium_battery_sublayer");
            } else {
                layeredSimpleItem(batteryOrCore, "lithium_battery_core", "lithium_battery_core_sublayer");
            }
        }
        for (AlloySet alloySet : MAgAlloySets.ALLOYS.getEntries()) {
            alloySetItem(alloySet);
        }
        for (MetalSet metalSet : MAgMetalSets.METAL_SET.getEntries()) {
            this.metalSetItem(metalSet);
        }
        for (CrystalSet crystalSet : MAgCrystalSets.CRYSTALS.getEntries()) {
            this.withExistingParent(getPath(crystalSet.crystal()), getCrystalSetParentLoc("crystal"));
        }
    }

    private void simpleItem(RegistryObject<Item> item) {
        this.withExistingParent(item.getId().getPath(),
                        new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(MAg.MOD_ID, "item/" + item.getId().getPath()));
    }

    private void layeredSimpleItem(RegistryObject<Item> item, @Nullable String... textures) {
        var model = this.withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"));
        for (int i = 0; i < textures.length; i++) {
            model.texture("layer" + i, new ResourceLocation(MAg.MOD_ID, "item/" + (textures[i] == null ? item.getId().getPath() : textures[i])));
        }
    }

    private void simpleItemWithSublayer(RegistryObject<Item> item, String prefix) {
        this.layeredSimpleItem(item, item.getId().getPath(), item.getId().getPath() + prefix);
    }

    private void machineUpgradeArchiveItem(RegistryObject<Item> archive, int value) {
        int generationCount = 1;
        int modValue = value;
        while (true) {
            int i = modValue - 5;
            if (i <= 0) break;
            modValue = i;
            generationCount++;
        }

        this.withExistingParent(archive.getId().getPath(), new ResourceLocation("item/generated"))
                .texture("layer0", modLoc("item/machine_upgrade_archive_" +
                        switch (modValue) {
                            case 1 -> "single";
                            case 2 -> "duo";
                            case 3 -> "tri";
                            case 4 -> "quad";
                            case 5 -> "quint";
                            default -> throw new IllegalStateException("Unexpected value: " + modValue);
                        }
                )).texture("layer1", modLoc("item/machine_upgrade_archive_gen" + generationCount));
    }

    private void alloySetItem(AlloySet alloySet) {
        this.withExistingParent(getPath(alloySet.alloy()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/alloy"));
        this.withExistingParent(getPath(alloySet.compressed()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/compressed_alloy"));
        this.withExistingParent(getPath(alloySet.duoCompressed()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/duo_compressed_alloy"));
        this.withExistingParent(getPath(alloySet.triCompressed()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/tri_compressed_alloy"));
        this.withExistingParent(getPath(alloySet.quadCompressed()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/quad_compressed_alloy"));
    }

    private void metalSetItem(MetalSet metalSet) {
        this.withExistingParent(getPath(metalSet.ingot()), getMetalSetParentLoc("ingot"));
        this.withExistingParent(getPath(metalSet.chunk()), getMetalSetParentLoc("chunk"));
        this.withExistingParent(getPath(metalSet.pureDust()), getMetalSetParentLoc("pure_dust"));
        this.withExistingParent(getPath(metalSet.dust()), getMetalSetParentLoc("dust"));
        this.withExistingParent(getPath(metalSet.dirtyDust()), getMetalSetParentLoc("dirty_dust"));
        this.withExistingParent(getPath(metalSet.particle()), getMetalSetParentLoc("particle"));
        this.withExistingParent(getPath(metalSet.raw()), getMetalSetParentLoc("raw"));
        this.withExistingParent(getPath(metalSet.nugget()), getMetalSetParentLoc("nugget"));
    }

    private static ResourceLocation getMetalSetParentLoc(String item){
        return new ResourceLocation(C.MOD_ID, "item/metalset/" + item);
    }

    private static ResourceLocation getCrystalSetParentLoc(String item){
        return new ResourceLocation(C.MOD_ID, "item/crystalset/" + item);
    }

    private static ResourceLocation getAlloySetParentLoc(String item){
        return new ResourceLocation(C.MOD_ID, "item/alloyset/" + item);
    }

    private static String getPath(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }
}
