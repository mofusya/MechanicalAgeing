package net.mofusya.mechanical_ageing.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.C;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class MAgBlockTagProvider extends BlockTagsProvider {
    public MAgBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, C.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider holderLookupProvider) {
        {
            ArrayList<RegistryObject<Block>> registries = new ArrayList<>();


            var tags = this.tag(BlockTags.MINEABLE_WITH_PICKAXE);
            for (RegistryObject<Block> block : registries) {
                tags.add(block.get());
            }
        }

        {
            ArrayList<RegistryObject<Block>> registries = new ArrayList<>();


            var tags = this.tag(BlockTags.NEEDS_IRON_TOOL);
            for (RegistryObject<Block> block : registries) {
                tags.add(block.get());
            }
        }

        for (IMAgDataProvider provider : MAgDataGenerator.getProviders()) {
            provider.generateBlockTags(this, holderLookupProvider);
        }
    }

    @Override
    public IntrinsicTagAppender<Block> tag(TagKey<Block> tagKey) {
        return super.tag(tagKey);
    }
}
