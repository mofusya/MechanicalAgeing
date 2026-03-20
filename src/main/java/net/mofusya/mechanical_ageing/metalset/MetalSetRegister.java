package net.mofusya.mechanical_ageing.metalset;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.items.item.ToppedAttributedBlockItem;
import net.mofusya.mechanical_ageing.items.item.ToppedAttributedItem;
import net.mofusya.ornatelib.item.AttributedItem;
import net.mofusya.ornatelib.registries.OrnateBlockDeferredRegister;
import net.mofusya.ornatelib.registries.OrnateItemDeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class MetalSetRegister {
    private final String modId;
    private final OrnateItemDeferredRegister itemRegisters;
    private final OrnateBlockDeferredRegister blockRegisters;
    private final ArrayList<MetalSet> metalSets = new ArrayList<>();

    private MetalSetRegister(String modId, int slot) {
        this.modId = modId;
        this.itemRegisters = OrnateItemDeferredRegister.create(modId, slot);
        this.blockRegisters = OrnateBlockDeferredRegister.create(modId, slot);
    }

    public MetalSet register(String id, MetalSet.Builder builder) {
        return this.register(id, builder, 0);
    }

    public MetalSet register(String id, MetalSet.Builder builder, int slot) {
        RegistryObject<Block> ore = this.blockRegisters.register(id + "_ore", createDefferBlockBuilder(builder), slot);
        RegistryObject<Block> deepslateOre = this.blockRegisters.register("deepslate_" + id + "_ore", createDefferBlockBuilder(builder), slot);
        RegistryObject<Block> block = this.blockRegisters.register(id + "_block", createDefferBlockBuilder(builder), slot);
        RegistryObject<Block> compressedBlock = this.blockRegisters.register("compressed_" + id + "_block", createDefferBlockBuilder(builder), slot);
        RegistryObject<Item> ingot = this.itemRegisters.register(id + "_ingot", () -> new ToppedAttributedItem(builder.getItemBuild(), createMetalAttribute(builder)));
        RegistryObject<Item> chunk = this.itemRegisters.register(id + "_chunk", () -> new ToppedAttributedItem(builder.getItemBuild(), createMetalAttribute(builder)));
        RegistryObject<Item> pureDust = this.itemRegisters.register("pure_" + id + "_dust", () -> new ToppedAttributedItem(builder.getItemBuild(), createMetalAttribute(builder)));
        RegistryObject<Item> dust = this.itemRegisters.register(id + "_dust", () -> new ToppedAttributedItem(builder.getItemBuild(), createMetalAttribute(builder)));
        RegistryObject<Item> dirtyDust = this.itemRegisters.register("dirty_" + id + "_dust", () -> new ToppedAttributedItem(builder.getItemBuild(), createMetalAttribute(builder)));
        RegistryObject<Item> particle = this.itemRegisters.register(id + "_particle", () -> new ToppedAttributedItem(builder.getItemBuild(), createMetalAttribute(builder)));
        RegistryObject<Item> nugget = this.itemRegisters.register(id + "_nugget", () -> new ToppedAttributedItem(builder.getItemBuild(), createMetalAttribute(builder)));
        RegistryObject<Item> raw = this.itemRegisters.register("raw_" + id, () -> new ToppedAttributedItem(builder.getItemBuild(), createMetalAttribute(builder)));
        MetalSet toReturn = new MetalSet(this.modId, id, compressedBlock, block, ore, deepslateOre, ingot, chunk, pureDust, dust, dirtyDust, particle, raw, nugget, builder.getMineableWith(), builder.getColor(), builder.getOreColor());
        this.metalSets.add(toReturn);
        return toReturn;
    }

    private static OrnateBlockDeferredRegister.Builder createDefferBlockBuilder(MetalSet.Builder builder) {
        return new OrnateBlockDeferredRegister.Builder()
                .itemFunc((block, properties) -> new ToppedAttributedBlockItem(block, properties, createMetalAttribute(builder)))
                .itemBuild(builder.getItemBuild())
                .blockBuild(builder.getBlockBuild());
    }

    private static AttributedItem.Builder createMetalAttribute(MetalSet.Builder builder) {
        return new AttributedItem.Builder()
                .attribute("density", builder.getDensity(), true)
                .attribute("hardness", builder.getHardness(), true)
                .attribute("melting_point", builder.getMeltingPoint(), true)
                .attribute("boiling_point", builder.getBoilingPoint(), true)
                .attribute("radiation_multiplier", builder.getRadiationMultiplier(), true)
                .attribute("magnetic", builder.isMagnetic(), true);
    }

    public void register(IEventBus eventBus) {
        this.itemRegisters.register(eventBus);
        this.blockRegisters.register(eventBus);
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

    public DeferredRegister<Block> getBlockRegister() {
        return this.blockRegisters.getBlockRegister();
    }

    public DeferredRegister<Block> getBlockRegister(int slot) {
        return this.blockRegisters.getBlockRegister(slot);
    }

    public List<RegistryObject<Block>> getMainBlocks() {
        return this.blockRegisters.getMainBlocks();
    }

    public List<RegistryObject<Block>> getBlocks() {
        return this.blockRegisters.getBlocks();
    }

    public List<RegistryObject<Block>> getBlocks(int slot) {
        return this.blockRegisters.getBlocks(slot);
    }

    public List<RegistryObject<Block>> getBlocks(int... slots) {
        return this.blockRegisters.getBlocks(slots);
    }

    public List<ItemLike> getAllItemLikes() {
        List<ItemLike> toReturn = new ArrayList<>();
        for (RegistryObject<Item> item : this.getItems()) {
            toReturn.add(item.get());
        }
        for (RegistryObject<Block> block : this.getBlocks()) {
            toReturn.add(block.get().asItem());
        }
        return toReturn;
    }

    public List<MetalSet> getEntries() {
        return new ArrayList<>(this.metalSets);
    }

    public static MetalSetRegister create(String modId) {
        return create(modId, 1);
    }

    public static MetalSetRegister create(String modId, int slot) {
        return new MetalSetRegister(modId, slot);
    }
}
