package net.mofusya.mechanical_ageing.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.ornatelib.item.AttributedItem;
import net.mofusya.ornatelib.registries.OrnateBlockDeferredRegister;

public class ModBlocks {
    public static final OrnateBlockDeferredRegister BLOCKS = OrnateBlockDeferredRegister.create(MechanicalAgeing.MOD_ID, 2);

    public static final RegistryObject<Block> REINFORCED_BRICKS = BLOCKS.register("reinforced_bricks", new OrnateBlockDeferredRegister.Builder()
            .attribute(new AttributedItem.Builder().attribute("melting_point", 691, true))
            .blockBuild(BlockBehaviour.Properties.copy(Blocks.BRICKS))
    );
}
