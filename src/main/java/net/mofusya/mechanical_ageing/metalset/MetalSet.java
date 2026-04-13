package net.mofusya.mechanical_ageing.metalset;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.world_generation.ModBiomeModifiers;
import net.mofusya.mechanical_ageing.world_generation.ModConfiguredFeatures;
import net.mofusya.mechanical_ageing.world_generation.ModPlacedFeatures;

public final class MetalSet {
    private final ResourceKey<ConfiguredFeature<?, ?>> oreKey;
    private final ResourceKey<PlacedFeature> orePlacedKey;
    private final ResourceKey<BiomeModifier> oreBiomeKey;

    private final RegistryObject<Block> compressedBlock;
    private final RegistryObject<Block> block;
    private final RegistryObject<Block> ore;
    private final RegistryObject<Block> deepslateOre;
    private final RegistryObject<Item> ingot;
    private final RegistryObject<Item> chunk;
    private final RegistryObject<Item> pureDust;
    private final RegistryObject<Item> dust;
    private final RegistryObject<Item> dirtyDust;
    private final RegistryObject<Item> particle;
    private final RegistryObject<Item> raw;
    private final RegistryObject<Item> nugget;
    private final TagKey<Block> mineableWith;
    private final int color;
    private final int oreColor;
    private final String oreName;

    private final String modId;
    private final String id;
    private final String name;

    public MetalSet(String modId, String id, String name, RegistryObject<Block> compressedBlock, RegistryObject<Block> block, RegistryObject<Block> ore, RegistryObject<Block> deepslateOre, RegistryObject<Item> ingot, RegistryObject<Item> chunk, RegistryObject<Item> pureDust, RegistryObject<Item> dust, RegistryObject<Item> dirtyDust, RegistryObject<Item> particle, RegistryObject<Item> raw, RegistryObject<Item> nugget, TagKey<Block> mineableWith, int color, int oreColor, String oreName) {
        this.modId = modId;
        this.id = id;
        this.name = name;
        this.compressedBlock = compressedBlock;
        this.block = block;
        this.ore = ore;
        this.deepslateOre = deepslateOre;
        this.ingot = ingot;
        this.chunk = chunk;
        this.pureDust = pureDust;
        this.dust = dust;
        this.dirtyDust = dirtyDust;
        this.particle = particle;
        this.raw = raw;
        this.nugget = nugget;
        this.mineableWith = mineableWith;
        this.color = color;
        this.oreColor = oreColor;
        this.oreName = oreName;

        this.oreKey = ModConfiguredFeatures.registerKey(id + "_ore");
        this.orePlacedKey = ModPlacedFeatures.registerKey(id + "_ore_placed");
        this.oreBiomeKey = ModBiomeModifiers.registerKey("add_" + id + "_ore");
    }

    public Block compressedBlock() {
        return this.compressedBlock.get();
    }

    public Block block() {
        return this.block.get();
    }

    public Block ore() {
        return this.ore.get();
    }

    public Block deepslateOre() {
        return this.deepslateOre.get();
    }

    public Item ingot() {
        return this.ingot.get();
    }

    public Item chunk() {
        return this.chunk.get();
    }

    public Item pureDust() {
        return this.pureDust.get();
    }

    public Item dust() {
        return this.dust.get();
    }

    public Item dirtyDust() {
        return this.dirtyDust.get();
    }

    public Item particle() {
        return this.particle.get();
    }

    public Item raw() {
        return this.raw.get();
    }

    public Item nugget() {
        return this.nugget.get();
    }

    public TagKey<Block> mineableWith() {
        return this.mineableWith;
    }

    public int color() {
        return this.color;
    }

    public int oreColor() {
        return this.oreColor;
    }

    public String getOreName() {
        return this.oreName;
    }

    public String getModId() {
        return this.modId;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ResourceKey<ConfiguredFeature<?, ?>> getOreKey() {
        return this.oreKey;
    }

    public ResourceKey<PlacedFeature> getOrePlacedKey() {
        return this.orePlacedKey;
    }

    public ResourceKey<BiomeModifier> getOreBiomeKey() {
        return this.oreBiomeKey;
    }

    public static Builder builder(String name, double density, double hardness, int meltingPoint, int boilingPoint){
        return new Builder(name, density, hardness, meltingPoint, boilingPoint);
    }

    public static class Builder {
        private final String name;
        private final double density;
        private final double hardness;
        private final int meltingPoint;
        private final int boilingPoint;
        private float radiationMultiplier = 1f;
        private boolean magnetic = false;
        private TagKey<Block> mineableWith = BlockTags.NEEDS_IRON_TOOL;
        private int color = 0xFFFFFF;
        private int oreColor = -404;
        private String oreName = null;

        private Item.Properties itemBuild = new Item.Properties();
        private BlockBehaviour.Properties blockBuild = BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK);

        private Builder(String name, double density, double hardness, int meltingPoint, int boilingPoint) {
            this.name = name;
            this.density = density;
            this.hardness = hardness;
            this.meltingPoint = meltingPoint;
            this.boilingPoint = boilingPoint;
        }

        public Builder radiationMultiplier(float radiationMultiplier) {
            this.radiationMultiplier = radiationMultiplier;
            return this;
        }

        public Builder magnetic() {
            this.magnetic = true;
            return this;
        }

        public Builder mineableWith(TagKey<Block> mineableWith) {
            this.mineableWith = mineableWith;
            return this;
        }

        public Builder itemBuild(Item.Properties itemBuild) {
            this.itemBuild = itemBuild;
            return this;
        }

        public Builder blockBuild(BlockBehaviour.Properties blockBuild) {
            this.blockBuild = blockBuild;
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Builder oreColor(int oreColor) {
            this.oreColor = oreColor;
            return this;
        }

        public Builder oreName(String oreName) {
            this.oreName = oreName;
            return this;
        }

        public String getName() {
            return this.name;
        }

        public double getDensity() {
            return this.density;
        }

        public double getHardness() {
            return this.hardness;
        }

        public int getBoilingPoint() {
            return this.boilingPoint;
        }

        public int getMeltingPoint() {
            return this.meltingPoint;
        }

        public float getRadiationMultiplier() {
            return this.radiationMultiplier;
        }

        public boolean isMagnetic() {
            return this.magnetic;
        }

        public TagKey<Block> getMineableWith() {
            return this.mineableWith;
        }

        public Item.Properties getItemBuild() {
            return this.itemBuild;
        }

        public BlockBehaviour.Properties getBlockBuild() {
            return this.blockBuild;
        }

        public int getColor() {
            return this.color;
        }

        public int getOreColor() {
            return this.oreColor;
        }

        public String getOreName() {
            return this.oreName;
        }
    }
}