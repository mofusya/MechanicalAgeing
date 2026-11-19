package net.mofusya.mechanical_ageing.machinetiles;

import net.kairayk.yklibrary.util.scrpos.ScrPosI;
import net.mofusya.mechanical_ageing.C;
import net.mofusya.mechanical_ageing.machinetiles.components.slot.ItemSlot;
import net.mofusya.mechanical_ageing.machinetiles.components.slot.MatterSlot;
import net.mofusya.mechanical_ageing.machinetiles.components.slot.UnitSlot;
import net.mofusya.mechanical_ageing.util.TripleEntry;
import net.mofusya.ornatelib.registries.OrnateHolder;
import net.mofusya.ornatelib.util.annotation.EverythingAreNonNullByDefault;

import java.util.List;

public class MAgMachineTiles {
    public static final MachineTileRegister R = new MachineTileRegister(C.MOD_ID);

    @EverythingAreNonNullByDefault
    public static final OrnateHolder<MachineTile> TEST = R.register("test", id -> new MachineTile(id) {
        @Override
        public List<ItemSlot> getItemSlots() {
            return List.of(
                    ItemSlot.of(ScrPosI.of(0, 0))
            );
        }

        @Override
        public List<MatterSlot> getMatterSlots() {
            return List.of(
                    MatterSlot.of(ScrPosI.of(0, 0), new TripleEntry<>(2048d))
            );
        }

        @Override
        public List<UnitSlot> getUnitSlots(UnitType type) {
            return List.of(
                    UnitSlot.of(ScrPosI.of(0, 0), new TripleEntry<>(4096d))
            );
        }
    });
}
