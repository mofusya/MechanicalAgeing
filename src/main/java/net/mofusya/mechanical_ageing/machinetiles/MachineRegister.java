package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.util.LazyPointer;
import net.mofusya.ornatelib.registries.OrnateBlockDeferredRegister;

import java.util.HashMap;
import java.util.Map;

public class MachineRegister {
    private static final Map<ResourceLocation,RegistryObject<Block>> blockMap = new HashMap<>();
    private static final Map<ResourceLocation,RegistryObject<BlockEntityType<MachineBlockEntity>>> blockEntityMap = new HashMap<>();
    private static final Map<ResourceLocation,RegistryObject<MenuType<MachineMenu>>> menuMap = new HashMap<>();

    protected final String modid;
    protected final OrnateBlockDeferredRegister BLOCKS;
    protected final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES;
    protected final DeferredRegister<MenuType<?>> MENUS;

    public MachineRegister(String modid) {
        this.modid = modid;
        BLOCKS = OrnateBlockDeferredRegister.create(modid);
        BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,modid);
        MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, modid);
    }

    public ResourceLocation register(final String id,final MachineTileSupplier sup) {
        // 疑似的なポインターを作成。これにより、nullを回避し、遅延初期化を実現する。
        final LazyPointer<RegistryObject<Block>> lazyBlock = new LazyPointer<>();
        final LazyPointer<RegistryObject<BlockEntityType<MachineBlockEntity>>> lazyBlockEntity = new LazyPointer<>();

        // 実際のパスを作成。これはDeferredRegisterの内部で使用されるキーと一致する
        final ResourceLocation resourceLocation = new ResourceLocation(modid, id);

        final MachineTile propertyTile = sup.create(resourceLocation);

        final RegistryObject<Block> block = BLOCKS.register(
                id,
                () -> new MachineBlock(() -> lazyBlockEntity.get().get(), propertyTile),
                propertyTile.getItemBuild()
        );

        final RegistryObject<BlockEntityType<MachineBlockEntity>> blockEntity = BLOCK_ENTITIES.register(
                id,
                () -> BlockEntityType.Builder.of(
                        (pos, state) -> new MachineBlockEntity(
                                lazyBlockEntity.get().get(),
                                pos,
                                state,
                                propertyTile
                        ),
                        lazyBlock.get().get()
                ).build(null)
        );

        final RegistryObject<MenuType<MachineMenu>> menu = MENUS.register(
                id,
                () -> IForgeMenuType.create(
                        (containerId, inventory, extraData) -> new MachineMenu(
                                containerId,
                                inventory,
                                extraData,
                                propertyTile,
                                lazyBlock.get().get()
                        )
                )
        );

        // ポインターに実際のRegistryObjectをセット
        lazyBlock.set(block);
        lazyBlockEntity.set(blockEntity);

        // マップに登録
        blockMap.put(resourceLocation, block);
        blockEntityMap.put(resourceLocation, blockEntity);
        menuMap.put(resourceLocation, menu);

        // 登録キーを返す
        return resourceLocation;
    }

    public void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        MENUS.register(eventBus);
    }

    public static RegistryObject<Block> getBlock(ResourceLocation id) {
        return blockMap.get(id);
    }

    public static RegistryObject<BlockEntityType<MachineBlockEntity>> getBlockEntity(ResourceLocation id) {
        return blockEntityMap.get(id);
    }

    public static RegistryObject<MenuType<MachineMenu>> getMenu(ResourceLocation id) {
        return menuMap.get(id);
    }

    @FunctionalInterface
    public interface MachineTileSupplier {
        MachineTile create(ResourceLocation location);
    }
}
