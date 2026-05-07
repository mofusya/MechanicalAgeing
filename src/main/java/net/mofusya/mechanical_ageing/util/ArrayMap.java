package net.mofusya.mechanical_ageing.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayMap<KEY, VALUE> {
    private final ArrayList<KEY> keys = new ArrayList<>();
    private final ArrayList<VALUE> values = new ArrayList<>();

    public void put(KEY key, VALUE value) {
        this.keys.add(key);
        this.values.add(value);
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

    public int size(){
        return this.getKeys().size();
    }
}
