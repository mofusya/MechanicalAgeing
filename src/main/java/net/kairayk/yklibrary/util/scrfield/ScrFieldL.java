package net.kairayk.yklibrary.util.scrfield;

import net.kairayk.yklibrary.util.scrpos.*;

public record ScrFieldL(ScrPosL ul, ScrPosL lr) implements IScrField<Long> {
    public ScrFieldL(ScrPosL ul, ScrPosL lr) {
        this.ul = ScrPosL.of(
                Math.min(ul.getX(), lr.getX()),
                Math.max(ul.getY(), lr.getY())
        );
        this.lr = ScrPosL.of(
                Math.max(ul.getX(), lr.getX()),
                Math.min(ul.getY(), lr.getY())
        );
    }



    //region create
    public static ScrFieldL of(ScrPosL ul, ScrPosL lr){
        return new ScrFieldL(ul, lr);
    }

    public static ScrFieldL of(ScrPosL center, long width, long height){
        return of(
                center.add(ScrPosL.of(width, height).div(2, true)),
                center.add(ScrPosL.of(width, height).div(2, false).reverse())
        );
    }
    //endregion



    //region getter
    @Override
    public ScrPosL getUpL() {
        return this.ul;
    }

    @Override
    public ScrPosL getLowR() {
        return this.lr;
    }

    @Override
    public ScrPosL getCenter() {
        return this.ul.add(this.lr).div(2);
    }

    @Override
    public ScrPosL getCenter(boolean toCeil) {
        return this.ul.add(this.lr).div(2, toCeil);
    }

    @Override
    public Long getArea() {
        return getWidth() * getHeight();
    }
    //endregion



    //region calc
    @Override
    public ScrFieldL offset(IScrPos<Long> amount) {
        return of(this.ul.add(amount), this.lr.add(amount));
    }

    @Override
    public ScrFieldL add(IScrPos<Long> amount) {
        return of(this.getCenter(),
                this.getWidth() + amount.getX(),
                this.getHeight() + amount.getY()
        );
    }

    @Override
    public ScrFieldL mul(IScrPos<Long> amount) {
        return of(this.getCenter(),
                this.getWidth() * amount.getX(),
                this.getHeight() * amount.getY()
        );
    }
    //endregion
}
