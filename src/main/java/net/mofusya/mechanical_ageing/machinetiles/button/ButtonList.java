package net.mofusya.mechanical_ageing.machinetiles.button;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineMenu;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineScreen;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class ButtonList extends ArrayList<ButtonProperties> {
    public ButtonList create(int x, int y, SlotType type) {
        this.create(x, y, (String) null, type);
        return this;
    }

    public ButtonList create(int x, int y, String label, SlotType type) {
        this.create(x, y, (screen, menu) -> Component.literal(label), type);
        return this;
    }

    public ButtonList create(int x, int y, @Nullable BiFunction<MachineScreen, MachineMenu, MutableComponent> labelFunc, SlotType type) {
        this.add(new ButtonProperties(x - 1, y - 1, labelFunc, type));
        return this;
    }
}
