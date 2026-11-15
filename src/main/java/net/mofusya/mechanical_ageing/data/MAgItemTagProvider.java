package net.mofusya.mechanical_ageing.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.mofusya.mechanical_ageing.C;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class MAgItemTagProvider extends ItemTagsProvider {

    public MAgItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, future, completableFuture, C.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider holderLookupProvider) {


        for (IMAgDataProvider provider : MAgDataGenerator.getProviders()) {
            provider.generateItemTags(this, holderLookupProvider);
        }
    }

    @Override
    public IntrinsicTagAppender<Item> tag(TagKey<Item> tagKey) {
        return super.tag(tagKey);
    }
}
