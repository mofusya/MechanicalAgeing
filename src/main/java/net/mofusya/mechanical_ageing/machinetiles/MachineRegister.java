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
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlock;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineMenu;
import net.mofusya.mechanical_ageing.util.LazyPointer;
import net.mofusya.ornatelib.registries.OrnateBlockDeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineRegister {
    private static final Map<ResourceLocation, LazyPointer<RegistryObject<Block>>> blockMap = new HashMap<>();
    private static final Map<ResourceLocation, LazyPointer<RegistryObject<BlockEntityType<MachineBlockEntity>>>> blockEntityMap = new HashMap<>();
    private static final Map<ResourceLocation, LazyPointer<RegistryObject<MenuType<MachineMenu>>>> menuMap = new HashMap<>();

    protected final String modId;
    protected final OrnateBlockDeferredRegister blocks;
    protected final DeferredRegister<BlockEntityType<?>> blockEntities;
    protected final DeferredRegister<MenuType<?>> menus;

    private final ArrayList<RegistryObject<MenuType<MachineMenu>>> machineMenus = new ArrayList<>();

    public MachineRegister(String modId) {
        this.modId = modId;
        blocks = OrnateBlockDeferredRegister.create(modId);
        blockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modId);
        menus = DeferredRegister.create(ForgeRegistries.MENU_TYPES, modId);
    }

    public MachineObject register(final String id, final MachineTileSupplier sup) {
        // 疑似的なポインターを作成。これにより、nullを回避し、遅延初期化を実現する。
        final LazyPointer<RegistryObject<Block>> lazyBlock = new LazyPointer<>();
        final LazyPointer<RegistryObject<BlockEntityType<MachineBlockEntity>>> lazyBlockEntity = new LazyPointer<>();
        final LazyPointer<RegistryObject<MenuType<MachineMenu>>> lazyMenu = new LazyPointer<>();

        // 実際のパスを作成。これはDeferredRegisterの内部で使用されるキーと一致する
        final ResourceLocation resourceLocation = new ResourceLocation(modId, id);

        // マップに登録
        blockMap.put(resourceLocation, lazyBlock);
        blockEntityMap.put(resourceLocation, lazyBlockEntity);
        menuMap.put(resourceLocation, lazyMenu);

        final MachineTile propertyTile = sup.create(resourceLocation);

        final RegistryObject<Block> block = blocks.register(
                id,
                () -> new MachineBlock(() -> lazyBlockEntity.get().get(), propertyTile),
                propertyTile.getItemBuild()
        );

        final RegistryObject<BlockEntityType<MachineBlockEntity>> blockEntity = blockEntities.register(
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

        final RegistryObject<MenuType<MachineMenu>> menu = menus.register(
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
        lazyMenu.set(menu);

        this.machineMenus.add(menu);

        return new MachineObject(propertyTile, block, blockEntity, menu);
    }

    public List<RegistryObject<Block>> getBlockEntries(){
        return this.blocks.getBlocks();
    }

    public List<RegistryObject<BlockEntityType<?>>> getBlockEntityEntries(){
        return new ArrayList<>(this.blockEntities.getEntries());
    }

    public List<RegistryObject<MenuType<MachineMenu>>> getMenuEntries(){
        return new ArrayList<>(this.machineMenus);
    }

    public void register(IEventBus eventBus) {
        blocks.register(eventBus);
        blockEntities.register(eventBus);
        menus.register(eventBus);
    }

    public static LazyPointer<RegistryObject<Block>> getBlock(ResourceLocation id) {
        return blockMap.get(id);
    }

    public static LazyPointer<RegistryObject<BlockEntityType<MachineBlockEntity>>> getBlockEntity(ResourceLocation id) {
        return blockEntityMap.get(id);
    }

    public static LazyPointer<RegistryObject<MenuType<MachineMenu>>> getMenu(ResourceLocation id) {
        return menuMap.get(id);
    }

    @FunctionalInterface
    public interface MachineTileSupplier {
        MachineTile create(ResourceLocation location);
    }
}
