package net.mofusya.mechanical_ageing.jade;

import net.kairayk.yklibrary.util.VectorColor;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.handlers.IUnitHandler;
import net.mofusya.mechanical_ageing.handlers.MAgCapabilities;
import net.mofusya.mechanical_ageing.handlers.units.DoubleUnitHandler;
import net.mofusya.mechanical_ageing.jade.style.MatterProgressStyle;
import net.mofusya.mechanical_ageing.jade.style.UnitProgressStyle;
import net.mofusya.mechanical_ageing.matter.IMatterStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.IProgressStyle;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public enum MAgComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag tag = blockAccessor.getServerData();

        if (tag.contains(C.MATTER_STACK_HANDLER)) {
            var matterHandlerTags = tag.getList(C.MATTER_STACK_HANDLER, Tag.TAG_COMPOUND).stream().map(pTag -> (CompoundTag) pTag).toList();

            IElementHelper helper = IElementHelper.get();
            for (int i = 0; i < matterHandlerTags.size(); i++) {
                CompoundTag matterHandlerTag = matterHandlerTags.get(i);
                MutableComponent handlerTooltip = Component.empty();

                if (matterHandlerTags.size() > 1) {
                    handlerTooltip.withStyle()
                            .append(Component.translatable("unit.mechanical_ageing.nealts.chamber"))
                            .append("#" + i)
                            .append(" ")
                            .append(Component.translatable(matterHandlerTag.getString(C.TYPE)));
                } else {
                    handlerTooltip.append(Component.translatable(matterHandlerTag.getString(C.TYPE)));
                }

                int color = matterHandlerTag.getInt(C.COLOR);
                double stored = matterHandlerTag.getDouble(C.STORED);
                double capacity = matterHandlerTag.getDouble(C.CAPACITY);

                handlerTooltip
                        .append(": ")
                        .append(String.valueOf(stored))
                        .append(" / ")
                        .append(String.valueOf(capacity))
                        .append(Component.translatable("unit.mechanical_ageing.nealts.short"));

                var matterProgressStyle = new MatterProgressStyle(VectorColor.fromARGB(color)).textColor(Objects.requireNonNull(ChatFormatting.GRAY.getColor()));
                tooltip.add(helper.progress((float) (stored / capacity), handlerTooltip, matterProgressStyle, BoxStyle.DEFAULT, true));
            }
        }

        this.appendUnitTooltip(tag, C.KELVIN_HANDLER, "kelvin", tooltip::add);
        this.appendUnitTooltip(tag, C.RALTS_HANDLER, "ralts", tooltip::add);
        this.appendUnitTooltip(tag, C.VAARR_HANDLER, "vaarr", tooltip::add);
        this.appendUnitTooltip(tag, C.TSEALV_HANDLER, "tsealv", tooltip::add);
    }

    public void appendUnitTooltip(CompoundTag tag, String tagId, String unitId, Consumer<IElement> addFunc) {
        if (tag.contains(tagId)) {
            List<CompoundTag> handlerTags = tag.getList(tagId, Tag.TAG_COMPOUND).stream().map(pTag -> (CompoundTag) pTag).toList();

            IElementHelper helper = IElementHelper.get();
            for (int i = 0; i < handlerTags.size(); i++) {
                CompoundTag handlerTag = handlerTags.get(i);
                MutableComponent handlerTooltip = Component.translatable("unit.mechanical_ageing." + unitId + ".chamber");

                if (handlerTags.size() > 1) {
                    handlerTooltip.append("#" + i);
                }

                int color = handlerTag.getInt(C.COLOR);
                double stored = handlerTag.getDouble(C.STORED);
                double capacity = handlerTag.getDouble(C.CAPACITY);

                handlerTooltip
                        .append(": ")
                        .append(String.valueOf(stored))
                        .append(" / ")
                        .append(String.valueOf(capacity))
                        .append(Component.translatable("unit.mechanical_ageing." + unitId + ".short"));

                var unitProgressStyle = new UnitProgressStyle(VectorColor.fromARGB(color)).textColor(Objects.requireNonNull(ChatFormatting.GRAY.getColor()));
                addFunc.accept(helper.progress((float) (stored / capacity), handlerTooltip, unitProgressStyle, BoxStyle.DEFAULT, true));
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
                matterHandlerTag.putInt(C.COLOR, stored.getType().getColor().toARGB());
                matterHandlerTag.putDouble(C.STORED, stored.getAmount());
                matterHandlerTag.putDouble(C.CAPACITY, capacity.getAmount());

                matterHandlerTags.add(matterHandlerTag);
            }

            tag.put(C.MATTER_STACK_HANDLER, matterHandlerTags);
        });

        this.add(blockEntity.getCapability(MAgCapabilities.KELVIN), C.KELVIN_HANDLER, tag::put);
        this.add(blockEntity.getCapability(MAgCapabilities.RALTS), C.RALTS_HANDLER, tag::put);
        this.add(blockEntity.getCapability(MAgCapabilities.VAARR), C.VAARR_HANDLER, tag::put);
        this.add(blockEntity.getCapability(MAgCapabilities.TSEALV), C.TSEALV_HANDLER, tag::put);
    }

    private void add(LazyOptional<? extends DoubleUnitHandler> lazyHandler, String tagId, BiConsumer<String, ListTag> putFunc) {
        lazyHandler.ifPresent(handler -> {
            putFunc.accept(tagId, convertDoubleUnitHandlerToJadeListTag(handler));
        });
    }

    private ListTag convertDoubleUnitHandlerToJadeListTag(DoubleUnitHandler handler) {
        ListTag handlerTags = new ListTag();

        for (int index = 0; index < handler.getSlotCount(); index++) {
            double stored = handler.getStored(index);
            double capacity = handler.getCapacity(index);

            CompoundTag handlerTag = new CompoundTag();
            handlerTag.putInt(C.COLOR, handler.getColor(index).toARGB());
            handlerTag.putDouble(C.STORED, stored);
            handlerTag.putDouble(C.CAPACITY, capacity);

            handlerTags.add(handlerTag);
        }

        return handlerTags;
    }

    @Override
    public ResourceLocation getUid() {
        return C.modLoc("provider");
    }
}