package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.machinetiles.IBgTileType;
import net.mofusya.mechanical_ageing.tiles.BgTileType;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.lang.SeptiLong;

@MethodsReturnNonNullByDefault
public class BrickSmeltingChamberTile extends SmeltingChamberTile {
    public BrickSmeltingChamberTile(ResourceLocation id) {
        super(id, Component.translatable("block.mechanical_ageing.brick_smelting_chamber.machine_name"), new UnLong(2400), new UnLong(300), UnLong.zero());
    }

    @Override
    public IBgTileType getBgTileType() {
        return BgTileType.BRICK;
    }
}
