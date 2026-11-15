package net.mofusya.mechanical_ageing.ores.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.mofusya.mechanical_ageing.ores.IOreTypeItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class OreTypeBlockItem extends BlockItem implements IOreTypeItemLike {
    private final String oreTypeId;
    @Nullable
    private final String itemTypePrefix;
    @Nullable
    private final String itemTypeSuffix;
    private final boolean isMultiversal;

    public OreTypeBlockItem(Block block, Properties build, String oreTypeId, @Nullable String itemTypePrefix, @Nullable String itemTypeSuffix, boolean isMultiversal) {
        super(block, build);
        this.oreTypeId = oreTypeId;
        this.itemTypePrefix = itemTypePrefix;
        this.itemTypeSuffix = itemTypeSuffix;
        this.isMultiversal = isMultiversal;
    }

    @Override @NotNull
    public Component getName(@NotNull ItemStack itemStack) {
        var id = ForgeRegistries.ITEMS.getKey(this);
        if (id == null) return super.getName(itemStack);

        return this.getName(id);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return this.isMultiversal();
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
