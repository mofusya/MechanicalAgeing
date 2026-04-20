package net.mofusya.mechanical_ageing.machinetiles.button;

import net.minecraft.network.chat.MutableComponent;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineMenu;
import net.mofusya.mechanical_ageing.machinetiles.baseclass.MachineScreen;
import net.mofusya.mechanical_ageing.machinetiles.slot.SlotType;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public record ButtonProperties(int x, int y, @Nullable BiFunction<MachineScreen, MachineMenu, MutableComponent> labelFunc, SlotType type) {
}