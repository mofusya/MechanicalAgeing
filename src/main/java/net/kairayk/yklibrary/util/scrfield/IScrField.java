package net.kairayk.yklibrary.util.scrfield;

import net.kairayk.yklibrary.util.scrpos.IScrPos;

public interface IScrField<T extends Number> {
    IScrPos<T> getUpL();
    IScrPos<T> getLowR();

    default T getWidth(){
        return getUpL().add(getLowR().reverse()).abs().getX();
    }
    default T getHeight(){
        return getUpL().add(getLowR().reverse()).abs().getY();
    }

    IScrPos<T> getCenter();
    IScrPos<T> getCenter(boolean toCeil);
    T getArea();

    IScrField<T> offset(IScrPos<T> amount);
    IScrField<T> add(IScrPos<T> amount);
    IScrField<T> mul(IScrPos<T> amount);

    default boolean isContains(IScrPos<T> pos) {
        return pos.contains(getUpL(), getLowR());
    }
}
