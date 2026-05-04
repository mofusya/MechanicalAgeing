package net.mofusya.mechanical_ageing.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.alloyset.AlloySet;
import net.mofusya.mechanical_ageing.alloyset.MAgAlloySets;
import net.mofusya.mechanical_ageing.items.MAgItem;
import net.mofusya.mechanical_ageing.metalset.MAgMetalSets;
import net.mofusya.mechanical_ageing.metalset.MetalSet;

import java.util.ArrayList;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MAg.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ArrayList<RegistryObject<Item>> registries = new ArrayList<>();

        for (RegistryObject<Item> item : registries) {
            this.simpleItem(item);
        }

        for (int i = 0; i < MAgItem.ITEMS.getItems(1).size(); i++) {
            RegistryObject<Item> archive = MAgItem.ITEMS.getItems(1).get(i);
            machineUpgradeArchiveItem(archive, i + 1);
        }
        for (AlloySet alloySet : MAgAlloySets.ALLOYS.getEntries()) {
            alloySetItem(alloySet);
        }
        for (MetalSet metalSet : MAgMetalSets.METAL_SET.getEntries()) {
            this.metalSetItem(metalSet);
        }
    }

    private void simpleItem(RegistryObject<Item> item) {
        this.withExistingParent(item.getId().getPath(),
                        new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(MAg.MOD_ID, "item/" + item.getId().getPath()));
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
        this.withExistingParent(getPath(metalSet.ingot()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/ingot"));
        this.withExistingParent(getPath(metalSet.chunk()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/chunk"));
        this.withExistingParent(getPath(metalSet.pureDust()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/pure_dust"));
        this.withExistingParent(getPath(metalSet.dust()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/dust"));
        this.withExistingParent(getPath(metalSet.dirtyDust()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/dirty_dust"));
        this.withExistingParent(getPath(metalSet.particle()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/particle"));
        this.withExistingParent(getPath(metalSet.raw()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/raw"));
        this.withExistingParent(getPath(metalSet.nugget()), new ResourceLocation("item/generated")).texture("layer0", modLoc("item/nugget"));
    }

    private static String getPath(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }
}
