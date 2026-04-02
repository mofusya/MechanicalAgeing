package net.mofusya.mechanical_ageing.util;

import java.util.function.Supplier;

/// 初期化を遅延させるための疑似的なポインタクラス
public class LazyPointer<T> implements Supplier<T> {
    private T value;
    public T get() {
        return value;
    }
    public void set(T value) {
        this.value = value;
    }
}