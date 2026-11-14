package net.mofusya.mechanical_ageing.ores;

import net.kairayk.yklibrary.util.VectorColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.mofusya.mechanical_ageing.util.MultiversalObject;
import net.mofusya.ornatelib.util.annotation.EverythingAreNonNullByDefault;
import net.mofusya.ornatelib.util.annotation.FieldsMayBeNullByDefault;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.util.annotation.ParametersMayBeNullByDefault;

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

    public OreType(ResourceLocation id, VectorColor color, Builder oreTypeBuilder,
                   MultiversalObject<Block> ore, MultiversalObject<Block> deepslateOre,

                   MultiversalObject<Block> block, MultiversalObject<Block> compressedBlock,
                   MultiversalObject<Block> doubleCompressedBlock, MultiversalObject<Block> tripleCompressedBlock,

                   MultiversalObject<Item> raw,
                   MultiversalObject<Item> icedRaw,

                   MultiversalObject<Item> ingot, MultiversalObject<Item> chunk,

                   MultiversalObject<Item> crystal,

                   MultiversalObject<Item> pureDust, MultiversalObject<Item> dust, MultiversalObject<Item> dirtyDust,
                   MultiversalObject<Item> wetParticle) {
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
    }

    public static Builder builder() {
        return new Builder();
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (OreType) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.color, that.color) &&
                Objects.equals(this.oreTypeBuilder, that.oreTypeBuilder) &&
                Objects.equals(this.ore, that.ore) &&
                Objects.equals(this.deepslateOre, that.deepslateOre) &&
                Objects.equals(this.block, that.block) &&
                Objects.equals(this.compressedBlock, that.compressedBlock) &&
                Objects.equals(this.doubleCompressedBlock, that.doubleCompressedBlock) &&
                Objects.equals(this.tripleCompressedBlock, that.tripleCompressedBlock) &&
                Objects.equals(this.raw, that.raw) &&
                Objects.equals(this.icedRaw, that.icedRaw) &&
                Objects.equals(this.ingot, that.ingot) &&
                Objects.equals(this.chunk, that.chunk) &&
                Objects.equals(this.crystal, that.crystal) &&
                Objects.equals(this.pureDust, that.pureDust) &&
                Objects.equals(this.dust, that.dust) &&
                Objects.equals(this.dirtyDust, that.dirtyDust) &&
                Objects.equals(this.wetParticle, that.wetParticle);
    }

    /**
     * The builder of the {@code OreType}, on what Item/Block will the {@code OreType} will have.
     */
    @EverythingAreNonNullByDefault
    public static class Builder {
        private boolean hasOres = false;
        private boolean hasBlocks = false;
        private boolean hasRaws = false;
        private boolean hasIngots = false;
        private boolean hasCrystals = false;
        private boolean hasDusts = false;
        private boolean hasProcessingItems = false;

        private BlockBehaviour.Properties blockBuild = BlockBehaviour.Properties.of();
        private Item.Properties itemBuild = new Item.Properties();

        public Builder hasBlockBuild(BlockBehaviour.Properties blockBuild) {
            this.blockBuild = blockBuild;
            return this;
        }

        public Builder hasItemBuild(Item.Properties itemBuild) {
            this.itemBuild = itemBuild;
            return this;
        }

        public Builder hasOres() {
            this.hasOres = true;
            return this;
        }

        public Builder hasBlocks() {
            this.hasBlocks = true;
            return this;
        }

        public Builder hasRaws() {
            this.hasRaws = true;

            if (this.ingots() && this.crystals()) {
                this.hasCrystals = false;
            }
            return this;
        }

        public Builder hasIngots() {
            this.hasIngots = true;

            if (this.raws() && this.crystals()) {
                this.hasCrystals = false;
            }
            return this;
        }

        public Builder hasCrystals() {
            this.hasCrystals = true;

            if (this.raws() && this.ingots()) {
                this.hasIngots = false;
            }
            return this;
        }

        public Builder hasDusts() {
            this.hasDusts = true;
            return this;
        }

        public Builder hasProcessingItems() {
            this.hasProcessingItems = true;
            return this;
        }

        public boolean ores() {
            return hasOres;
        }

        public boolean blocks() {
            return hasBlocks;
        }

        public boolean raws() {
            return hasRaws;
        }

        public boolean ingots() {
            return hasIngots;
        }

        public boolean crystals() {
            return hasCrystals;
        }

        public boolean dusts() {
            return hasDusts;
        }

        public boolean processingItems() {
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
