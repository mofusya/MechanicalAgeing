package net.kairayk.yklibrary.util.scrpos;

import java.awt.*;

public interface IScrPos<T extends Number> {
    default int[] toIntArray() {
        return new int[]{getX().intValue(), getY().intValue()};
    }

    default long[] toLongArray() {
        return new long[]{getX().longValue(), getY().longValue()};
    }

    default float[] toFloatArray() {
        return new float[]{getX().floatValue(), getY().floatValue()};
    }

    default double[] toDoubleArray() {
        return new double[]{getX().doubleValue(), getY().doubleValue()};
    }

    default Dimension toDim() {
        return new Dimension(getX().intValue(), getY().intValue());
    }

    default IScrPos<T> covertGUIpos() {
        return reverse(false, true);
    }

    default ScrPosF castF() {
        return ScrPosF.of(getX().floatValue(), getY().floatValue());
    }

    default ScrPosD castD() {
        return ScrPosD.of(getX().doubleValue(), getY().doubleValue());
    }

    default ScrPosI castI() {
        return ScrPosI.of(getX().intValue(), getY().intValue());
    }

    default ScrPosL castL() {
        return ScrPosL.of(getX().longValue(), getY().longValue());
    }

    IScrPos<T> copy();

    T getX();

    T getY();

    IScrPos<T> reverse(boolean x, boolean y);

    IScrPos<T> reverse();

    IScrPos<T> abs();

    IScrPos<T> add(IScrPos<?> amount);

    IScrPos<T> mul(IScrPos<?> amount);

    IScrPos<T> div(Number amount, boolean toCeil);

    IScrPos<T> div(Number amount);

    default IScrPos<T> div(IScrPos<?> div){
        return this.mul(ScrPosD.of(1.0 / div.getX().doubleValue(), 1.0 / div.getY().doubleValue()));
    }

    default IScrPos<T> mul(Number amount) {
        if (amount.doubleValue() == 1.0) return this;

        double multi = 1d / amount.doubleValue();
        return this.div(multi);
    }

    default IScrPos<T> addPolar(double radius, double radian) {
        return add(getPolar(radius, radian));
    }

    default IScrPos<T> mulPolar(double radius, double radian) {
        return mul(getPolar(radius, radian));
    }

    default IScrPos<Double> getPolar(double radius, double radian) {
        return ScrPos.ofD((Math.cos(radian) * radius), (Math.sin(radian) * radius));
    }


    default boolean contains(IScrPos<?> ul, IScrPos<?> lr) {
        return ul.castD().getX() < lr.castD().getX() &&
                ul.castD().getY() > lr.castD().getY() &&
                ul.castD().getX() <= getX().doubleValue() &&
                lr.castD().getX() >= getX().doubleValue() &&
                ul.castD().getY() >= getY().doubleValue() &&
                lr.castD().getY() <= getY().doubleValue();
    }

    default boolean contains(IScrPos<?> center, T length) {
        return this.contains(
                center.copy().add(ScrPosD.of(length, length).div(2, true).reverse(true, false)),
                center.copy().add(ScrPosD.of(length, length).div(2, false).reverse(false, true))
        );
    }
}