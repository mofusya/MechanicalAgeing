package net.mofusya.mechanical_ageing.data.loot_tables;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.blocks.MAgBlocks;
import net.mofusya.mechanical_ageing.metalset.MetalSet;
import net.mofusya.mechanical_ageing.metalset.MAgMetalSets;

import java.util.ArrayList;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        ArrayList<RegistryObject<Block>> registries = new ArrayList<>();

        registries.addAll(MAgBlocks.BLOCKS.getBlocks());

        for (RegistryObject<Block> block : registries) {
            this.dropSelf(block.get());
        }

        for (MetalSet metalSet : MAgMetalSets.METAL_SET.getEntries()) {
            this.dropSelf(metalSet.compressedBlock());
            this.dropSelf(metalSet.block());
            this.add(metalSet.ore(), block -> createOreDrop(metalSet.ore(), metalSet.raw()));
            this.add(metalSet.deepslateOre(), block -> createOreDrop(metalSet.deepslateOre(), metalSet.raw()));
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.addAll(MAgMetalSets.METAL_SET.getBlocks().stream().map(RegistryObject::get).toList());
        blocks.addAll(MAgBlocks.BLOCKS.getBlocks().stream().map(RegistryObject::get).toList());
        return blocks;
    }
}