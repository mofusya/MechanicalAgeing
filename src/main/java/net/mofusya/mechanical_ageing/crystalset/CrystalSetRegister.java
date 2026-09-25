package net.mofusya.mechanical_ageing.crystalset;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.items.item.ToppedMaterialAttributedBlockItem;
import net.mofusya.mechanical_ageing.items.item.ToppedMetalAttributedItem;
import net.mofusya.mechanical_ageing.matter.MatterRegister;
import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.ornatelib.item.AttributedItem;
import net.mofusya.ornatelib.registries.OrnateBlockRegister;
import net.mofusya.ornatelib.registries.OrnateItemRegister;

import java.util.ArrayList;
import java.util.List;

public class CrystalSetRegister {
    private final String modId;
    private final OrnateItemRegister itemRegister;
    private final OrnateBlockRegister blockRegister;
    private final MatterRegister matterRegister;
    private final ArrayList<CrystalSet> crystalSets = new ArrayList<>();

    public CrystalSetRegister(String modId) {
        this(modId, 1);
    }
    public CrystalSetRegister(String modId, int slot) {
        this.modId = modId;
        this.itemRegister = new OrnateItemRegister(modId, slot);
        this.blockRegister = new OrnateBlockRegister(modId, slot);
        this.matterRegister = new MatterRegister();
    }

    public CrystalSet register(String id, CrystalSet.Builder builder) {
        return this.register(id, builder, 0);
    }

    public CrystalSet register(String id, CrystalSet.Builder builder, int slot) {
        RegistryObject<Block> ore = this.blockRegister.register(id + "_ore", createRegisterBuilder(builder), slot);
        RegistryObject<Block> deepslateOre = this.blockRegister.register("deepslate_" + id + "_ore", createRegisterBuilder(builder), slot);
        RegistryObject<Block> block = this.blockRegister.register(id + "_block", createRegisterBuilder(builder), slot);
        RegistryObject<Block> compressedBlock = this.blockRegister.register("compressed_" + id + "_block", createRegisterBuilder(builder), slot);
        RegistryObject<Item> crystal = this.itemRegister.register(id, () -> new ToppedMetalAttributedItem(builder.getItemBuild(), createCrystalAttribute(builder)), slot);
        MatterType liquid = this.matterRegister.create(new ResourceLocation(this.modId, "liquid_" + id), new MatterType.Builder(builder.getColor()).build());
        MatterType gas = this.matterRegister.create(new ResourceLocation(this.modId, id + "_gas"), new MatterType.Builder(builder.getColor() + 0x101010).build());

        CrystalSet crystalSet = new CrystalSet(this.modId, id, ore, deepslateOre, block, compressedBlock, crystal, liquid, gas, builder.getColor(), builder);
        this.crystalSets.add(crystalSet);
        return crystalSet;
    }

    private static OrnateBlockRegister.Builder createRegisterBuilder(CrystalSet.Builder builder) {
        return new OrnateBlockRegister.Builder()
                .itemFunc((block, properties) -> new ToppedMaterialAttributedBlockItem(block, properties, createCrystalAttribute(builder)))
                .itemBuild(builder.getItemBuild())
                .blockBuild(builder.getBlockBuild());
    }

    private static AttributedItem.Builder createCrystalAttribute(CrystalSet.Builder builder) {
        return new AttributedItem.Builder()
                .attribute("density", builder.getDensity(), true)
                .attribute("hardness", builder.getHardness(), true)
                .attribute("melting_point", builder.getMeltingPoint(), true)
                .attribute("boiling_point", builder.getBoilingPoint(), true)
                .attribute("radiation_multiplier", builder.getRadiationMultiplier(), true);
    }

    public void register(IEventBus eventBus) {
        this.itemRegister.register(eventBus);
        this.blockRegister.register(eventBus);
        this.matterRegister.register();
    }

    public DeferredRegister<Item> getItemRegister() {
        return this.getItemRegister(0);
    }

    public DeferredRegister<Item> getItemRegister(int slot) {
        return this.itemRegister.getItemRegister(slot);
    }

    public List<RegistryObject<Item>> getMainItems() {
        return this.itemRegister.getMainItems();
    }

    public List<RegistryObject<Item>> getItems() {
        return this.itemRegister.getItems();
    }

    public List<RegistryObject<Item>> getItems(int slot) {
        return this.itemRegister.getItems(slot);
    }

    public List<RegistryObject<Item>> getItems(int... slots) {
        return this.itemRegister.getItems(slots);
    }

    public DeferredRegister<Block> getBlockRegister() {
        return this.blockRegister.getBlockRegister();
    }

    public DeferredRegister<Block> getBlockRegister(int slot) {
        return this.blockRegister.getBlockRegister(slot);
    }

    public MatterRegister getMatterRegister() {
        return matterRegister;
    }

    public List<RegistryObject<Block>> getMainBlocks() {
        return this.blockRegister.getMainBlocks();
    }

    public List<RegistryObject<Block>> getBlocks() {
        return this.blockRegister.getBlocks();
    }

    public List<RegistryObject<Block>> getBlocks(int slot) {
        return this.blockRegister.getBlocks(slot);
    }

    public List<RegistryObject<Block>> getBlocks(int... slots) {
        return this.blockRegister.getBlocks(slots);
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

    public List<MatterType> getMatterTypes(){
        return new ArrayList<>(this.getMatterRegister().get().values());
    }

    public List<CrystalSet> getEntries() {
        return new ArrayList<>(this.crystalSets);
    }
}
