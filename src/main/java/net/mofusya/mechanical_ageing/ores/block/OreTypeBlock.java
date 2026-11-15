package net.mofusya.mechanical_ageing.ores.block;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.mofusya.mechanical_ageing.ores.IOreTypeItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class OreTypeBlock extends Block implements IOreTypeItemLike {
    private final String oreTypeId;
    @Nullable
    private final String itemTypePrefix;
    @Nullable
    private final String itemTypeSuffix;
    private final boolean isMultiversal;

    public OreTypeBlock(Properties build, String oreTypeId, @Nullable String itemTypePrefix, @Nullable String itemTypeSuffix, boolean isMultiversal) {
        super(build);
        this.oreTypeId = oreTypeId;
        this.itemTypePrefix = itemTypePrefix;
        this.itemTypeSuffix = itemTypeSuffix;
        this.isMultiversal = isMultiversal;
    }

    @Override @NotNull
    public MutableComponent getName() {
        var id = ForgeRegistries.BLOCKS.getKey(this);
        if (id == null) return super.getName();

        return this.getName(id);
    }

    @Override
    public String getOreTypeId() {
        return this.oreTypeId;
    }

    @Override @Nullable
    public String getItemTypePrefix() {
        return this.itemTypePrefix;
    }

    @Override @Nullable
    public String getItemTypeSuffix() {
        return this.itemTypeSuffix;
    }

    @Override
    public boolean isMultiversal() {
        return this.isMultiversal;
    }
}
