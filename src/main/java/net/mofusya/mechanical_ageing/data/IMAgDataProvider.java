package net.mofusya.mechanical_ageing.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.data.loot_tables.MAgBlockLootTableProvider;
import net.mofusya.ornatelib.util.annotation.ParametersAreNonNullByDefault;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * Am interface to make data generation from different classes easier.
 */
@ParametersAreNonNullByDefault
public interface IMAgDataProvider {
    default void generateBlockState(MAgBlockStateProvider provider) {
    }

    default void generateBlockTags(MAgBlockTagProvider provider, HolderLookup.Provider holderLookupProvider) {
    }

    default void generateItemModels(MAgItemModelProvider provider) {
    }

    default void generateItemTags(MAgItemTagProvider provider, HolderLookup.Provider holderLookupProvider) {
    }

    default void generateBlockLootTables(MAgBlockLootTableProvider provider) {
    }

    default void generateRecipes(MAgRecipeProvider provider, Consumer<FinishedRecipe> writer) {
    }

    default void generateWorldGenerations(RegistrySetBuilder builder) {
    }

    default Collection<Block> getKnownBlockLootTables() {
        return List.of();
    }

    static ModelFile getModelFromLoc(ResourceLocation loc, ModelProvider<?> provider){
        return new ModelFile.ExistingModelFile(loc, provider.existingFileHelper);
    }
}
