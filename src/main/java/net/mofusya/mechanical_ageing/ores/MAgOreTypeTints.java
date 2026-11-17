package net.mofusya.mechanical_ageing.ores;

import net.kairayk.yklibrary.util.VectorColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mofusya.mechanical_ageing.util.MultiversalObject;
import net.mofusya.mechanical_ageing.tint.BlockColorRegister;
import net.mofusya.mechanical_ageing.tint.IMAgTintProvider;
import net.mofusya.mechanical_ageing.tint.ItemColorRegister;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@OnlyIn(Dist.CLIENT)
public enum MAgOreTypeTints implements IMAgTintProvider {
    INSTANCE;

    @Override
    public void registerItemColorTint(ItemColorRegister R, ItemColors itemColors) {
        for (OreType oreType : MAgOreTypes.R.getEntries().values()) {
            var builder = oreType.getOreTypeBuilder();

            if (builder.ores().get()) {
                this.registerBlockItemTint(R, oreType.getColor(), builder.raws().getColour(), oreType.getOre(), 0);
                this.registerBlockItemTint(R, oreType.getColor(), builder.raws().getColour(), oreType.getDeepslateOre(), 0);
            }
            if (builder.blocks().get()) {
                this.registerBlockItemTint(R, oreType.getColor(), builder.blocks().getColour(), oreType.getBlock(), 0);
                this.registerBlockItemTint(R, oreType.getColor(), builder.blocks().getColour(), oreType.getCompressedBlock(), 0);
                this.registerBlockItemTint(R, oreType.getColor(), builder.blocks().getColour(), oreType.getDoubleCompressedBlock(), 0);
                this.registerBlockItemTint(R, oreType.getColor(), builder.blocks().getColour(), oreType.getTripleCompressedBlock(), 0);
            }

            if (builder.raws().get()) {
                this.registerItemTint(R, oreType.getColor(), builder.raws().getColour(), oreType.getRaw(), 0);

                if (builder.processingItems().get()) {
                    this.registerItemTint(R, oreType.getColor(), builder.raws().getColour(), oreType.getIcedRaw(), 0);
                }
            }
            if (builder.ingots().get()) {
                this.registerItemTint(R, oreType.getColor(), builder.ingots().getColour(), oreType.getIngot(), 0);
                this.registerItemTint(R, oreType.getColor(), builder.ingots().getColour(), oreType.getChunk(), 0);
            }
            if (builder.crystals().get()) {
                this.registerItemTint(R, oreType.getColor(), builder.crystals().getColour(), oreType.getCrystal(), 0);
            }
            if (builder.processingItems().get() || builder.dusts().get()) {
                this.registerItemTint(R, builder.processingItems().getColour() == null ? oreType.getColor() : builder.processingItems().getColour(), builder.dusts().getColour(), oreType.getPureDust(), 0);
            }
            if (builder.processingItems().get()) {
                this.registerItemTint(R, oreType.getColor(), builder.processingItems().getColour(), oreType.getDust(), 0);
                this.registerItemTint(R, oreType.getColor(), builder.processingItems().getColour(), oreType.getDirtyDust(), 0);
                this.registerItemTint(R, oreType.getColor(), builder.processingItems().getColour(), oreType.getWetParticle(), 0);
            }
        }
    }

    private void registerBlockItemTint(ItemColorRegister R, VectorColor defaultColour, @Nullable VectorColor overrideColour, MultiversalObject<Block> block, int... tintIndexes) {
        R.register((itemStack, tintIndex) -> {
            if (Arrays.stream(tintIndexes).boxed().toList().contains(tintIndex)) {
                return overrideColour == null ? defaultColour.toARGB() : overrideColour.toARGB();
            }
            return 0xFFFFFFFF;
        }, block.get(), block.getMultiversal());
    }

    private void registerItemTint(ItemColorRegister R, VectorColor defaultColour, @Nullable VectorColor overrideColour, MultiversalObject<Item> item, int... tintIndexes) {
        R.register((itemStack, tintIndex) -> {
            if (Arrays.stream(tintIndexes).boxed().toList().contains(tintIndex)) {
                return overrideColour == null ? defaultColour.toARGB() : overrideColour.toARGB();
            }
            return 0xFFFFFFFF;
        }, item.get(), item.getMultiversal());
    }

    @Override
    public void registerBlockColorTint(BlockColorRegister R, BlockColors itemColors) {
        for (OreType oreType : MAgOreTypes.R.getEntries().values()) {
            var builder = oreType.getOreTypeBuilder();

            if (builder.ores().get()) {
                this.registerBlockTint(R, oreType.getColor(), builder.raws().getColour(), oreType.getOre(), 0);
                this.registerBlockTint(R, oreType.getColor(), builder.raws().getColour(), oreType.getDeepslateOre(), 0);
            }
            if (builder.blocks().get()) {
                this.registerBlockTint(R, oreType.getColor(), builder.blocks().getColour(), oreType.getBlock(), 0);
                this.registerBlockTint(R, oreType.getColor(), builder.blocks().getColour(), oreType.getCompressedBlock(), 0);
                this.registerBlockTint(R, oreType.getColor(), builder.blocks().getColour(), oreType.getDoubleCompressedBlock(), 0);
                this.registerBlockTint(R, oreType.getColor(), builder.blocks().getColour(), oreType.getTripleCompressedBlock(), 0);
            }
        }
    }

    private void registerBlockTint(BlockColorRegister R, VectorColor defaultColour, @Nullable VectorColor overrideColour, MultiversalObject<Block> item, int... tintIndexes) {
        R.register((state, getter, pos, tintIndex) -> {
            if (Arrays.stream(tintIndexes).boxed().toList().contains(tintIndex)) {
                return overrideColour == null ? defaultColour.toARGB() : overrideColour.toARGB();
            }
            return 0xFFFFFFFF;
        }, item.get(), item.getMultiversal());
    }
}
