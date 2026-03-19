package net.mofusya.mechanical_ageing.metalset;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

public final class MetalSet {
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

    private final String modId;
    private final String id;

    public MetalSet(String modId, String id, RegistryObject<Block> compressedBlock, RegistryObject<Block> block, RegistryObject<Block> ore, RegistryObject<Block> deepslateOre, RegistryObject<Item> ingot, RegistryObject<Item> chunk, RegistryObject<Item> pureDust, RegistryObject<Item> dust, RegistryObject<Item> dirtyDust, RegistryObject<Item> particle, RegistryObject<Item> raw, RegistryObject<Item> nugget, TagKey<Block> mineableWith) {
        this.modId = modId;
        this.id = id;
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

    public TagKey<Block> mineableWith(){
        return this.mineableWith;
    }

    public String getModId() {
        return this.modId;
    }

    public String getId() {
        return this.id;
    }

    public static class Builder{
        private final double density;
        private final int meltingPoint;
        private final int boilingPoint;
        private float radiationMultiplier = 1f;
        private boolean magnetic = false;
        private TagKey<Block> mineableWith = BlockTags.NEEDS_IRON_TOOL;

        private Item.Properties itemBuild = new Item.Properties();
        private BlockBehaviour.Properties blockBuild = BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK);

        public Builder(double density, int meltingPoint, int boilingPoint) {
            this.density = density;
            this.meltingPoint = meltingPoint;
            this.boilingPoint = boilingPoint;
        }

        public Builder radiationMultiplier(float radiationMultiplier){
            this.radiationMultiplier = radiationMultiplier;
            return this;
        }

        public Builder magnetic(){
            this.magnetic = true;
            return this;
        }

        public Builder mineableWith(TagKey<Block> mineableWith){
            this.mineableWith = mineableWith;
            return this;
        }

        public Builder itemBuild(Item.Properties itemBuild){
            this.itemBuild = itemBuild;
            return this;
        }

        public Builder blockBuild(BlockBehaviour.Properties blockBuild){
            this.blockBuild = blockBuild;
            return this;
        }

        public double getDensity() {
            return this.density;
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
    }
}