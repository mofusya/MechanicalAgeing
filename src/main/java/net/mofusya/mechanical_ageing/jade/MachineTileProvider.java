package net.mofusya.mechanical_ageing.jade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.MAg;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineBlockEntity;
import net.mofusya.mechanical_ageing.machinetiles.matter.MatterHandler;
import net.mofusya.mechanical_ageing.matter.MatterType;
import net.mofusya.mechanical_ageing.util.SeptiLongHelper;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum MachineTileProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        CompoundTag tag = accessor.getServerData();

        if (tag.contains("matterStorageSize")) {
            int matterStorageSize = tag.getInt("matterStorageSize");
            if (matterStorageSize > 0) {
                for (int i = 0; i < matterStorageSize; i++) {
                    String matterStorageName = tag.getString("matterStorageName_" + i);
                    String matterStorageAmount = tag.getString("matterStorageAmount_" + i);
                    String matterStorageCapacity = tag.getString("matterStorageCapacity_" + i);

                    tooltip.add(Component.translatable(matterStorageName).append(": " + matterStorageAmount + " / " + matterStorageCapacity));
                }
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        MachineBlockEntity blockEntity = (MachineBlockEntity) accessor.getBlockEntity();
        MatterHandler matterHandler = (MatterHandler) blockEntity.getMatterHandler();
        if (matterHandler == null) {
            tag.putInt("matterStorageSize", 0);
        } else {
            tag.putInt("matterStorageSize", matterHandler.size());
            for (int i = 0; i < matterHandler.size(); i++) {
                MatterType type = matterHandler.getStored(i).getType();

                tag.putString("matterStorageName_" + i, type == null ? "block.minecraft.air" : type.getTranslationId());

                if (accessor.getPlayer().isShiftKeyDown()) {
                    tag.putString("matterStorageAmount_" + i, matterHandler.getStored(i).getAmount() + (type == null ? "mB" : type.getSuffix()));
                    tag.putString("matterStorageCapacity_" + i, matterHandler.getMaxStored(i).toString() + (type == null ? "mB" : type.getSuffix()));
                } else {
                    tag.putString("matterStorageAmount_" + i, SeptiLongHelper.convertToStringAndAddSuffix(matterHandler.getStored(i).getAmount()) + (type == null ? "mB" : type.getSuffix()));
                    tag.putString("matterStorageCapacity_" + i, SeptiLongHelper.convertToStringAndAddSuffix(matterHandler.getMaxStored(i)) + (type == null ? "mB" : type.getSuffix()));
                }
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(MAg.MOD_ID, "machine_tile");
    }
}
