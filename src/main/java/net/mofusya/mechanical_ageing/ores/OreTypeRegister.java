package net.mofusya.mechanical_ageing.ores;

import net.kairayk.yklibrary.util.VectorColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
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
     * @param modId The modId to use.
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
     * @param id The name/id of the new {@code OreType}.
     * @param color The rendering color of the new {@code OreType}.
     * @param builder The builder on what Item/Block will the new {@code OreType} have.
     * @return The created new {@code OreType}.
     */
    public OreType register(String id, VectorColor color, OreType.Builder builder) {
        @Nullable
        MultiversalObject<Block> ore = builder.ores() ? createBlock(id + "_ore", builder) : null;
        @Nullable
        MultiversalObject<Block> deepslateOre = builder.ores() ? createBlock("deepslate_" + id + "_ore", builder) : null;

        @Nullable
        MultiversalObject<Block> block = builder.blocks() ? createBlock(id + "_block", builder) : null;
        @Nullable
        MultiversalObject<Block> compressedBlock = builder.blocks() ? createBlock("compressed_" + id + "_block", builder) : null;
        @Nullable
        MultiversalObject<Block> doubleCompressedBlock = builder.blocks() ? createBlock("duo_compressed_" + id + "_block", builder) : null;
        @Nullable
        MultiversalObject<Block> tripleCompressedBlock = builder.blocks() ? createBlock("tri_compressed_" + id + "_block", builder) : null;

        @Nullable
        MultiversalObject<Item> raw = builder.raws() ? createItem("raw_" + id, builder) : null;
        @Nullable
        MultiversalObject<Item> icedRaw = builder.raws() && builder.processingItems() ? createItem("iced_raw_" + id, builder) : null;

        @Nullable
        MultiversalObject<Item> ingot = builder.ingots() ? createItem(id + "_ingot", builder) : null;
        @Nullable
        MultiversalObject<Item> chunk = builder.ingots() ? createItem(id + "_chunk", builder) : null;

        @Nullable
        MultiversalObject<Item> crystal = builder.crystals() ? createItem(id + "_crystal", builder) : null;

        @Nullable
        MultiversalObject<Item> pureDust = builder.dusts() || builder.processingItems() ? createItem("pure_" + id + "_dust", builder) : null;
        @Nullable
        MultiversalObject<Item> dust = builder.processingItems() ? createItem(id + "_dust", builder) : null;
        @Nullable
        MultiversalObject<Item> dirtyDust = builder.processingItems() ? createItem("dirty_" + id + "_dust", builder) : null;
        @Nullable
        MultiversalObject<Item> wetParticle = builder.processingItems() ? createItem("wet_" + id + "_particle", builder) : null;

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

    private MultiversalObject<Block> createBlock(String id, OreType.Builder builder) {
        return new MultiversalObject<>(
                this.getBlockR().register(id, () -> new Block(builder.blockBuild()), builder.itemBuild(), 0),
                this.getBlockR().register("multiversal_" + id, () -> new Block(builder.blockBuild()), builder.itemBuild(), 1)
        );
    }

    private MultiversalObject<Item> createItem(String id, OreType.Builder builder) {
        return new MultiversalObject<>(
                this.getItemR().register(id, builder.itemBuild(), 0),
                this.getItemR().register("multiversal_" + id, builder.itemBuild(), 1)
        );
    }

    public void register(IEventBus eventBus){
        this.getItemR().register(eventBus);
        this.getBlockR().register(eventBus);
        this.getRegistry().accept(this);
    }

    /**
     * A getter for this {@code R}.
     * @return This {@code R}.
     */
    public Map<ResourceLocation, OreType> getEntries() {
        return Map.copyOf(this.R);
    }

    /**
     * A getter for this {@code itemR}.
     * @return This {@code itemR}.
     */
    public OrnateItemRegister getItemR() {
        return itemR;
    }

    /**
     * A getter for this {@code blockR}.
     * @return This {@code blockR}.
     */
    public OrnateBlockRegister getBlockR() {
        return blockR;
    }

    /**
     * A getter for this {@code modId}.
     * @return This {@code modId}.
     */
    public String getModId() {
        return modId;
    }

    /**
     * A private getter for this {@code registry}.
     * @return This {@code registry}.
     */
    private Consumer<OreTypeRegister> getRegistry() {
        return registry;
    }
}