package net.kairayk.yklibrary.util.scrpos;

public record ScrPosL(long x, long y) implements IScrPos<Long>{
    public ScrPosL() {
        this(0, 0);
    }

    //region create
    public static ScrPosL of(Long x, Long y) {
        return new ScrPosL(x,y);
    }
    public static ScrPosL of(Long[] array) {
        return of(array[0], array[1]);
    }
    public static ScrPosL of(Number x, Number y){
        return of(x.longValue(), y.longValue());
    }
    //endregion

    //region getter
    public ScrPosL copy() {
        return of(this.x, this.y);
    }
    @Override
    public Long getX() {
        return this.x;
    }

    @Override
    public Long getY() {
        return this.y;
    }
    //endregion

    //region basic calc
    @Override
    public ScrPosL reverse(boolean x, boolean y) {
        return of(
                x ? -this.x : this.x,
                y ? -this.y : this.y
        );
    }
    public ScrPosL reverse() {
        return reverse(true, true);
    }

    @Override
    public ScrPosL abs() {
        return of(Math.abs(this.x), Math.abs(this.y));
    }

    @Override
    public ScrPosL add(IScrPos<?> amount) {
        return of(
                this.x + amount.castL().getX(),
                this.y + amount.castL().getY()
        );
    }

    @Override
    public ScrPosL mul(IScrPos<?> amount) {
        return of(
                this.x * amount.castL().getX(),
                this.y * amount.castL().getY()
        );
    }

    @Override
    public ScrPosL div(Number amount, boolean toCeil) {
        double denominator = amount.doubleValue();
        double outX = (double) (this.x / denominator);
        double outY = (double) (this.y / denominator);
        return ScrPosL.of(
                toCeil ? Math.ceil(outX) : Math.floor(outX),
                toCeil ? Math.ceil(outY) : Math.floor(outY)
        );
    }

    @Override
    public ScrPosL div(Number amount) {
        double denominator = amount.doubleValue();
        return ScrPosL.of(
                this.x / denominator,
                this.y / denominator
        );
    }
    //endregion
}