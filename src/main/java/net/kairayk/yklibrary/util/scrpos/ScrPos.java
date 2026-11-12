package net.kairayk.yklibrary.util.scrpos;

@SuppressWarnings("unchecked")
public class ScrPos<T extends Number> implements IScrPos<T> {
    private double x, y;
    private final Class<T> type;

    public ScrPos(Number x, Number y, Class<T> type) {
        this.x = x.doubleValue();
        this.y = y.doubleValue();
        this.type = type;
    }



    //region create
    public static <T extends Number> ScrPos<T> of(Number x, Number y, Class<T> typeClass) {
        return new ScrPos<>(x, y, typeClass);
    }

    public static ScrPos<Double> ofD(Number x, Number y) {
        return new ScrPos<>(x, y, Double.class);
    }
    public static ScrPos<Float> ofF(Number x, Number y) {
        return new ScrPos<>(x, y, Float.class);
    }
    public static ScrPos<Integer> ofI(Number x, Number y) {
        return new ScrPos<>(x, y, Integer.class);
    }
    public static ScrPos<Long> ofL(Number x, Number y) {
        return new ScrPos<>(x, y, Long.class);
    }

    public static ScrPos<Double> ofD(IScrPos<Double> p) {
        return new ScrPos<>(p.getX(), p.getY(), Double.class);
    }
    public static ScrPos<Float> ofF(IScrPos<Float> p) {
        return new ScrPos<>(p.getX(), p.getY(), Float.class);
    }
    public static ScrPos<Integer> ofI(IScrPos<Integer> p) {
        return new ScrPos<>(p.getX(), p.getY(), Integer.class);
    }
    public static ScrPos<Long> ofL(IScrPos<Long> p) {
        return new ScrPos<>(p.getX(), p.getY(), Long.class);
    }
    //endregion



    //region getter
    @Override
    public ScrPos<T> copy() {
        return of(this.x, this.y, this.type);
    }

    @Override
    public T getX() {
        if (type == Integer.class) return (T) Integer.valueOf((int) x);
        if (type == Long.class)    return (T) Long.valueOf((long) x);
        if (type == Float.class)   return (T) Float.valueOf((float) x);
        return (T) Double.valueOf(x);
    }

    @Override
    public T getY() {
        if (type == Integer.class) return (T) Integer.valueOf((int) y);
        if (type == Long.class)    return (T) Long.valueOf((long) y);
        if (type == Float.class)   return (T) Float.valueOf((float) y);
        return (T) Double.valueOf(y);
    }
    //endregion



    //region calc
    public ScrPos<T> set(IScrPos<?> amount) {
        this.x = amount.castD().getX();
        this.y = amount.castD().getY();
        return this;
    }

    public ScrPos<T> reset(){
        this.x = 0;
        this.y = 0;
        return this;
    }

    @Override
    public ScrPos<T> add(IScrPos<?> amount) {
        this.x += amount.castD().getX();
        this.y += amount.castD().getY();
        return this;
    }

    @Override
    public ScrPos<T> mul(IScrPos<?> amount) {
        this.x *= amount.castD().getX();
        this.y *= amount.castD().getY();
        return this;
    }

    @Override
    public ScrPos<T> div(Number amount, boolean toCeil) {
        double d = amount.doubleValue();
        double outX = this.x / d;
        double outY = this.y / d;
        this.x = toCeil ? Math.ceil(outX) : Math.floor(outX);
        this.y = toCeil ? Math.ceil(outY) : Math.floor(outY);
        return this;
    }

    @Override
    public ScrPos<T> div(Number amount) {
        double d = amount.doubleValue();
        this.x /= d;
        this.y /= d;
        return this;
    }

    @Override
    public ScrPos<T> reverse(boolean x, boolean y) {
        if (x) this.x = -this.x;
        if (y) this.y = -this.y;
        return this;
    }

    @Override
    public ScrPos<T> reverse() {
        return reverse(true, true);
    }

    @Override
    public ScrPos<T> abs() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        return this;
    }
    //endregion



    //region build
    public ScrPosD buildD() {
        return ScrPosD.of(x, y);
    }

    public ScrPosF buildF() {
        return ScrPosF.of((float) x, (float) y);
    }

    public ScrPosI buildI() {
        return ScrPosI.of((int) x, (int) y);
    }

    public ScrPosL buildL() {
        return ScrPosL.of((long) x, (long) y);
    }
    //endregion
}