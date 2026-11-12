package net.kairayk.yklibrary.util.scrpos;

public record ScrPosD(double x, double y) implements IScrPos<Double>{
    public ScrPosD() {
        this(0d,0d);
    }

    //region create
    public static ScrPosD of(Double x, Double y) {
        return new ScrPosD(x,y);
    }
    public static ScrPosD of(Double[] array) {
        return of(array[0], array[1]);
    }
    public static ScrPosD of(Number x, Number y){
        return of(x.doubleValue(), y.doubleValue());
    }
    //endregion

    //region getter
    @Override
    public ScrPosD copy() {
        return of(this.x, this.y);
    }
    @Override
    public Double getX() {
        return this.x;
    }

    @Override
    public Double getY() {
        return this.y;
    }
    //endregion

    //region basic calc
    @Override
    public ScrPosD reverse(boolean x, boolean y) {
        return of(
                x ? -this.x : this.x,
                y ? -this.y : this.y
        );
    }
    public ScrPosD reverse() {
        return reverse(true, true);
    }

    @Override
    public ScrPosD abs() {
        return of(Math.abs(this.x), Math.abs(this.y));
    }

    @Override
    public ScrPosD add(IScrPos<?> amount) {
        return of(
                this.x + amount.castD().getX(),
                this.y + amount.castD().getY()
        );
    }

    @Override
    public ScrPosD mul(IScrPos<?> amount) {
        return of(
                this.x * amount.castD().getX(),
                this.y * amount.castD().getY()
        );
    }

    @Override
    public ScrPosD div(Number amount, boolean toCeil) {
        double denominator = amount.doubleValue();
        double outX = (double) (this.x / denominator);
        double outY = (double) (this.y / denominator);
        return ScrPosD.of(
                toCeil ? Math.ceil(outX) : Math.floor(outX),
                toCeil ? Math.ceil(outY) : Math.floor(outY)
        );
    }

    @Override
    public ScrPosD div(Number amount) {
        double denominator = amount.doubleValue();
        return ScrPosD.of(
                this.x / denominator,
                this.y / denominator
        );
    }
    //endregion
}