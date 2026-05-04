package net.mofusya.mechanical_ageing.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.blocks.MAgBlocks;
import net.mofusya.mechanical_ageing.metalset.MAgMetalSets;
import net.mofusya.mechanical_ageing.metalset.MetalSet;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MAg.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        {
            ArrayList<RegistryObject<Block>> registries = new ArrayList<>();

            registries.addAll(MAgMetalSets.METAL_SET.getBlocks());
            registries.add(MAgBlocks.REINFORCED_BRICKS);

            var tags = this.tag(BlockTags.MINEABLE_WITH_PICKAXE);
            for (RegistryObject<Block> block : registries) {
                tags.add(block.get());
            }
        }

        {
            for (MetalSet metalSet : MAgMetalSets.METAL_SET.getEntries()) {
                var tags = this.tag(metalSet.mineableWith());
                tags.add(metalSet.compressedBlock());
                tags.add(metalSet.block());
                tags.add(metalSet.ore());
                tags.add(metalSet.deepslateOre());
            }
        }
    }
}
