package net.mofusya.mechanical_ageing.machinetiles.fluid;

import net.minecraft.world.level.material.Fluid;

import java.util.function.Function;

public record FluidSlotProperties(int x, int y, Function<Fluid, Boolean> isValidFunc, int capacity) {
}
