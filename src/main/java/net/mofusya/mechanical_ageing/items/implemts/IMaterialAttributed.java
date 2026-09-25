package net.mofusya.mechanical_ageing.items.implemts;

public interface IMaterialAttributed {
    double getDensity();

    double getHardness();

    int getMeltingPoint();

    int getBoilingPoint();

    float getRadiationMultiplier();
}
