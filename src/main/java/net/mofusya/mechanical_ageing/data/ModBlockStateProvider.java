package net.mofusya.mechanical_ageing.data;

import net.minecraft.core.Direction;
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
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.blocks.MAgBlocks;
import net.mofusya.mechanical_ageing.data.blockstate.MachineBlockStateBuilder;
import net.mofusya.mechanical_ageing.data.blockstate.MachineBlockStateHelper;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlock;
import net.mofusya.mechanical_ageing.metalset.MAgMetalSets;
import net.mofusya.mechanical_ageing.metalset.MetalSet;

import java.util.ArrayList;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MAg.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ArrayList<RegistryObject<Block>> registries = new ArrayList<>();

        registries.addAll(MAgBlocks.BLOCKS.getBlocks());

        for (RegistryObject<Block> block : registries) {
            this.blockWithItem(block);
        }

        for (MetalSet metalSet : MAgMetalSets.METAL_SET.getEntries()) {
            this.metalSetBlock(metalSet);
        }

        for (MachineBlockStateBuilder builder : MachineBlockStateHelper.BUILDER_LIST) {
            this.machineBlock(builder);
        }
    }

    private void machineBlock(MachineBlockStateBuilder builder) {
        var model = models().withExistingParent(builder.getName(), modLoc("block/multi_layered_machine_block"));

        if (builder.getFrontCoverTexture() != null) model.texture("cover_front", builder.getFrontCoverTexture());
        if (builder.getSideCoverTexture() != null) model.texture("cover_side", builder.getSideCoverTexture());
        if (builder.getTopCoverTexture() != null) model.texture("cover_top", builder.getTopCoverTexture());
        if (builder.getBottomCoverTexture() != null) model.texture("cover_bottom", builder.getBottomCoverTexture());

        if (builder.getSideColor() != -404) model.texture("side_side", modLoc("block/machine/side"));

        if (builder.getUpperCrystalColor() != -404) {
            model.texture("main_crystal_side", modLoc("block/machine/upper_crystal"));
            model.texture("main_crystal_vert", modLoc("block/machine/crystal"));
        }
        ;
        if (builder.getLowerCrystalColor() != -404) {
            model.texture("sub_crystal_side", modLoc("block/machine/lower_crystal"));
            model.texture("sub_crystal_vert", modLoc("block/machine/crystal"));
        }

        if (builder.getFrameColor() != -404) {
            model.texture("frame_side", modLoc("block/machine/frame_side"));
            model.texture("frame_top", modLoc("block/machine/frame_top"));
            model.texture("frame_bottom", modLoc("block/machine/frame_bottom"));
        }

        if (builder.getFrontBaseTexture() != null) model.texture("base_front", builder.getFrontBaseTexture());
        if (builder.getSideBaseTexture() != null) model.texture("base_side", builder.getSideBaseTexture());
        if (builder.getTopBaseTexture() != null) model.texture("base_top", builder.getTopBaseTexture());
        if (builder.getBottomBaseTexture() != null) model.texture("base_bottom", builder.getBottomBaseTexture());

        if (builder.getBackground() != null) model.texture("background", builder.getBackground());

        ResourceLocation particleTexture;
        if (builder.getSideCoverTexture() != null) {
            particleTexture = builder.getSideCoverTexture();
        } else if (builder.getSideBaseTexture() != null) {
            particleTexture = builder.getSideBaseTexture();
        } else {
            particleTexture = modLoc("block/machine/frame_side");
        }
        model.texture("particle", particleTexture);

        this.getVariantBuilder(builder.getBlock()).forAllStates(state -> {
            var configuredModel = ConfiguredModel.builder().modelFile(model);

            if (builder.hasFacing()) {
                Direction facing = state.getValue(MachineBlock.FACING);

                int rotation = switch (facing) {
                    case SOUTH -> 180;
                    case WEST -> 270;
                    case EAST -> 90;
                    default -> 0;
                };

                configuredModel.rotationY(rotation);
            }

            return configuredModel.build();
        });

        this.itemModels().withExistingParent(builder.getName(), modLoc("block/" + builder.getName()));
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
                        .withExistingParent(name, modLoc("block/tint_block"))
                        .texture("all", modLoc("block/block"));

                this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());

                this.itemModels().withExistingParent(name, modLoc("block/" + name));
            }

            //compressedBlock
            {
                Block block = metalSet.compressedBlock();
                String name = ForgeRegistries.BLOCKS.getKey(block).getPath();

                ModelFile model = models()
                        .withExistingParent(name, modLoc("block/tint_block"))
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
