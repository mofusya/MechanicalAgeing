package net.mofusya.mechanical_ageing.data.loot_tables;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.blocks.MAgBlocks;
import net.mofusya.mechanical_ageing.crystalset.CrystalSet;
import net.mofusya.mechanical_ageing.crystalset.MAgCrystalSets;
import net.mofusya.mechanical_ageing.metalset.MetalSet;
import net.mofusya.mechanical_ageing.metalset.MAgMetalSets;
import net.mofusya.mechanical_ageing.tiles.MAgMachines;

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
        registries.addAll(MAgMachines.MACHINES.getBlockEntries());

        for (RegistryObject<Block> block : registries) {
            this.dropSelf(block.get());
        }

        for (MetalSet metalSet : MAgMetalSets.METAL_SET.getEntries()) {
            this.dropSelf(metalSet.compressedBlock());
            this.dropSelf(metalSet.block());
            this.add(metalSet.ore(), block -> createOreDrop(metalSet.ore(), metalSet.raw()));
            this.add(metalSet.deepslateOre(), block -> createOreDrop(metalSet.deepslateOre(), metalSet.raw()));
        }

        for (CrystalSet crystalSet : MAgCrystalSets.CRYSTALS.getEntries()) {
            this.dropSelf(crystalSet.block());
            this.dropSelf(crystalSet.compressedBlock());
            this.crystalDrop(crystalSet.ore(), crystalSet.crystal());
            this.crystalDrop(crystalSet.deepslateOre(), crystalSet.crystal());
        }
    }

    private void crystalDrop(Block ore, Item crystal){
        this.add(ore, block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(crystal).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.addAll(MAgMachines.MACHINES.getBlockEntries().stream().map(RegistryObject::get).toList());
        blocks.addAll(MAgMetalSets.METAL_SET.getBlocks().stream().map(RegistryObject::get).toList());
        blocks.addAll(MAgCrystalSets.CRYSTALS.getBlocks().stream().map(RegistryObject::get).toList());
        blocks.addAll(MAgBlocks.BLOCKS.getBlocks().stream().map(RegistryObject::get).toList());
        return blocks;
    }
}