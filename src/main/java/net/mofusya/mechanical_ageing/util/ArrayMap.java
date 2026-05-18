package net.mofusya.mechanical_ageing.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ArrayMap<KEY, VALUE> {
    private final ArrayList<KEY> keys = new ArrayList<>();
    private final ArrayList<VALUE> values = new ArrayList<>();

    public void put(KEY key, VALUE value) {
        if (this.keys.contains(key)) {
            this.values.set(this.keys.indexOf(key), value);
        } else {
            this.keys.add(key);
            this.values.add(value);
        }
    }

    public void putAll(ArrayMap<KEY, VALUE> arrayMap){
        arrayMap.forEach(this::put);
    }

    public VALUE get(KEY key) {
        return this.values.get(this.keys.indexOf(key));
    }

    public final List<KEY> getKeys(VALUE pValue) {
        ArrayList<KEY> toReturn = new ArrayList<>();
        for (KEY key : this.keys) {
            if (this.get(key).equals(pValue)) {
                toReturn.add(key);
            }
        }
        return toReturn;
    }

    public ArrayList<KEY> getKeys() {
        return new ArrayList<>(this.keys);
    }

    public ArrayList<VALUE> getValues() {
        return new ArrayList<>(this.values);
    }

    public int size() {
        return this.getKeys().size();
    }

    public boolean hasContent() {
        return this.size() > 0;
    }

    public void forEach(BiConsumer<KEY, VALUE> func) {
        for (KEY key : this.getKeys()) {
            VALUE value = this.get(key);
            func.accept(key, value);
        }
    }
}
