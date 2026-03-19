package net.mofusya.mechanical_ageing.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.metalset.MetalSet;
import net.mofusya.mechanical_ageing.metalset.ModMetalSet;

import java.util.ArrayList;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MechanicalAgeing.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ArrayList<RegistryObject<Block>> registries = new ArrayList<>();

        for (RegistryObject<Block> block : registries) {
            this.blockWithItem(block);
        }

        for (MetalSet metalSet : ModMetalSet.METAL_SET.getEntries()) {
            this.metalSetBlock(metalSet);
        }
    }

    private void blockWithItem(RegistryObject<Block> block) {
        this.simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private void metalSetBlock(MetalSet metalSet) {
        {
            //block
            {
                Block block = metalSet.block();
                String name = ForgeRegistries.BLOCKS.getKey(block).getPath();

                ModelFile model = models()
                        .withExistingParent(name, mcLoc("block/cube_all"))
                        .texture("all", modLoc("block/block"));

                this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());

                this.itemModels().withExistingParent(name, modLoc("block/" + name));
            }

            //compressedBlock
            {
                Block block = metalSet.compressedBlock();
                String name = ForgeRegistries.BLOCKS.getKey(block).getPath();

                ModelFile model = models()
                        .withExistingParent(name, mcLoc("block/cube_all"))
                        .texture("all", modLoc("block/compressed_block"));

                this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());

                this.itemModels().withExistingParent(name, modLoc("block/" + name));
            }

            //ore
            {
                Block block = metalSet.ore();
                String name = ForgeRegistries.BLOCKS.getKey(block).getPath();

                ResourceLocation stone = blockTexture(Blocks.STONE);

                ModelFile model = models()
                        .withExistingParent(name, modLoc("block/duo_layered_block"))
                        .texture("layer0", stone)
                        .texture("layer1", modLoc("block/ore"));

                this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());

                this.itemModels().withExistingParent(name, modLoc("block/" + name));
            }

            //deepslate
            {
                Block block = metalSet.deepslateOre();
                String name = ForgeRegistries.BLOCKS.getKey(block).getPath();

                ResourceLocation deepslate = blockTexture(Blocks.DEEPSLATE);

                ModelFile model = models()
                        .withExistingParent(name, modLoc("block/duo_layered_block"))
                        .texture("layer0", deepslate)
                        .texture("layer1", modLoc("block/ore"));

                this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());

                this.itemModels().withExistingParent(name, modLoc("block/" + name));
            }
        }
    }
}
