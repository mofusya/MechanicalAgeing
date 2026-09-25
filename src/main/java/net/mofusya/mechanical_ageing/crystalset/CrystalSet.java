package net.mofusya.mechanical_ageing.crystalset;

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
import net.mofusya.mechanical_ageing.alloyset.AlloySet;
import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.mechanical_ageing.metalset.IMetalLike;
import net.mofusya.mechanical_ageing.metalset.MetalSet;
import net.mofusya.mechanical_ageing.world_generation.ModBiomeModifiers;
import net.mofusya.mechanical_ageing.world_generation.ModConfiguredFeatures;
import net.mofusya.mechanical_ageing.world_generation.ModPlacedFeatures;

public final class CrystalSet implements IMetalLike {
    private final String modId;
    private final String id;

    private final RegistryObject<Block> ore;
    private final RegistryObject<Block> deepslateOre;
    private final RegistryObject<Block> block;
    private final RegistryObject<Block> compressedBlock;
    private final RegistryObject<Item> crystal;
    private final MatterType liquid;
    private final MatterType gas;
    private final int color;

    private final ResourceKey<ConfiguredFeature<?, ?>> oreKey;
    private final ResourceKey<PlacedFeature> orePlacedKey;
    private final ResourceKey<BiomeModifier> oreBiomeKey;

    private final Builder builder;

    public CrystalSet(String modId, String id, RegistryObject<Block> ore, RegistryObject<Block> deepslateOre, RegistryObject<Block> block, RegistryObject<Block> compressedBlock, RegistryObject<Item> crystal, MatterType liquid, MatterType gas, int color, Builder builder) {
        this.modId = modId;
        this.id = id;
        this.ore = ore;
        this.deepslateOre = deepslateOre;
        this.block = block;
        this.compressedBlock = compressedBlock;
        this.crystal = crystal;
        this.liquid = liquid;
        this.gas = gas;
        this.color = color;
        this.oreKey = ModConfiguredFeatures.registerKey(id + "_ore");
        this.orePlacedKey = ModPlacedFeatures.registerKey(id + "_ore_placed");
        this.oreBiomeKey = ModBiomeModifiers.registerKey("add_" + id + "_ore");
        this.builder = builder;
    }

    public Block block() {
        return block.get();
    }

    public Block compressedBlock() {
        return compressedBlock.get();
    }

    public Item crystal() {
        return crystal.get();
    }

    public MatterType gas() {
        return gas;
    }

    public MatterType liquid() {
        return liquid;
    }

    public String getModId() {
        return modId;
    }

    public Block ore() {
        return ore.get();
    }

    public Block deepslateOre() {
        return deepslateOre.get();
    }

    public Builder getBuilder() {
        return builder;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int color() {
        return this.color;
    }

    public ResourceKey<BiomeModifier> getOreBiomeKey() {
        return oreBiomeKey;
    }

    public ResourceKey<ConfiguredFeature<?, ?>> getOreKey() {
        return oreKey;
    }

    public ResourceKey<PlacedFeature> getOrePlacedKey() {
        return orePlacedKey;
    }

    public static Builder builder(double density, double hardness, int meltingPoint, int boilingPoint){
        return new Builder(density, hardness, meltingPoint, boilingPoint);
    }

    public static class Builder {
        private final double density;
        private final double hardness;
        private final int meltingPoint;
        private final int boilingPoint;
        private float radiationMultiplier = 1f;
        private TagKey<Block> mineableWith = BlockTags.NEEDS_IRON_TOOL;
        private int color = 0xFFFFFF;

        private Item.Properties itemBuild = new Item.Properties();
        private BlockBehaviour.Properties blockBuild = BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK);

        private Builder(double density, double hardness, int meltingPoint, int boilingPoint) {
            this.density = density;
            this.hardness = hardness;
            this.meltingPoint = meltingPoint;
            this.boilingPoint = boilingPoint;
        }

        public CrystalSet.Builder color(int color) {
            this.color = color;
            return this;
        }

        public CrystalSet.Builder radiationMultiplier(float radiationMultiplier) {
            this.radiationMultiplier = radiationMultiplier;
            return this;
        }

        public CrystalSet.Builder mineableWith(TagKey<Block> mineableWith) {
            this.mineableWith = mineableWith;
            return this;
        }

        public CrystalSet.Builder itemBuild(Item.Properties itemBuild) {
            this.itemBuild = itemBuild;
            return this;
        }

        public CrystalSet.Builder blockBuild(BlockBehaviour.Properties blockBuild) {
            this.blockBuild = blockBuild;
            return this;
        }

        public int getBoilingPoint() {
            return this.boilingPoint;
        }

        public int getColor() {
            return this.color;
        }

        public double getDensity() {
            return this.density;
        }

        public double getHardness() {
            return this.hardness;
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

        public int getMeltingPoint() {
            return this.meltingPoint;
        }

        public float getRadiationMultiplier() {
            return this.radiationMultiplier;
        }

        public Builder copy(){
            return new Builder(this.density, this.hardness, this.meltingPoint, this.boilingPoint)
                    .radiationMultiplier(this.radiationMultiplier)
                    .mineableWith(this.mineableWith)
                    .itemBuild(this.itemBuild)
                    .blockBuild(this.blockBuild)
                    .color(this.color);
        }
    }
}
