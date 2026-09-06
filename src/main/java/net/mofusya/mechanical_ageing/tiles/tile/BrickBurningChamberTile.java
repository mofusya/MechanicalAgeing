package net.mofusya.mechanical_ageing.tiles.tile;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.mofusya.mechanical_ageing.machinetiles.IBgTileType;
import net.mofusya.mechanical_ageing.tiles.BgTileType;
import net.mofusya.ornatelib.lang.UnLong;
import net.mofusya.ornatelib.util.annotation.MethodsReturnNonNullByDefault;
import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;

@MethodsReturnNonNullByDefault
public class BrickBurningChamberTile extends BurningChamberTile {
    public BrickBurningChamberTile(ResourceLocation id) {
        super(id, Component.translatable("block.mechanical_ageing.brick_burning_chamber.machine_name"),
                UnLong.million(), UnLong.thousand().multi(10), UnLong.zero(),
                new UnLong(2400), UnLong.zero(), new UnLong(300));
    }

    @Override
    public IBgTileType getBgTileType() {
        return BgTileType.BRICK;
    }
}
