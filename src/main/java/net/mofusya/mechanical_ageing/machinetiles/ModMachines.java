package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.MechanicalAgeing;
import net.mofusya.mechanical_ageing.blocks.ModBlocks;
import net.mofusya.mechanical_ageing.machinetiles.tile.DestructorTile;
import net.mofusya.ornatelib.registries.OrnateBlockDeferredRegister;

import java.util.function.Supplier;

public class ModMachines {
    public static final OrnateBlockDeferredRegister BLOCKS = OrnateBlockDeferredRegister.create(MechanicalAgeing.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MechanicalAgeing.MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MechanicalAgeing.MOD_ID);

    public static final RegistryObject<Block> DESTROYER = registerBlock("destroyer", DestructorTile::new);
    public static final RegistryObject<BlockEntityType<MachineBlockEntity>> DESTROYER_BE = registerBE("destroyer", DestructorTile::new);
    public static final RegistryObject<MenuType<MachineMenu>> DESTROYER_MENU = registerMenu("destroyer", DestructorTile::new);

    public static RegistryObject<Block> registerBlock(String id, Supplier<MachineTile> sup) {
        MachineTile machineTile = sup.get();
        return BLOCKS.register(id, () -> new MachineBlock(() -> machineTile.getBlockEntity().get(), machineTile), machineTile.getItemBuild());
    }

    public static RegistryObject<BlockEntityType<MachineBlockEntity>> registerBE(String id, Supplier<MachineTile> sup) {
        MachineTile machineTile = sup.get();
        return BLOCK_ENTITIES.register(id, () -> BlockEntityType.Builder.of((pos, state) -> new MachineBlockEntity(machineTile.getBlockEntity().get(), pos, state, machineTile), machineTile.getBlock().get()).build(null));
    }

    public static RegistryObject<MenuType<MachineMenu>> registerMenu(String id, Supplier<MachineTile> sup) {
        MachineTile machineTile = sup.get();
        return MENUS.register(id, () -> IForgeMenuType.create((containerId, inventory, extraData) -> new MachineMenu(containerId, inventory, extraData, machineTile, machineTile.getBlock().get())));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        MENUS.register(eventBus);
    }
}