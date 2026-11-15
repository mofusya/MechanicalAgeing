package net.mofusya.mechanical_ageing.ores;

import net.kairayk.yklibrary.util.VectorColor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.util.ColouredBoolean;
import net.mofusya.mechanical_ageing.util.MultiversalObject;
import net.mofusya.ornatelib.util.annotation.FieldsAreNonNullByDefault;
import net.mofusya.ornatelib.util.annotation.FieldsMayBeNullByDefault;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.util.annotation.ParametersMayBeNullByDefault;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

@FieldsMayBeNullByDefault
@ParametersMayBeNullByDefault
@MethodsReturnNonNullByDefault
public final class OreType {
    @Nonnull
    private final ResourceLocation id;
    @Nonnull
    private final VectorColor color;
    @Nonnull
    private final Builder oreTypeBuilder;
    private final MultiversalObject<Block> ore;
    private final MultiversalObject<Block> deepslateOre;
    private final MultiversalObject<Block> block;
    private final MultiversalObject<Block> compressedBlock;
    private final MultiversalObject<Block> doubleCompressedBlock;
    private final MultiversalObject<Block> tripleCompressedBlock;
    private final MultiversalObject<Item> raw;
    private final MultiversalObject<Item> icedRaw;
    private final MultiversalObject<Item> ingot;
    private final MultiversalObject<Item> chunk;
    private final MultiversalObject<Item> crystal;
    private final MultiversalObject<Item> pureDust;
    private final MultiversalObject<Item> dust;
    private final MultiversalObject<Item> dirtyDust;
    private final MultiversalObject<Item> wetParticle;
    private final ResourceKey<ConfiguredFeature<?, ?>> oreKey;
    private final ResourceKey<PlacedFeature> orePlacedKey;
    private final ResourceKey<BiomeModifier> oreBiomeKey;

    public OreType(@Nonnull ResourceLocation id, @Nonnull VectorColor color, @Nonnull Builder oreTypeBuilder,
                   MultiversalObject<Block> ore, MultiversalObject<Block> deepslateOre,

                   MultiversalObject<Block> block, MultiversalObject<Block> compressedBlock,
                   MultiversalObject<Block> doubleCompressedBlock, MultiversalObject<Block> tripleCompressedBlock,

                   MultiversalObject<Item> raw,
                   MultiversalObject<Item> icedRaw,

                   MultiversalObject<Item> ingot, MultiversalObject<Item> chunk,

                   MultiversalObject<Item> crystal,

                   MultiversalObject<Item> pureDust, MultiversalObject<Item> dust,
                   MultiversalObject<Item> dirtyDust, MultiversalObject<Item> wetParticle
    ) {
        this.id = id;
        this.color = color;
        this.oreTypeBuilder = oreTypeBuilder;
        this.ore = ore;
        this.deepslateOre = deepslateOre;
        this.block = block;
        this.compressedBlock = compressedBlock;
        this.doubleCompressedBlock = doubleCompressedBlock;
        this.tripleCompressedBlock = tripleCompressedBlock;
        this.raw = raw;
        this.icedRaw = icedRaw;
        this.ingot = ingot;
        this.chunk = chunk;
        this.crystal = crystal;
        this.pureDust = pureDust;
        this.dust = dust;
        this.dirtyDust = dirtyDust;
        this.wetParticle = wetParticle;

        if (oreTypeBuilder.ores().get()) {
            this.oreKey = registerKey(id.getPath() + "_ore", Registries.CONFIGURED_FEATURE);
            this.orePlacedKey = registerKey(id.getPath() + "_placed_ore", Registries.PLACED_FEATURE);
            this.oreBiomeKey = registerKey(id.getPath() + "_ore_biome_modifier", ForgeRegistries.Keys.BIOME_MODIFIERS);
        } else {
            this.oreKey = null;
            this.orePlacedKey = null;
            this.oreBiomeKey = null;
        }
    }

    private static <T> ResourceKey<T> registerKey(@NotNull String name, @NotNull ResourceKey<Registry<T>> resourceKey) {
        return ResourceKey.create(resourceKey, new ResourceLocation(MAg.MOD_ID, name));
    }

    public ResourceLocation getId() {
        return id;
    }

    public VectorColor getColor() {
        return color;
    }

    public Builder getOreTypeBuilder() {
        return oreTypeBuilder;
    }

    public MultiversalObject<Block> getOre() {
        return Objects.requireNonNull(ore);
    }

    public MultiversalObject<Block> getDeepslateOre() {
        return Objects.requireNonNull(deepslateOre);
    }

    public MultiversalObject<Block> getBlock() {
        return Objects.requireNonNull(block);
    }

    public MultiversalObject<Block> getCompressedBlock() {
        return Objects.requireNonNull(compressedBlock);
    }

    public MultiversalObject<Block> getDoubleCompressedBlock() {
        return Objects.requireNonNull(doubleCompressedBlock);
    }

    public MultiversalObject<Block> getTripleCompressedBlock() {
        return Objects.requireNonNull(tripleCompressedBlock);
    }

    public MultiversalObject<Item> getRaw() {
        return Objects.requireNonNull(raw);
    }

    public MultiversalObject<Item> getIcedRaw() {
        return Objects.requireNonNull(icedRaw);
    }

    public MultiversalObject<Item> getIngot() {
        return Objects.requireNonNull(ingot);
    }

    public MultiversalObject<Item> getChunk() {
        return Objects.requireNonNull(chunk);
    }

    public MultiversalObject<Item> getCrystal() {
        return Objects.requireNonNull(crystal);
    }

    public MultiversalObject<Item> getPureDust() {
        return Objects.requireNonNull(pureDust);
    }

    public MultiversalObject<Item> getDust() {
        return Objects.requireNonNull(dust);
    }

    public MultiversalObject<Item> getDirtyDust() {
        return Objects.requireNonNull(dirtyDust);
    }

    public MultiversalObject<Item> getWetParticle() {
        return Objects.requireNonNull(wetParticle);
    }

    public ResourceKey<ConfiguredFeature<?, ?>> getOreKey() {
        return Objects.requireNonNull(oreKey);
    }

    public ResourceKey<PlacedFeature> getOrePlacedKey() {
        return Objects.requireNonNull(orePlacedKey);
    }

    public ResourceKey<BiomeModifier> getOreBiomeKey() {
        return Objects.requireNonNull(oreBiomeKey);
    }

    /**
     * The builder of the {@code OreType}, on what Item/Block will the {@code OreType} will have.
     */
    @FieldsAreNonNullByDefault
    @MethodsReturnNonNullByDefault
    @ParametersMayBeNullByDefault
    public static class Builder {
        private final ColouredBoolean hasOres = new ColouredBoolean();
        private final ColouredBoolean hasBlocks = new ColouredBoolean();
        private final ColouredBoolean hasRaws = new ColouredBoolean();
        private final ColouredBoolean hasIngots = new ColouredBoolean();
        private final ColouredBoolean hasCrystals = new ColouredBoolean();
        private final ColouredBoolean hasDusts = new ColouredBoolean();
        private final ColouredBoolean hasProcessingItems = new ColouredBoolean();

        private BlockBehaviour.Properties blockBuild = BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops();
        private Item.Properties itemBuild = new Item.Properties();

        public Builder hasBlockBuild(@Nonnull BlockBehaviour.Properties blockBuild) {
            this.blockBuild = blockBuild;
            return this;
        }

        public Builder hasItemBuild(@Nonnull Item.Properties itemBuild) {
            this.itemBuild = itemBuild;
            return this;
        }

        public Builder hasOres() {
            return hasOres(null);
        }

        public Builder hasOres(VectorColor color) {
            this.hasOres.activate();
            this.hasOres.setColour(color);
            return this;
        }

        public Builder hasBlocks() {
            return hasBlocks(null);
        }

        public Builder hasBlocks(VectorColor color) {
            this.hasBlocks.activate();
            this.hasBlocks.setColour(color);
            return this;
        }

        public Builder hasRaws() {
            return hasRaws(null);
        }

        public Builder hasRaws(VectorColor color) {
            this.hasRaws.activate();
            this.hasRaws.setColour(color);

            if (this.ingots().get() && this.crystals().get()) {
                this.hasCrystals.disable();
            }
            return this;
        }

        public Builder hasIngots() {
            return hasIngots(null);
        }

        public Builder hasIngots(VectorColor color) {
            this.hasIngots.activate();
            this.hasIngots.setColour(color);

            if (this.raws().get() && this.crystals().get()) {
                this.hasCrystals.disable();
            }
            return this;
        }

        public Builder hasCrystals() {
            return hasCrystals(null);
        }

        public Builder hasCrystals(VectorColor color) {
            this.hasCrystals.activate();
            this.hasCrystals.setColour(color);

            if (this.raws().get() && this.ingots().get()) {
                this.hasIngots.disable();
            }
            return this;
        }

        public Builder hasDusts() {
            return hasDusts(null);
        }

        public Builder hasDusts(VectorColor color) {
            this.hasDusts.activate();
            this.hasDusts.setColour(color);
            return this;
        }

        public Builder hasProcessingItems() {
            return hasProcessingItems(null);
        }

        public Builder hasProcessingItems(VectorColor color) {
            this.hasProcessingItems.activate();
            this.hasProcessingItems.setColour(color);
            return this;
        }

        public ColouredBoolean ores() {
            return hasOres;
        }

        public ColouredBoolean blocks() {
            return hasBlocks;
        }

        public ColouredBoolean raws() {
            return hasRaws;
        }

        public ColouredBoolean ingots() {
            return hasIngots;
        }

        public ColouredBoolean crystals() {
            return hasCrystals;
        }

        public ColouredBoolean dusts() {
            return hasDusts;
        }

        public ColouredBoolean processingItems() {
            return hasProcessingItems;
        }

        public BlockBehaviour.Properties blockBuild() {
            return blockBuild;
        }

        public Item.Properties itemBuild() {
            return itemBuild;
        }
    }
}
