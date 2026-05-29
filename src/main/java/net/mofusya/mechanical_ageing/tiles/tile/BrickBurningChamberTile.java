package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.machinetiles.IBgTileType;
import net.mofusya.mechanical_ageing.tiles.BgTileType;
import net.mofusya.mechanical_ageing.util.annotations.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;

@MethodsReturnNonNullByDefault
public class BrickBurningChamberTile extends BurningChamberTile {
    public BrickBurningChamberTile(ResourceLocation id) {
        super(id, Component.translatable("block.mechanical_ageing.brick_burning_chamber.machine_name"),
                SeptiLongValue.MILLION.get(), SeptiLongValue.THOUSAND.get().multiply(10), SeptiLongValue.ZERO.get(),
                new SeptiLong(2400), SeptiLongValue.ZERO.get(), new SeptiLong(300));
    }

    @Override
    public IBgTileType getBgTileType() {
        return BgTileType.BRICK;
    }
}
