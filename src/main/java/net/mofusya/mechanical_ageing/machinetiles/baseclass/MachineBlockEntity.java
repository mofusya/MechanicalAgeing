package net.mofusya.mechanical_ageing.machinetiles.baseclass;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.handlers.MAgCapabilities;
import net.mofusya.mechanical_ageing.handlers.OptionalMasterHandler;
import net.mofusya.mechanical_ageing.handlers.matter.IMatterStackHandler;
import net.mofusya.mechanical_ageing.handlers.matter.MatterStackHandler;
import net.mofusya.mechanical_ageing.handlers.units.unit.*;
import net.mofusya.mechanical_ageing.machinetiles.MachineTile;
import net.mofusya.mechanical_ageing.machinetiles.components.slot.ItemSlot;
import net.mofusya.mechanical_ageing.machinetiles.components.slot.MatterSlot;
import net.mofusya.mechanical_ageing.machinetiles.components.slot.UnitSlot;
import net.mofusya.mechanical_ageing.matter.IMatterStack;
import net.mofusya.ornatelib.block.entity.SyncedBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MachineBlockEntity extends SyncedBlockEntity implements MenuProvider {
    private final MachineTile machineTile;

    private final OptionalMasterHandler<ItemStackHandler, IItemHandler> itemMasterHandler;
    private final OptionalMasterHandler<MatterStackHandler, IMatterStackHandler> matterMasterHandler;
    private final OptionalMasterHandler<KelvinUnitHandler, IKelvinUnitHandler> kelvinMasterHandler;
    private final OptionalMasterHandler<RaltsUnitHandler, IRaltsUnitHandler> raltsMasterHandler;
    private final OptionalMasterHandler<VaarrUnitHandler, IVaarrUnitHandler> vaarrMasterHandler;
    private final OptionalMasterHandler<TsealvUnitHandler, ITsealvUnitHandler> tsealvMasterHandler;

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, MachineTile machineTile) {
        super(type, pos, state);
        this.machineTile = machineTile;

        List<ItemSlot> itemSlots = machineTile.getItemSlots();
        this.itemMasterHandler = new OptionalMasterHandler<>(itemSlots.isEmpty() ? Optional.empty() : Optional.of(new ItemStackHandler(itemSlots.size()) {
            @Override
            protected void onContentsChanged(int slot) {
                MachineBlockEntity.this.setChange();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack itemStack) {
                return itemSlots.get(slot).isValidFunc().test(itemStack);
            }
        }));

        List<MatterSlot> matterSlots = machineTile.getMatterSlots();
        var matterHandlerSettings = matterSlots.stream().map(MatterSlot::settings).toList();
        this.matterMasterHandler = new OptionalMasterHandler<>(matterSlots.isEmpty() ? Optional.empty() : Optional.of(new MatterStackHandler(matterHandlerSettings, this::setChange) {
            @Override
            public boolean isValid(IMatterStack matterStack, int slot) {
                return matterSlots.get(slot).isValidFunc().test(matterStack);
            }
        }));

        List<UnitSlot> kelvinSlots = machineTile.getUnitSlots(MachineTile.UnitType.KELVIN);
        var kelvinHandlerSettings = kelvinSlots.stream().map(UnitSlot::settings).toList();
        this.kelvinMasterHandler = new OptionalMasterHandler<>(kelvinSlots.isEmpty() ? Optional.empty() : Optional.of(new KelvinUnitHandler(kelvinHandlerSettings, this::setChange)));

        List<UnitSlot> raltsSlots = machineTile.getUnitSlots(MachineTile.UnitType.RALTS);
        var raltsHandlerSettings = raltsSlots.stream().map(UnitSlot::settings).toList();
        this.raltsMasterHandler = new OptionalMasterHandler<>(raltsSlots.isEmpty() ? Optional.empty() : Optional.of(new RaltsUnitHandler(raltsHandlerSettings, this::setChange)));

        List<UnitSlot> vaarrSlots = machineTile.getUnitSlots(MachineTile.UnitType.VAARR);
        var vaarrHandlerSettings = vaarrSlots.stream().map(UnitSlot::settings).toList();
        this.vaarrMasterHandler = new OptionalMasterHandler<>(vaarrSlots.isEmpty() ? Optional.empty() : Optional.of(new VaarrUnitHandler(vaarrHandlerSettings, this::setChange)));

        List<UnitSlot> tsealvSlots = machineTile.getUnitSlots(MachineTile.UnitType.TSEALV);
        var tsealvHandlerSettings = tsealvSlots.stream().map(UnitSlot::settings).toList();
        this.tsealvMasterHandler = new OptionalMasterHandler<>(tsealvSlots.isEmpty() ? Optional.empty() : Optional.of(new TsealvUnitHandler(tsealvHandlerSettings, this::setChange)));
    }

    public void tick(Level level, BlockPos pos, BlockState state){
        this.getMachineTile().tick(level, pos, state, this);
    }

    public void drop() {
        if (this.getLevel() == null) return;

        this.getItemMasterHandler().getOptionalHandler().ifPresent(hanlder -> {
            SimpleContainer container = new SimpleContainer(hanlder.getSlots());
            for (int i = 0; i < hanlder.getSlots(); i++) {
                container.setItem(i, hanlder.getStackInSlot(i));
            }

            Containers.dropContents(this.getLevel(), this.getBlockPos(), container);
        });
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (this.getLevel() == null) return super.getCapability(cap, side);

        if (cap == ForgeCapabilities.ITEM_HANDLER && this.getLazyItemHandler().isPresent()) {
            return this.getLazyItemHandler().cast();
        }

        if (cap == MAgCapabilities.MATTER && this.getLazyMatterHandler().isPresent()) {
            return this.getLazyMatterHandler().cast();
        }

        if (cap == MAgCapabilities.KELVIN && this.getLazyKelvinHandler().isPresent()) {
            return this.getLazyKelvinHandler().cast();
        }

        if (cap == MAgCapabilities.RALTS && this.getLazyRaltsHandler().isPresent()) {
            return this.getLazyRaltsHandler().cast();
        }

        if (cap == MAgCapabilities.VAARR && this.getLazyVaarrHandler().isPresent()) {
            return this.getLazyVaarrHandler().cast();
        }

        if (cap == MAgCapabilities.TSEALV && this.getLazyTsealvHandler().isPresent()) {
            return this.getLazyTsealvHandler().cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.getItemMasterHandler().initializeLazyHandler();
        this.getMatterMasterHandler().initializeLazyHandler();

        this.getKelvinMasterHandler().initializeLazyHandler();
        this.getRaltsMasterHandler().initializeLazyHandler();
        this.getVaarrMasterHandler().initializeLazyHandler();
        this.getTsealvMasterHandler().initializeLazyHandler();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.getItemMasterHandler().invalidateLazyHandler();
        this.getMatterMasterHandler().invalidateLazyHandler();

        this.getKelvinMasterHandler().invalidateLazyHandler();
        this.getRaltsMasterHandler().invalidateLazyHandler();
        this.getVaarrMasterHandler().invalidateLazyHandler();
        this.getTsealvMasterHandler().invalidateLazyHandler();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        this.getItemMasterHandler().getOptionalHandler().ifPresent(handler -> tag.put(C.ITEM_STACK_HANDLER, handler.serializeNBT()));
        this.getMatterMasterHandler().getOptionalHandler().ifPresent(handler -> tag.put(C.MATTER_STACK_HANDLER, handler.serializeNBT()));

        this.getKelvinMasterHandler().getOptionalHandler().ifPresent(handler -> tag.put(C.KELVIN_HANDLER, handler.serializeNBT()));
        this.getRaltsMasterHandler().getOptionalHandler().ifPresent(handler -> tag.put(C.RALTS_HANDLER, handler.serializeNBT()));
        this.getVaarrMasterHandler().getOptionalHandler().ifPresent(handler -> tag.put(C.VAARR_HANDLER, handler.serializeNBT()));
        this.getTsealvMasterHandler().getOptionalHandler().ifPresent(handler -> tag.put(C.TSEALV_HANDLER, handler.serializeNBT()));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.getItemMasterHandler().getOptionalHandler().ifPresent(handler -> handler.deserializeNBT(tag.getCompound(C.ITEM_STACK_HANDLER)));
        this.getMatterMasterHandler().getOptionalHandler().ifPresent(handler -> handler.deserializeNBT(tag.getCompound(C.MATTER_STACK_HANDLER)));

        this.getKelvinMasterHandler().getOptionalHandler().ifPresent(handler -> handler.deserializeNBT(tag.getCompound(C.KELVIN_HANDLER)));
        this.getRaltsMasterHandler().getOptionalHandler().ifPresent(handler -> handler.deserializeNBT(tag.getCompound(C.RALTS_HANDLER)));
        this.getVaarrMasterHandler().getOptionalHandler().ifPresent(handler -> handler.deserializeNBT(tag.getCompound(C.VAARR_HANDLER)));
        this.getTsealvMasterHandler().getOptionalHandler().ifPresent(handler -> handler.deserializeNBT(tag.getCompound(C.TSEALV_HANDLER)));
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new MachineMenu(windowId, inventory, this, Optional.empty(), this.getMachineTile());
    }

    public MachineTile getMachineTile() {
        return machineTile;
    }

    public OptionalMasterHandler<ItemStackHandler, IItemHandler> getItemMasterHandler() {
        return itemMasterHandler;
    }

    public ItemStackHandler getItemHandler() {
        return this.getItemMasterHandler().getHandler();
    }

    protected LazyOptional<IItemHandler> getLazyItemHandler() {
        return this.getItemMasterHandler().getLazyHandler();
    }

    public OptionalMasterHandler<MatterStackHandler, IMatterStackHandler> getMatterMasterHandler() {
        return matterMasterHandler;
    }

    public MatterStackHandler getMatterHandler() {
        return this.getMatterMasterHandler().getHandler();
    }

    protected LazyOptional<IMatterStackHandler> getLazyMatterHandler() {
        return this.getMatterMasterHandler().getLazyHandler();
    }

    public OptionalMasterHandler<KelvinUnitHandler, IKelvinUnitHandler> getKelvinMasterHandler() {
        return kelvinMasterHandler;
    }

    public KelvinUnitHandler getKelvinHandler() {
        return this.getKelvinMasterHandler().getHandler();
    }

    public LazyOptional<IKelvinUnitHandler> getLazyKelvinHandler() {
        return this.getKelvinMasterHandler().getLazyHandler();
    }

    public OptionalMasterHandler<RaltsUnitHandler, IRaltsUnitHandler> getRaltsMasterHandler() {
        return raltsMasterHandler;
    }

    public RaltsUnitHandler getRaltsHandler() {
        return this.getRaltsMasterHandler().getHandler();
    }

    public LazyOptional<IRaltsUnitHandler> getLazyRaltsHandler() {
        return this.getRaltsMasterHandler().getLazyHandler();
    }

    public OptionalMasterHandler<VaarrUnitHandler, IVaarrUnitHandler> getVaarrMasterHandler() {
        return vaarrMasterHandler;
    }

    public VaarrUnitHandler getVaarrHandler() {
        return this.getVaarrMasterHandler().getHandler();
    }

    public LazyOptional<IVaarrUnitHandler> getLazyVaarrHandler() {
        return this.getVaarrMasterHandler().getLazyHandler();
    }

    public OptionalMasterHandler<TsealvUnitHandler, ITsealvUnitHandler> getTsealvMasterHandler() {
        return tsealvMasterHandler;
    }

    public TsealvUnitHandler getTsealvHandler() {
        return this.getTsealvMasterHandler().getHandler();
    }

    public LazyOptional<ITsealvUnitHandler> getLazyTsealvHandler() {
        return this.getTsealvMasterHandler().getLazyHandler();
    }
}
