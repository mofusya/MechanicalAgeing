package net.mofusya.mechanical_ageing.jade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.handlers.IUnitHandler;
import net.mofusya.mechanical_ageing.handlers.MAgCapabilities;
import net.mofusya.mechanical_ageing.matter.IMatterStack;
import org.stringtemplate.v4.ST;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public enum MAgComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag tag = blockAccessor.getServerData();

        if (tag.contains(C.MATTER_STACK_HANDLER)) {
            ListTag matterHandlerTags = tag.getList(C.MATTER_STACK_HANDLER, Tag.TAG_COMPOUND);
            for (CompoundTag matterHandlerTag : matterHandlerTags.stream().map(pTag -> (CompoundTag) pTag).toList()) {
                MutableComponent handlerTooltip = Component.translatable(matterHandlerTag.getString(C.TYPE))
                        .append(": ")
                        .append(matterHandlerTag.getString(C.STORED))
                        .append(" / ")
                        .append(matterHandlerTag.getString(C.CAPACITY))
                        .append(Component.translatable("unit.mechanical_ageing.nealts.short"));

                tooltip.add(handlerTooltip);
            }
        }

        this.appendUnitTooltip(tag, C.KELVIN_HANDLER, "kelvin", tooltip::add);
        this.appendUnitTooltip(tag, C.RALTS_HANDLER, "ralts", tooltip::add);
        this.appendUnitTooltip(tag, C.VAARR_HANDLER, "vaarr", tooltip::add);
        this.appendUnitTooltip(tag, C.TSEALV_HANDLER, "tsealv", tooltip::add);
    }

    public void appendUnitTooltip(CompoundTag tag, String tagId, String unitId, Consumer<Component> addFunc){
        if (tag.contains(tagId)){
            List<CompoundTag> handlerTags = tag.getList(tagId, Tag.TAG_COMPOUND).stream().map(pTag -> (CompoundTag) pTag).toList();

            for (int i = 0; i < handlerTags.size(); i++) {
                CompoundTag handlerTag = handlerTags.get(i);

                MutableComponent handlerTooltip = Component.translatable("unit.mechanical_ageing." + unitId + ".chamber")
                        .append("#" + i)
                        .append(": ")
                        .append(handlerTag.getString(C.STORED))
                        .append(" / ")
                        .append(handlerTag.getString(C.CAPACITY))
                        .append(Component.translatable("unit.mechanical_ageing." + unitId + ".short"));

                addFunc.accept(handlerTooltip);
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor blockAccessor) {
        BlockEntity blockEntity = blockAccessor.getBlockEntity();

        blockEntity.getCapability(MAgCapabilities.MATTER).ifPresent(handler -> {
            ListTag matterHandlerTags = new ListTag();

            for (int index = 0; index < handler.getSlotCount(); index++) {
                IMatterStack stored = handler.getStored(index);
                IMatterStack capacity = handler.getCapacity(index);

                CompoundTag matterHandlerTag = new CompoundTag();
                matterHandlerTag.putString(C.TYPE, stored.getType().getTranslationKey());
                matterHandlerTag.putString(C.STORED, String.valueOf(stored.getAmount()));
                matterHandlerTag.putString(C.CAPACITY, String.valueOf(capacity.getAmount()));

                matterHandlerTags.add(matterHandlerTag);
            }

            tag.put(C.MATTER_STACK_HANDLER, matterHandlerTags);
        });

        this.add(blockEntity.getCapability(MAgCapabilities.KELVIN), C.KELVIN_HANDLER, tag::put);
        this.add(blockEntity.getCapability(MAgCapabilities.RALTS), C.RALTS_HANDLER, tag::put);
        this.add(blockEntity.getCapability(MAgCapabilities.VAARR), C.VAARR_HANDLER, tag::put);
        this.add(blockEntity.getCapability(MAgCapabilities.TSEALV), C.TSEALV_HANDLER, tag::put);
    }

    private void add(LazyOptional<IUnitHandler<Double>> lazyHandler, String tagId, BiConsumer<String, ListTag> putFunc){
        lazyHandler.ifPresent(handler -> {
            putFunc.accept(tagId, convertDoubleUnitHandlerToJadeListTag(handler));
        });
    }

    private ListTag convertDoubleUnitHandlerToJadeListTag(IUnitHandler<Double> handler){
        ListTag handlerTags = new ListTag();

        for (int index = 0; index < handler.getSlotCount(); index++) {
            double stored = handler.getStored(index);
            double capacity = handler.getCapacity(index);

            CompoundTag handlerTag = new CompoundTag();
            handlerTag.putString(C.STORED, String.valueOf(stored));
            handlerTag.putString(C.CAPACITY, String.valueOf(capacity));

            handlerTags.add(handlerTag);
        }

        return handlerTags;
    }

    @Override
    public ResourceLocation getUid() {
        return C.modLoc("provider");
    }
}