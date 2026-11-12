package net.kairayk.yklibrary.util.scrfield;

import net.kairayk.yklibrary.util.scrpos.*;

public record ScrFieldD(ScrPosD ul, ScrPosD lr) implements IScrField<Double> {
    public ScrFieldD(ScrPosD ul, ScrPosD lr) {
        this.ul = ScrPosD.of(
                Math.min(ul.getX(), lr.getX()),
                Math.max(ul.getY(), lr.getY())
        );
        this.lr = ScrPosD.of(
                Math.max(ul.getX(), lr.getX()),
                Math.min(ul.getY(), lr.getY())
        );
    }



    //region create
    public static ScrFieldD of(ScrPosD ul, ScrPosD lr){
        return new ScrFieldD(ul, lr);
    }

    public static ScrFieldD of(ScrPosD center, double width, double height){
        return of(
                center.add(ScrPosD.of(width, height).div(2, true)),
                center.add(ScrPosD.of(width, height).div(2, false).reverse())
        );
    }
    //endregion



    //region getter
    @Override
    public ScrPosD getUpL() {
        return this.ul;
    }

    @Override
    public ScrPosD getLowR() {
        return this.lr;
    }

    @Override
    public ScrPosD getCenter() {
        return this.ul.add(this.lr).div(2);
    }

    @Override
    public ScrPosD getCenter(boolean toCeil) {
        return this.ul.add(this.lr).div(2, toCeil);
    }

    @Override
    public Double getArea() {
        return getWidth() * getHeight();
    }
    //endregion



    //region calc
    @Override
    public ScrFieldD offset(IScrPos<Double> amount) {
        return of(this.ul.add(amount), this.lr.add(amount));
    }

    @Override
    public ScrFieldD add(IScrPos<Double> amount) {
        return of(this.getCenter(),
                this.getWidth() + amount.getX(),
                this.getHeight() + amount.getY()
        );
    }

    @Override
    public ScrFieldD mul(IScrPos<Double> amount) {
        return of(this.getCenter(),
                this.getWidth() * amount.getX(),
                this.getHeight() * amount.getY()
        );
    }
    //endregion
}
