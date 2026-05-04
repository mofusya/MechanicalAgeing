package net.mofusya.mechanical_ageing.items.implemts;

public interface IMetalAttributed {
    double getDensity();

    double getHardness();

    int getMeltingPoint();

    int getBoilingPoint();

    float getRadiationMultiplier();

    boolean isMagnetic();
}
