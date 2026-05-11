package net.mofusya.mechanical_ageing.util;

@FunctionalInterface
public interface QuadConsumer<P1, P2, P3, P4> {
    void accept(P1 p1, P2 p2, P3 p3, P4 p4);
}
