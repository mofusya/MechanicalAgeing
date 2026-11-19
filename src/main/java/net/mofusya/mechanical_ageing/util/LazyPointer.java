package net.mofusya.mechanical_ageing.util;

import java.util.function.Supplier;

public class LazyPointer<T> implements Supplier<T> {
    private T value;

    @Override
    public T get() {
        return this.value;
    }

    public void set(T value){
        this.value = value;
    }
}
