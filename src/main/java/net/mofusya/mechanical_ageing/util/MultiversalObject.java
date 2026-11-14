package net.mofusya.mechanical_ageing.util;

import net.minecraftforge.registries.RegistryObject;

public class MultiversalObject<T> {
    private final RegistryObject<T> normalObject;
    private final RegistryObject<T> multiversalObject;

    public MultiversalObject(RegistryObject<T> normalObject, RegistryObject<T> multiversalObject) {
        this.normalObject = normalObject;
        this.multiversalObject = multiversalObject;
    }

    public T get(){
        return this.getObject().get();
    }

    public T getMultiversal(){
        return this.getMultiversalObject().get();
    }

    public RegistryObject<T> getObject() {
        return this.normalObject;
    }

    public RegistryObject<T> getMultiversalObject() {
        return this.multiversalObject;
    }
}
