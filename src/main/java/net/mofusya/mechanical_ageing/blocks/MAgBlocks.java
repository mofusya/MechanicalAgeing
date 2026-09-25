package net.mofusya.mechanical_ageing.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.blocks.block.DriveShaftBlock;
import net.mofusya.mechanical_ageing.blocks.block.NarrowBlock;
import net.mofusya.ornatelib.item.AttributedItem;
import net.mofusya.ornatelib.registries.OrnateBlockDeferredRegister;
import net.mofusya.ornatelib.registries.OrnateBlockRegister;

public class MAgBlocks {
    public static final OrnateBlockRegister BLOCKS = new OrnateBlockRegister(MAg.MOD_ID, 2);

    public static final RegistryObject<Block> REINFORCED_BRICKS = BLOCKS.register("reinforced_bricks", new OrnateBlockRegister.Builder()
            .attribute(new AttributedItem.Builder().attribute("melting_point", 2800, true))
            .blockBuild(BlockBehaviour.Properties.copy(Blocks.BRICKS))
    );

    public static final RegistryObject<Block> DRIVE_SHAFT = BLOCKS.register("drive_shaft", new OrnateBlockRegister.Builder()
            .attribute(new AttributedItem.Builder()
                    .attribute("melting_point", 1756, true)
                    .attribute("durability_rp", 1048576, true)
            )
            .blockBuild(BlockBehaviour.Properties.copy(Blocks.IRON_BARS).noOcclusion())
            .blockFunc(DriveShaftBlock::new)
    );

    public static final RegistryObject<Block> ROTOR_BLADE = BLOCKS.register("rotor_blade", new OrnateBlockRegister.Builder()
            .attribute(new AttributedItem.Builder()
                    .attribute("melting_point", 1756, true)
                    .attribute("durability_rp", 1048576, true)
            )
            .blockBuild(BlockBehaviour.Properties.copy(Blocks.IRON_BARS).noOcclusion())
            .blockFunc(DriveShaftBlock::new)
    );

    public static final RegistryObject<Block> COIL_BLOCK = BLOCKS.register("coil", new OrnateBlockRegister.Builder()
            .attribute(new AttributedItem.Builder().attribute("durability_rp", 1048576, true))
            .blockBuild(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion())
            .blockFunc(build -> new NarrowBlock(build, 1))
    );
}
