package net.mofusya.mechanical_ageing.machinetiles;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineMenu;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineScreen;
import net.mofusya.mechanical_ageing.util.LazyPointer;
import net.mofusya.ornatelib.registries.OrnateBlockEntityTypeRegister;
import net.mofusya.ornatelib.registries.OrnateBlockRegister;
import net.mofusya.ornatelib.registries.OrnateHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A class to register new {@code MachineTile}'s.
 */
public final class MachineTileRegister {
    /**
     * The fields for storing all {@code MachineTile}'s, {@code Block}'s, {@code BlockEntityType<MachineBlockEntity>}'s, and {@code MenuType<MachineMenu>}'s registered by this register.
     */
    private static final Map<ResourceLocation, OrnateHolder<MachineTile>> MACHINE_TILES = new HashMap<>();
    private static final Map<ResourceLocation, LazyPointer<RegistryObject<Block>>> MACHINE_BLOCKS = new HashMap<>();
    private static final Map<ResourceLocation, LazyPointer<RegistryObject<BlockEntityType<MachineBlockEntity>>>> MACHINE_BLOCK_ENTITY_TYPES = new HashMap<>();
    private static final Map<ResourceLocation, LazyPointer<RegistryObject<MenuType<MachineMenu>>>> MACHINE_MENUS_TYPES = new HashMap<>();

    /**
     * A field for storing the mod id to use.
     */
    private final String modId;
    /**
     * The block register.
     */
    private final OrnateBlockRegister blockR;
    /**
     * The blockEntityType register.
     */
    private final OrnateBlockEntityTypeRegister blockEntityTypeR;
    /**
     * The menuType register.
     */
    private final DeferredRegister<MenuType<?>> menuTypeR;

    /**
     * A simple flag to determine if this register is locked.
     */
    private boolean locked;

    /**
     * The constructor for this.
     *
     * @param modId The mod id for this register.
     */
    public MachineTileRegister(String modId) {
        this.modId = modId;
        this.blockR = new OrnateBlockRegister(modId);
        this.blockEntityTypeR = new OrnateBlockEntityTypeRegister(modId);
        this.menuTypeR = DeferredRegister.create(ForgeRegistries.MENU_TYPES, modId);
    }

    /**
     * The getters/helper getters for this {@code MACHINE_TILES}, {@code MACHINE_BLOCKS}, {@code MACHINE_BLOCK_ENTITY_TYPES}, {@code MACHINE_MENUS_TYPES}.
     */
    //region The getters/helper getters
    public static ImmutableMap<ResourceLocation, OrnateHolder<MachineTile>> getMachines() {
        return ImmutableMap.copyOf(MACHINE_TILES);
    }

    public static OrnateHolder<MachineTile> getMachine(ResourceLocation id) {
        return MACHINE_TILES.get(id);
    }

    public static ImmutableMap<ResourceLocation, Supplier<RegistryObject<Block>>> getMachineBlocks() {
        return ImmutableMap.copyOf(MACHINE_BLOCKS);
    }

    public static Supplier<RegistryObject<Block>> getMachineBlock(ResourceLocation id) {
        return MACHINE_BLOCKS.get(id);
    }

    public static ImmutableMap<ResourceLocation, Supplier<RegistryObject<BlockEntityType<MachineBlockEntity>>>> getMachineBlockEntityTypes() {
        return ImmutableMap.copyOf(MACHINE_BLOCK_ENTITY_TYPES);
    }

    public static Supplier<RegistryObject<BlockEntityType<MachineBlockEntity>>> getMachineBlockEntityType(ResourceLocation id) {
        return MACHINE_BLOCK_ENTITY_TYPES.get(id);
    }

    public static ImmutableMap<ResourceLocation, Supplier<RegistryObject<MenuType<MachineMenu>>>> getMachineMenuTypes() {
        return ImmutableMap.copyOf(MACHINE_MENUS_TYPES);
    }

    public static Supplier<RegistryObject<MenuType<MachineMenu>>> getMachineMenuType(ResourceLocation id) {
        return MACHINE_MENUS_TYPES.get(id);
    }
    //endregion

    /**
     * A method to register a new {@code MachineTile}.
     *
     * @param id              The id of the {@code MachineTile} to register.
     * @param machineTileFunc The {@code MachineTile} to register.
     * @param <T>             A {@code MachineTile}.
     * @return The registered {@code OrnateHolder}<{@code MachineTile}>.
     */
    public <T extends MachineTile> OrnateHolder<T> register(String id, Function<ResourceLocation, T> machineTileFunc) {
        if (this.isLocked()) throw new RuntimeException("The register is locked.");

        LazyPointer<RegistryObject<Block>> lazyBlock = new LazyPointer<>();
        LazyPointer<RegistryObject<BlockEntityType<MachineBlockEntity>>> lazyBlockEntityType = new LazyPointer<>();
        LazyPointer<RegistryObject<MenuType<MachineMenu>>> lazyMenuType = new LazyPointer<>();

        ResourceLocation fullId = new ResourceLocation(this.getModId(), id);
        OrnateHolder<T> machineTile = new OrnateHolder<>(fullId, machineTileFunc);

        MACHINE_BLOCKS.put(fullId, lazyBlock);
        MACHINE_BLOCK_ENTITY_TYPES.put(fullId, lazyBlockEntityType);
        MACHINE_MENUS_TYPES.put(fullId, lazyMenuType);

        RegistryObject<Block> block = this.getBlockR().register(id, new OrnateBlockRegister.Builder()
                .blockFunc(machineTile.get()::createBlock)
                .blockBuild(machineTile.get().getBlockProperties())
                .itemFunc(machineTile.get()::createItem)
                .itemBuild(machineTile.get().getItemProperties())
        );

        @SuppressWarnings("DataFlowIssue")
        RegistryObject<BlockEntityType<MachineBlockEntity>> blockEntityType = blockEntityTypeR.register(id,
                () -> BlockEntityType.Builder.of(
                        (pos, state) -> machineTile.get().createBlockEntity(lazyBlockEntityType.get().get(), pos, state),
                        lazyBlock.get().get()
                ).build(null)
        );

        RegistryObject<MenuType<MachineMenu>> menuType = menuTypeR.register(id,
                () -> IForgeMenuType.create((windowId, inventory, buf) ->
                        machineTile.get().createMenu(windowId, inventory, buf)
                )
        );

        lazyBlock.set(block);
        lazyBlockEntityType.set(blockEntityType);
        lazyMenuType.set(menuType);

        @SuppressWarnings("unchecked")
        var castMachineTile = (OrnateHolder<MachineTile>) machineTile;
        MACHINE_TILES.put(fullId, castMachineTile);
        return machineTile;
    }

    /**
     * A method to register all the {@code Block}s, {@code BlockEntityType<MachineBlockEntity>}s, and {@code MenuType<MachineMenu>}s to the game.
     *
     * @param eventBus The {@code IEventBus} of this mod.
     */
    public void register(IEventBus eventBus) {
        this.getBlockR().register(eventBus);
        this.getBlockEntityTypeR().register(eventBus);
        this.getMenuTypeR().register(eventBus);

        this.lock();
    }

    //region Getters

    /**
     * A getter for this {@code modId}.
     *
     * @return This {@code modId}.
     */
    public String getModId() {
        return modId;
    }

    /**
     * A getter for this {@code blockR}.
     *
     * @return This {@code blockR}.
     */
    public OrnateBlockRegister getBlockR() {
        return blockR;
    }

    /**
     * A getter for this {@code blockEntityTypeR}.
     *
     * @return This {@code blockEntityTypeR}.
     */
    public OrnateBlockEntityTypeRegister getBlockEntityTypeR() {
        return blockEntityTypeR;
    }

    /**
     * A getter for this {@code menuTypeR}.
     *
     * @return This {@code menuTypeR}.
     */
    public DeferredRegister<MenuType<?>> getMenuTypeR() {
        return menuTypeR;
    }

    /**
     * A getter for this {@code locked}.
     *
     * @return This {@code locked}.
     */
    public boolean isLocked() {
        return this.locked;
    }
    //endregion
    //region Setters

    /**
     * A method to lock this register.
     */
    public void lock() {
        this.locked = true;
    }
    //endregion

    @Mod.EventBusSubscriber(modid = C.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvent{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            event.enqueueWork(() -> MachineTileRegister.MACHINE_MENUS_TYPES.values().stream().map(LazyPointer::get).map(RegistryObject::get).forEach(ClientEvent::connect));
        }

        private static void connect(MenuType<MachineMenu> menu){
            MenuScreens.register(menu, MachineScreen::new);
        }
    }
}