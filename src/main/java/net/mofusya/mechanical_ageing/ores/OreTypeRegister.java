package net.mofusya.mechanical_ageing.ores;

import net.kairayk.yklibrary.util.VectorColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.mofusya.mechanical_ageing.items.blocks.item.FoilBlockItem;
import net.mofusya.mechanical_ageing.ores.block.OreTypeBlock;
import net.mofusya.mechanical_ageing.ores.item.OreTypeBlockItem;
import net.mofusya.mechanical_ageing.ores.item.OreTypeItem;
import net.mofusya.mechanical_ageing.util.MultiversalObject;
import net.mofusya.ornatelib.registries.OrnateBlockRegister;
import net.mofusya.ornatelib.registries.OrnateItemRegister;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A simple register for a {@code OreType}
 */
public class OreTypeRegister {

    /**
     * A field to store all {@code OreType}'s that wer registered from this register.
     */
    private final Map<ResourceLocation, OreType> R;
    /**
     * The Item register.
     */
    private final OrnateItemRegister itemR;
    /**
     * The Block register.
     */
    private final OrnateBlockRegister blockR;

    /**
     * The modId for this register.
     */
    private final String modId;
    /**
     * The consumer for accepting this to the registry.
     */
    private final Consumer<OreTypeRegister> registry;

    /**
     * The root constructor.
     *
     * @param modId    The modId to use.
     * @param registry The consumer for accepting this to the registry.
     */
    public OreTypeRegister(String modId, Consumer<OreTypeRegister> registry) {
        this.modId = modId;
        this.registry = registry;

        this.R = new HashMap<>();
        this.itemR = new OrnateItemRegister(modId, 2);
        this.blockR = new OrnateBlockRegister(modId, 2);
    }

    /**
     * A method to register a new {@code OreType}.
     *
     * @param id      The name/id of the new {@code OreType}.
     * @param color   The rendering color of the new {@code OreType}.
     * @param builder The builder on what Item/Block will the new {@code OreType} have.
     * @return The created new {@code OreType}.
     */
    public OreType register(String id, VectorColor color, OreType.Builder builder) {
        @Nullable
        MultiversalObject<Block> ore = builder.ores().get() ? createBlock(id, null, "ore", builder) : null;
        @Nullable
        MultiversalObject<Block> deepslateOre = builder.ores().get() ? createBlock(id, "deepslate", "ore", builder) : null;

        @Nullable
        MultiversalObject<Block> block = builder.blocks().get() ? createBlock(id, null, "block", builder) : null;
        @Nullable
        MultiversalObject<Block> compressedBlock = builder.blocks().get() ? createBlock(id, "compressed", "block", builder) : null;
        @Nullable
        MultiversalObject<Block> doubleCompressedBlock = builder.blocks().get() ? createBlock(id, "duo_compressed", "block", builder) : null;
        @Nullable
        MultiversalObject<Block> tripleCompressedBlock = builder.blocks().get() ? createBlock(id, "tri_compressed", "block", builder) : null;

        @Nullable
        MultiversalObject<Item> raw = builder.raws().get() ? createItem(id, "raw", null, builder) : null;
        @Nullable
        MultiversalObject<Item> icedRaw = builder.raws().get() && builder.processingItems().get() ? createItem(id, "iced_raw", null, builder) : null;

        @Nullable
        MultiversalObject<Item> ingot = builder.ingots().get() ? createItem(id, null, "ingot", builder) : null;
        @Nullable
        MultiversalObject<Item> chunk = builder.ingots().get() ? createItem(id, null, "chunk", builder) : null;

        @Nullable
        MultiversalObject<Item> crystal = builder.crystals().get() ? createItem(id, null, "crystal", builder) : null;

        @Nullable
        MultiversalObject<Item> pureDust = builder.dusts().get() || builder.processingItems().get() ? createItem(id, "pure", "dust", builder) : null;
        @Nullable
        MultiversalObject<Item> dust = builder.processingItems().get() ? createItem(id, null, "dust", builder) : null;
        @Nullable
        MultiversalObject<Item> dirtyDust = builder.processingItems().get() ? createItem(id, "dirty", "dust", builder) : null;
        @Nullable
        MultiversalObject<Item> wetParticle = builder.processingItems().get() ? createItem(id, "wet", "particle", builder) : null;

        OreType oreType = new OreType(
                new ResourceLocation(this.getModId(), id), color, builder,
                ore, deepslateOre,

                block, compressedBlock,
                doubleCompressedBlock, tripleCompressedBlock,

                raw, icedRaw,

                ingot, chunk,

                crystal,

                pureDust, dust, dirtyDust, wetParticle
        );
        this.R.put(oreType.getId(), oreType);
        return oreType;
    }

    private MultiversalObject<Block> createBlock(String oreTypeId, @Nullable String itemTypePrefix, @Nullable String itemTypeSuffix, OreType.Builder builder) {
        StringBuilder idBuilder = new StringBuilder();
        if (itemTypePrefix != null) idBuilder.append(itemTypePrefix).append("_");
        idBuilder.append(oreTypeId);
        if (itemTypeSuffix != null) idBuilder.append("_").append(itemTypeSuffix);
        String id = idBuilder.toString();

        return new MultiversalObject<>(
                this.getBlockR().register(id, new OrnateBlockRegister.Builder()
                        .blockBuild(builder.blockBuild())
                        .blockFunc(build -> new OreTypeBlock(build, oreTypeId, itemTypePrefix, itemTypeSuffix, false))
                        .itemBuild(builder.itemBuild())
                        .itemFunc((block, build) -> new OreTypeBlockItem(block, build, oreTypeId, itemTypePrefix, itemTypeSuffix, false)), 0),
                this.getBlockR().register("multiversal_" + id, new OrnateBlockRegister.Builder()
                        .blockBuild(builder.blockBuild())
                        .blockFunc(build -> new OreTypeBlock(build, oreTypeId, itemTypePrefix, itemTypeSuffix, true))
                        .itemBuild(builder.itemBuild())
                        .itemFunc((block, build) -> new OreTypeBlockItem(block, build, oreTypeId, itemTypePrefix, itemTypeSuffix, true)), 1)
        );
    }

    private MultiversalObject<Item> createItem(String oreTypeId, @Nullable String itemTypePrefix, @Nullable String itemTypeSuffix, OreType.Builder builder) {
        StringBuilder idBuilder = new StringBuilder();
        if (itemTypePrefix != null) idBuilder.append(itemTypePrefix).append("_");
        idBuilder.append(oreTypeId);
        if (itemTypeSuffix != null) idBuilder.append("_").append(itemTypeSuffix);
        String id = idBuilder.toString();

        return new MultiversalObject<>(
                this.getItemR().register(id, build -> new OreTypeItem(build, oreTypeId, itemTypePrefix, itemTypeSuffix, false), builder.itemBuild(), 0),
                this.getItemR().register("multiversal_" + id, build -> new OreTypeItem(build, oreTypeId, itemTypePrefix, itemTypeSuffix, true), builder.itemBuild(), 1)
        );
    }

    public void register(IEventBus eventBus) {
        this.getItemR().register(eventBus);
        this.getBlockR().register(eventBus);
        this.getRegistry().accept(this);
    }

    /**
     * A getter for this {@code R}.
     *
     * @return This {@code R}.
     */
    public Map<ResourceLocation, OreType> getEntries() {
        return Map.copyOf(this.R);
    }

    /**
     * A getter for this {@code itemR}.
     *
     * @return This {@code itemR}.
     */
    public OrnateItemRegister getItemR() {
        return itemR;
    }

    /**
     * A getter for this {@code blockR}.
     *
     * @return This {@code blockR}.
     */
    public OrnateBlockRegister getBlockR() {
        return blockR;
    }

    /**
     * A getter for this {@code modId}.
     *
     * @return This {@code modId}.
     */
    public String getModId() {
        return modId;
    }

    /**
     * A private getter for this {@code registry}.
     *
     * @return This {@code registry}.
     */
    private Consumer<OreTypeRegister> getRegistry() {
        return registry;
    }
}