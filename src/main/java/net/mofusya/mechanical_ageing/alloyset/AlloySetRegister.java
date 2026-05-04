package net.mofusya.mechanical_ageing.alloyset;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.items.item.ToppedMetalAttributedItem;
import net.mofusya.mechanical_ageing.matter.MatterManager;
import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.ornatelib.item.AttributedItem;
import net.mofusya.ornatelib.registries.OrnateItemDeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class AlloySetRegister {
    private final String modId;
    private final OrnateItemDeferredRegister itemRegisters;
    private final ArrayList<AlloySet> alloySets = new ArrayList<>();

    public AlloySetRegister(String modId) {
        this(modId, 1);
    }

    public AlloySetRegister(String modId, int slot) {
        this.modId = modId;
        this.itemRegisters = OrnateItemDeferredRegister.create(modId, slot);
    }

    public AlloySet register(String id, AlloySet.Builder builder) {
        return this.register(id, builder, 0);
    }

    public AlloySet register(String id, AlloySet.Builder builder, int slot) {
        RegistryObject<Item> alloy = this.itemRegisters.register(id, () -> new ToppedMetalAttributedItem(builder.getItemBuild(), createAttribute(builder)), slot);
        RegistryObject<Item> compressedAlloy = this.itemRegisters.register("compressed_" + id, () -> new ToppedMetalAttributedItem(builder.getItemBuild(), createAttribute(builder, 27)), slot);
        RegistryObject<Item> duoCompressedAlloy = this.itemRegisters.register("duo_compressed_" + id, () -> new ToppedMetalAttributedItem(builder.getItemBuild(), createAttribute(builder, 27 * 27)), slot);
        RegistryObject<Item> triCompressedAlloy = this.itemRegisters.register("tri_compressed_" + id, () -> new ToppedMetalAttributedItem(builder.getItemBuild(), createAttribute(builder, 27 * 27 * 27)), slot);
        RegistryObject<Item> quadCompressedAlloy = this.itemRegisters.register("quad_compressed_" + id, () -> new ToppedMetalAttributedItem(builder.getItemBuild(), createAttribute(builder, 27 * 27 * 27 * 27)), slot);
        MatterType matterType = MatterManager.create(new ResourceLocation(this.modId, id), new MatterType.Builder(builder.getColor()).build());

        AlloySet toReturn = new AlloySet(this.modId, id, alloy, compressedAlloy, duoCompressedAlloy, triCompressedAlloy, quadCompressedAlloy, matterType, builder.getColor());
        this.alloySets.add(toReturn);
        return toReturn;
    }

    private static AttributedItem.Builder createAttribute(AlloySet.Builder builder) {
        return createAttribute(builder, 1f);
    }

    private static AttributedItem.Builder createAttribute(AlloySet.Builder builder, float compressMultiplier) {
        return new AttributedItem.Builder()
                .attribute("density", builder.getDensity() * compressMultiplier, true)
                .attribute("hardness", builder.getHardness() * compressMultiplier, true)
                .attribute("melting_point", builder.getMeltingPoint(), true)
                .attribute("boiling_point", builder.getBoilingPoint(), true)
                .attribute("radiation_multiplier", builder.getRadiationMultiplier(), true)
                .attribute("magnetic", builder.isMagnetic(), true);
    }

    public void register(IEventBus eventBus) {
        this.itemRegisters.register(eventBus);
    }

    public DeferredRegister<Item> getItemRegister() {
        return this.itemRegisters.getItemRegister();
    }

    public DeferredRegister<Item> getItemRegister(int slot) {
        return this.itemRegisters.getItemRegister(slot);
    }

    public List<RegistryObject<Item>> getMainItems() {
        return this.itemRegisters.getMainItems();
    }

    public List<RegistryObject<Item>> getItems() {
        return this.itemRegisters.getItems();
    }

    public List<RegistryObject<Item>> getItems(int slot) {
        return this.itemRegisters.getItems(slot);
    }

    public List<RegistryObject<Item>> getItems(int... slots) {
        return this.itemRegisters.getItems(slots);
    }

    public List<AlloySet> getEntries() {
        return new ArrayList<>(this.alloySets);
    }
}
