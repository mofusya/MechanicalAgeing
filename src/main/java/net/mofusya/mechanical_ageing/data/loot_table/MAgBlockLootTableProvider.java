package net.mofusya.mechanical_ageing.data.loot_table;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.data.IMAgDataProvider;
import net.mofusya.mechanical_ageing.data.MAgDataGenerator;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;

public class MAgBlockLootTableProvider extends BlockLootSubProvider {
    public MAgBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        ArrayList<RegistryObject<Block>> registries = new ArrayList<>();


        for (RegistryObject<Block> block : registries) {
            this.dropSelf(block.get());
        }

        for (IMAgDataProvider provider : MAgDataGenerator.getProviders()) {
            provider.generateBlockLootTables(this);
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();

        for (IMAgDataProvider provider : MAgDataGenerator.getProviders()) {
            blocks.addAll(provider.getKnownBlockLootTables());
        }

        return blocks;
    }

    @Override
    public void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
    }

    @Override
    public void add(Block block, Function<Block, LootTable.Builder> builderFunc) {
        super.add(block, builderFunc);
    }

    @Override
    public void dropSelf(Block block) {
        super.dropSelf(block);
    }

    @Override
    public void dropWhenSilkTouch(Block block) {
        super.dropWhenSilkTouch(block);
    }

    @Override
    public void dropOther(Block block, ItemLike itemLike) {
        super.dropOther(block, itemLike);
    }

    @Override
    public void otherWhenSilkTouch(Block block, Block otherBlock) {
        super.otherWhenSilkTouch(block, otherBlock);
    }
}