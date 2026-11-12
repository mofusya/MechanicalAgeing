package net.kairayk.yklibrary.util.scrpos;

public record ScrPosI(int x, int y) implements IScrPos<Integer>{
    public ScrPosI() {
        this(0, 0);
    }

    //region create
    public static ScrPosI of(Integer x, Integer y) {
        return new ScrPosI(x,y);
    }
    public static ScrPosI of(Integer[] array) {
        return of(array[0], array[1]);
    }
    public static ScrPosI of(Number x, Number y){
        return of(x.intValue(), y.intValue());
    }
    //endregion

    //region getter
    public ScrPosI copy() {
        return of(this.x, this.y);
    }
    @Override
    public Integer getX() {
        return this.x;
    }

    @Override
    public Integer getY() {
        return this.y;
    }
    //endregion

    //region basic calc
    @Override
    public ScrPosI reverse(boolean x, boolean y) {
        return of(
                x ? -this.x : this.x,
                y ? -this.y : this.y
        );
    }
    public ScrPosI reverse() {
        return reverse(true, true);
    }

    @Override
    public ScrPosI abs() {
        return of(Math.abs(this.x), Math.abs(this.y));
    }

    @Override
    public ScrPosI add(IScrPos<?> amount) {
        return of(
                this.x + amount.castI().getX(),
                this.y + amount.castI().getY()
        );
    }

    @Override
    public ScrPosI mul(IScrPos<?> amount) {
        return of(
                this.x * amount.castF().getX(),
                this.y * amount.castF().getY()
        );
    }

    @Override
    public ScrPosI div(Number amount, boolean toCeil) {
        double denominator = amount.doubleValue();
        double outX = (double) (this.x / denominator);
        double outY = (double) (this.y / denominator);
        return ScrPosI.of(
                toCeil ? Math.ceil(outX) : Math.floor(outX),
                toCeil ? Math.ceil(outY) : Math.floor(outY)
        );
    }

    @Override
    public ScrPosI div(Number amount) {
        double denominator = amount.doubleValue();
        return ScrPosI.of(
                this.x / denominator,
                this.y / denominator
        );
    }
    //endregion
}