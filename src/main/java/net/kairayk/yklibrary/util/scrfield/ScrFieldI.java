package net.kairayk.yklibrary.util.scrfield;

import net.kairayk.yklibrary.util.scrpos.*;

public record ScrFieldI(ScrPosI ul, ScrPosI lr) implements IScrField<Integer> {
    public ScrFieldI(ScrPosI ul, ScrPosI lr) {
        this.ul = ScrPosI.of(
                Math.min(ul.getX(), lr.getX()),
                Math.max(ul.getY(), lr.getY())
        );
        this.lr = ScrPosI.of(
                Math.max(ul.getX(), lr.getX()),
                Math.min(ul.getY(), lr.getY())
        );
    }



    //region create
    public static ScrFieldI of(ScrPosI ul, ScrPosI lr){
        return new ScrFieldI(ul, lr);
    }

    public static ScrFieldI of(ScrPosI center, int width, int height){
        return of(
                center.add(ScrPosI.of(width, height).div(2, true)),
                center.add(ScrPosI.of(width, height).div(2, false).reverse())
        );
    }
    //endregion



    //region getter
    @Override
    public ScrPosI getUpL() {
        return this.ul;
    }

    @Override
    public ScrPosI getLowR() {
        return this.lr;
    }

    @Override
    public ScrPosI getCenter() {
        return this.ul.add(this.lr).div(2);
    }

    @Override
    public ScrPosI getCenter(boolean toCeil) {
        return this.ul.add(this.lr).div(2, toCeil);
    }

    @Override
    public Integer getArea() {
        return getWidth() * getHeight();
    }
    //endregion



    //region calc
    @Override
    public ScrFieldI offset(IScrPos<Integer> amount) {
        return of(this.ul.add(amount), this.lr.add(amount));
    }

    @Override
    public ScrFieldI add(IScrPos<Integer> amount) {
        return of(this.getCenter(),
                this.getWidth() + amount.getX(),
                this.getHeight() + amount.getY()
        );
    }

    @Override
    public ScrFieldI mul(IScrPos<Integer> amount) {
        return of(this.getCenter(),
                this.getWidth() * amount.getX(),
                this.getHeight() * amount.getY()
        );
    }
    //endregion
}
