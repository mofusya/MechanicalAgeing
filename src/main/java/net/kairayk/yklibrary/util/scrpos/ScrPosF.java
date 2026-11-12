package net.kairayk.yklibrary.util.scrpos;

public record ScrPosF(float x, float y) implements IScrPos<Float>{
    public ScrPosF() {
        this(0, 0);
    }

    //region create
    public static ScrPosF of(Float x, Float y) {
        return new ScrPosF(x,y);
    }
    public static ScrPosF of(Float[] array) {
        return of(array[0], array[1]);
    }
    public static ScrPosF of(Number x, Number y){
        return of(x.floatValue(), y.floatValue());
    }
    //endregion

    //region getter
    public ScrPosF copy() {
        return of(this.x, this.y);
    }
    @Override
    public Float getX() {
        return this.x;
    }

    @Override
    public Float getY() {
        return this.y;
    }
    //endregion

    //region basic calc
    @Override
    public ScrPosF reverse(boolean x, boolean y) {
        return of(
                x ? -this.x : this.x,
                y ? -this.y : this.y
        );
    }
    public ScrPosF reverse() {
        return reverse(true, true);
    }

    @Override
    public ScrPosF abs() {
        return of(Math.abs(this.x), Math.abs(this.y));
    }

    @Override
    public ScrPosF add(IScrPos<?> amount) {
        return of(
                this.x + amount.castF().getX(),
                this.y + amount.castF().getY()
        );
    }

    @Override
    public ScrPosF mul(IScrPos<?> amount) {
        return of(
                this.x * amount.castF().getX(),
                this.y * amount.castF().getY()
        );
    }

    @Override
    public ScrPosF div(Number amount, boolean toCeil) {
        double denominator = amount.doubleValue();

        double outX = (double) (this.x / denominator);
        double outY = (double) (this.y / denominator);
        return ScrPosF.of(
                toCeil ? Math.ceil(outX) : Math.floor(outX),
                toCeil ? Math.ceil(outY) : Math.floor(outY)
        );
    }

    @Override
    public ScrPosF div(Number amount) {
        double denominator = amount.doubleValue();
        return ScrPosF.of(
                this.x / denominator,
                this.y / denominator
        );
    }
    //endregion
}