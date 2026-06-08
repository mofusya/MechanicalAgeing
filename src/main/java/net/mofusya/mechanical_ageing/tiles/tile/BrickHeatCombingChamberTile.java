package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.machinetiles.IBgTileType;
import net.mofusya.mechanical_ageing.tiles.BgTileType;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.lang.SeptiLong;

@MethodsReturnNonNullByDefault
public class BrickHeatCombingChamberTile extends HeatCombingChamberTile {
    public BrickHeatCombingChamberTile(ResourceLocation id) {
        super(id, Component.translatable("block.mechanical_ageing.brick_heat_combining_chamber.machine_name"),
                new SeptiLong(2400), new SeptiLong(300), new SeptiLong());
    }

    @Override
    public IBgTileType getBgTileType() {
        return BgTileType.BRICK;
    }
}
