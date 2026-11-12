package net.kairayk.yklibrary.util.scrfield;

import net.kairayk.yklibrary.util.scrpos.*;

@SuppressWarnings("unchecked")
public class ScrField<T extends Number> implements IScrField<T> {
    private final ScrPos<Double> ul;
    private final ScrPos<Double> lr;
    private final Class<T> type;

    public ScrField(IScrPos<?> ul, IScrPos<?> lr, Class<T> type) {
        this.ul = ScrPos.ofD(
                Math.min(ul.getX().doubleValue(), lr.getX().doubleValue()),
                Math.max(ul.getY().doubleValue(), lr.getY().doubleValue())
        );
        this.lr = ScrPos.ofD(
                Math.max(ul.getX().doubleValue(), lr.getX().doubleValue()),
                Math.min(ul.getY().doubleValue(), lr.getY().doubleValue())
        );
        this.type = type;
    }

    //region create
    public static <T extends Number> ScrField<T> of(IScrPos<?> ul, IScrPos<?> lr, Class<T> type) {
        return new ScrField<>(ul, lr, type);
    }

    public static <T extends Number> ScrField<T> of(IScrPos<?> center, Number width, Number height, Class<T> type) {
        return new ScrField<>(
                center.copy().add(ScrPos.of(width, height, type).div(2, true)),
                center.copy().add(ScrPos.of(width, height, type).div(2, false).reverse()),
                type);
    }
    //endregion



    //region getter
    @Override
    public ScrPos<T> getUpL() {
        return ScrPos.of(ul.getX(), ul.getY(), type);
    }

    @Override
    public ScrPos<T> getLowR() {
        return ScrPos.of(lr.getX(), lr.getY(), type);
    }

    @Override
    public T getWidth() {
        return convertType(getUpL().add(getLowR().reverse()).abs().getX().doubleValue());
    }

    @Override
    public T getHeight() {
        return convertType(getUpL().add(getLowR().reverse()).abs().getY().doubleValue());
    }

    @Override
    public ScrPos<T> getCenter() {
        ScrPos<Double> center = ul.copy().add(lr).div(2);
        return ScrPos.of(center.getX(), center.getY(), type);
    }

    @Override
    public ScrPos<T> getCenter(boolean toCeil) {
        ScrPos<Double> center = ul.copy().add(lr).div(2, toCeil);
        return ScrPos.of(center.getX(), center.getY(), type);
    }

    @Override
    public T getArea() {
        double area = getWidth().doubleValue() * getHeight().doubleValue();
        return convertType(area);
    }
    //endregion



    //region calc
    @Override
    public ScrField<T> offset(IScrPos<T> amount) {
        this.ul.add(amount);
        this.lr.add(amount);
        return this;
    }

    @Override
    public ScrField<T> add(IScrPos<T> amount) {
        ScrPos<Double> c = ul.copy().add(lr).div(2);
        ScrPos<Double> wh = ScrPos.ofD(
                getWidth().doubleValue() + amount.getX().doubleValue(),
                getHeight().doubleValue() + amount.getY().doubleValue());
        this.ul.set(c.copy().add(wh.copy().div(2, true)));
        this.lr.set(c.copy().add(wh.copy().div(2,false).reverse()));
        return this;
    }

    @Override
    public ScrField<T> mul(IScrPos<T> amount) {
        ScrPos<Double> c = ul.copy().add(lr).div(2);
        ScrPos<Double> wh = ScrPos.ofD(
                        getWidth().doubleValue() * amount.getX().doubleValue(),
                        getHeight().doubleValue() * amount.getY().doubleValue());
        this.ul.set(c.copy().add(wh.copy().div(2, true)));
        this.lr.set(c.copy().add(wh.copy().div(2,false).reverse()));
        return this;
    }
    //endregion



    //region build
    public ScrFieldD buildD() {
        return ScrFieldD.of(ul.buildD(), lr.buildD());
    }

    public ScrFieldF buildF() {
        return ScrFieldF.of(ul.buildF(), lr.buildF());
    }

    public ScrFieldI buildI() {
        return ScrFieldI.of(ul.buildI(), lr.buildI());
    }

    public ScrFieldL buildL() {
        return ScrFieldL.of(ul.buildL(), lr.buildL());
    }
    //endregion



    private T convertType(double val) {
        if (type == Integer.class) return (T) Integer.valueOf((int) val);
        if (type == Long.class)    return (T) Long.valueOf((long) val);
        if (type == Float.class)   return (T) Float.valueOf((float) val);
        return (T) Double.valueOf(val);
    }
}