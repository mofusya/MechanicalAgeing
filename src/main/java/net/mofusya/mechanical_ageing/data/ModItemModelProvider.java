package net.mofusya.mechanical_ageing.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.metalset.MetalSet;
import net.mofusya.mechanical_ageing.metalset.ModMetalSet;

import java.util.ArrayList;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MechanicalAgeing.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ArrayList<RegistryObject<Item>> registries = new ArrayList<>();

        for (RegistryObject<Item> item : registries) {
            this.simpleItem(item);
        }

        for (MetalSet metalSet : ModMetalSet.METAL_SET.getEntries()) {
            this.metalSetItem(metalSet);
        }
    }

    private void simpleItem(RegistryObject<Item> item) {
        this.withExistingParent(item.getId().getPath(),
                        new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(MechanicalAgeing.MOD_ID, "item/" + item.getId().getPath()));
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
