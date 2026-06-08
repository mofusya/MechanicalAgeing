package net.mofusya.mechanical_ageing.machinetiles;

import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public record CapabilityOverride<T>(@Nullable Supplier<LazyOptional<T>> capabilityFunc, boolean override) {
    public CapabilityOverride(@Nullable Supplier<LazyOptional<T>> capabilityFunc, boolean override) {
        this.capabilityFunc = capabilityFunc;
        this.override = override;
    }

    @Override
    @Nullable
    public Supplier<LazyOptional<T>> capabilityFunc() {
        return this.capabilityFunc;
    }
}
