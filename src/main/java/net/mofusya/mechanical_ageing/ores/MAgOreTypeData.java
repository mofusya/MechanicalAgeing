package net.mofusya.mechanical_ageing.ores;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.data.*;
import net.mofusya.mechanical_ageing.data.loot_tables.MAgBlockLootTableProvider;
import net.mofusya.mechanical_ageing.util.MultiversalObject;
import net.mofusya.ornatelib.util.annotation.ParametersAreNonNullByDefault;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A class to generate all the assets/data files needed for the MAg {@code OreType}s
 */
@ParametersAreNonNullByDefault
public enum MAgOreTypeData implements IMAgDataProvider {
    INSTANCE;

    @Override
    public void generateRecipes(MAgRecipeProvider provider, Consumer<FinishedRecipe> writer) {
        for (OreType oreType : MAgOreTypes.R.getEntries().values()) {
            var builder = oreType.getOreTypeBuilder();
        }
    }

    //region BlockTags
    @Override
    public void generateBlockTags(MAgBlockTagProvider provider, HolderLookup.Provider holderLookupProvider) {
        var pickaxeTag = provider.tag(BlockTags.MINEABLE_WITH_PICKAXE);
        var ironToolTag = provider.tag(BlockTags.NEEDS_IRON_TOOL);

        ArrayList<Block> blocks = new ArrayList<>();
        for (OreType oreType : MAgOreTypes.R.getEntries().values()) {
            var builder = oreType.getOreTypeBuilder();

            if (builder.ores().get()) {
                blocks.addAll(oreType.getOre().getAll());
                blocks.addAll(oreType.getDeepslateOre().getAll());
            }
            if (builder.blocks().get()) {
                blocks.addAll(oreType.getBlock().getAll());
                blocks.addAll(oreType.getCompressedBlock().getAll());
                blocks.addAll(oreType.getDoubleCompressedBlock().getAll());
                blocks.addAll(oreType.getTripleCompressedBlock().getAll());
            }
        }

        pickaxeTag.add(blocks.toArray(Block[]::new));
        ironToolTag.add(blocks.toArray(Block[]::new));

    }

    //endregion
    //region LootTables
    @Override
    public void generateBlockLootTables(MAgBlockLootTableProvider provider) {
        for (OreType oreType : MAgOreTypes.R.getEntries().values()) {
            var builder = oreType.getOreTypeBuilder();

            if (builder.ores().get()) {
                this.dropSelf(oreType.getOre(), provider);
                this.dropSelf(oreType.getDeepslateOre(), provider);
            }
            if (builder.blocks().get()) {
                this.dropSelf(oreType.getBlock(), provider);
                this.dropSelf(oreType.getCompressedBlock(), provider);
                this.dropSelf(oreType.getDoubleCompressedBlock(), provider);
                this.dropSelf(oreType.getTripleCompressedBlock(), provider);
            }
        }
    }

    private void dropSelf(MultiversalObject<Block> block, MAgBlockLootTableProvider provider) {
        provider.dropSelf(block.get());
        provider.dropSelf(block.getMultiversal());
    }

    @Override
    public Collection<Block> getKnownBlockLootTables() {
        ArrayList<Block> blocks = new ArrayList<>();
        for (OreType oreType : MAgOreTypes.R.getEntries().values()) {
            var builder = oreType.getOreTypeBuilder();

            if (builder.ores().get()) {
                blocks.addAll(oreType.getOre().getAll());
                blocks.addAll(oreType.getDeepslateOre().getAll());
            }
            if (builder.blocks().get()) {
                blocks.addAll(oreType.getBlock().getAll());
                blocks.addAll(oreType.getCompressedBlock().getAll());
                blocks.addAll(oreType.getDoubleCompressedBlock().getAll());
                blocks.addAll(oreType.getTripleCompressedBlock().getAll());
            }
        }
        return blocks;
    }

    //endregion
    //region BlockStates
    @Override
    public void generateBlockState(MAgBlockStateProvider provider) {
        provider.models().withExistingParent("block/" + C.ORETYPE_DIR + "ore", C.modLoc("block/duo_layered_block"))
                .texture("layer0", new ResourceLocation("block/stone"))
                .texture("layer1", C.modLoc("block/" + C.ORETYPE_DIR + "ore"));

        provider.models().withExistingParent("block/" + C.ORETYPE_DIR + "deepslate_ore", C.modLoc("block/duo_layered_block"))
                .texture("layer0", new ResourceLocation("block/deepslate_top"))
                .texture("layer1", C.modLoc("block/" + C.ORETYPE_DIR + "ore"));

        provider.models().withExistingParent("block/" + C.ORETYPE_DIR + "block", C.modLoc("block/tint_block"))
                .texture("all", C.modLoc("block/" + C.ORETYPE_DIR + "block"));

        provider.models().withExistingParent("block/" + C.ORETYPE_DIR + "compressed_block", C.modLoc("block/duo_layered_block"))
                .texture("layer0", new ResourceLocation("block/bedrock"))
                .texture("layer1", C.modLoc("block/" + C.ORETYPE_DIR + "compressed_block"));

        provider.models().withExistingParent("block/" + C.ORETYPE_DIR + "duo_compressed_block", C.modLoc("block/duo_layered_block"))
                .texture("layer0", new ResourceLocation("block/bedrock"))
                .texture("layer1", C.modLoc("block/" + C.ORETYPE_DIR + "duo_compressed_block"));

        provider.models().withExistingParent("block/" + C.ORETYPE_DIR + "tri_compressed_block", C.modLoc("block/duo_layered_block"))
                .texture("layer0", new ResourceLocation("block/bedrock"))
                .texture("layer1", C.modLoc("block/" + C.ORETYPE_DIR + "tri_compressed_block"));

        for (OreType oreType : MAgOreTypes.R.getEntries().values()) {
            var builder = oreType.getOreTypeBuilder();

            if (builder.ores().get()) {
                this.block(oreType.getOre(), "ore", provider);
                this.block(oreType.getDeepslateOre(), "deepslate_ore", provider);
            }
            if (builder.blocks().get()) {
                this.block(oreType.getBlock(), "block", provider);
                this.block(oreType.getCompressedBlock(), "compressed_block", provider);
                this.block(oreType.getDoubleCompressedBlock(), "duo_compressed_block", provider);
                this.block(oreType.getTripleCompressedBlock(), "tri_compressed_block", provider);
            }
        }
    }

    private void block(MultiversalObject<Block> blockObject, String parent, MAgBlockStateProvider provider) {
        {   //Normal
            ModelFile model = provider.models().withExistingParent("block/" + Objects.requireNonNull(blockObject.getObject().getId()).getPath(), C.modLoc("block/" + C.ORETYPE_DIR + parent));

            provider.getVariantBuilder(blockObject.get()).forAllStates(state -> ConfiguredModel.builder()
                    .modelFile(model).build());

            provider.itemModels().withExistingParent(blockObject.getObject().getId().getPath(), C.modLoc("block/" + blockObject.getObject().getId().getPath()));
        }
        {   //Multiversal
            ModelFile model = provider.models().withExistingParent("block/" + Objects.requireNonNull(blockObject.getMultiversalObject().getId()).getPath(), C.modLoc("block/" + C.ORETYPE_DIR + parent));

            provider.getVariantBuilder(blockObject.getMultiversal()).forAllStates(state -> ConfiguredModel.builder()
                    .modelFile(model).build());

            provider.itemModels().withExistingParent(blockObject.getMultiversalObject().getId().getPath(), C.modLoc("block/" + blockObject.getMultiversalObject().getId().getPath()));
        }
    }

    //endregion
    //region ItemModels
    @Override
    public void generateItemModels(MAgItemModelProvider provider) {
        this.createTemplateItemModel("raw", provider);

        provider.withExistingParent("item/" + C.ORETYPE_DIR + "iced_raw", new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(C.MOD_ID, "item/" + C.ORETYPE_DIR + "raw"))
                .texture("layer1", new ResourceLocation(C.MOD_ID, "item/" + C.ORETYPE_DIR + "iced_raw"));

        this.createTemplateItemModel("ingot", provider);
        this.createTemplateItemModel("chunk", provider);

        this.createTemplateItemModel("crystal", provider);

        this.createTemplateItemModel("pure_dust", provider);
        this.createTemplateItemModel("dust", provider);
        this.createTemplateItemModel("dirty_dust", provider);
        provider.withExistingParent("item/" + C.ORETYPE_DIR + "wet_particle", new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(C.MOD_ID, "item/" + C.ORETYPE_DIR + "dirty_dust"))
                .texture("layer1", new ResourceLocation(C.MOD_ID, "item/" + C.ORETYPE_DIR + "wet_particle"));

        for (OreType oreType : MAgOreTypes.R.getEntries().values()) {
            var builder = oreType.getOreTypeBuilder();

            if (builder.raws().get()) {
                this.item(oreType.getRaw(), "raw", provider);
                if (builder.processingItems().get()) {
                    this.item(oreType.getIcedRaw(), "iced_raw", provider);
                }
            }
            if (builder.ingots().get()) {
                this.item(oreType.getIngot(), "ingot", provider);
                this.item(oreType.getChunk(), "chunk", provider);
            }
            if (builder.crystals().get()) {
                this.item(oreType.getCrystal(), "crystal", provider);
            }
            if (builder.processingItems().get() || builder.dusts().get()) {
                this.item(oreType.getPureDust(), "pure_dust", provider);
            }
            if (builder.processingItems().get()) {
                this.item(oreType.getDust(), "dust", provider);
                this.item(oreType.getDirtyDust(), "dirty_dust", provider);
                this.item(oreType.getWetParticle(), "wet_particle", provider);
            }
        }
    }

    private void createTemplateItemModel(String id, MAgItemModelProvider provider) {
        provider.withExistingParent("item/" + C.ORETYPE_DIR + id, new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(C.MOD_ID, "item/" + C.ORETYPE_DIR + id));
    }

    private void item(MultiversalObject<Item> itemObject, String parent, MAgItemModelProvider provider) {
        provider.withExistingParent(Objects.requireNonNull(itemObject.getObject().getId()).getPath(),
                new ResourceLocation(C.MOD_ID, "item/" + C.ORETYPE_DIR + parent));

        provider.withExistingParent(Objects.requireNonNull(itemObject.getMultiversalObject().getId()).getPath(),
                new ResourceLocation(C.MOD_ID, "item/" + C.ORETYPE_DIR + parent));
    }
    //endregion
}
