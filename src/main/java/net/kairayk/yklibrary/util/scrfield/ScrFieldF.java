package net.kairayk.yklibrary.util.scrfield;

import net.kairayk.yklibrary.util.scrpos.*;

public record ScrFieldF(ScrPosF ul, ScrPosF lr) implements IScrField<Float> {
    public ScrFieldF(ScrPosF ul, ScrPosF lr) {
        this.ul = ScrPosF.of(
                Math.min(ul.getX(), lr.getX()),
                Math.max(ul.getY(), lr.getY())
        );
        this.lr = ScrPosF.of(
                Math.max(ul.getX(), lr.getX()),
                Math.min(ul.getY(), lr.getY())
        );
    }



    //region create
    public static ScrFieldF of(ScrPosF ul, ScrPosF lr){
        return new ScrFieldF(ul, lr);
    }

    public static ScrFieldF of(ScrPosF center, float width, float height){
        return of(
                center.add(ScrPosF.of(width, height).div(2, true)),
                center.add(ScrPosF.of(width, height).div(2, false).reverse())
        );
    }
    //endregion



    //region getter
    @Override
    public ScrPosF getUpL() {
        return this.ul;
    }

    @Override
    public ScrPosF getLowR() {
        return this.lr;
    }

    @Override
    public ScrPosF getCenter() {
        return this.ul.add(this.lr).div(2);
    }

    @Override
    public ScrPosF getCenter(boolean toCeil) {
        return this.ul.add(this.lr).div(2, toCeil);
    }

    @Override
    public Float getArea() {
        return getWidth() * getHeight();
    }
    //endregion



    //region calc
    @Override
    public ScrFieldF offset(IScrPos<Float> amount) {
        return ScrFieldF.of(this.ul.add(amount), this.lr.add(amount));
    }

    @Override
    public ScrFieldF add(IScrPos<Float> amount) {
        return of(this.getCenter(),
                this.getWidth() + amount.getX(),
                this.getHeight() + amount.getY()
        );
    }

    @Override
    public ScrFieldF mul(IScrPos<Float> amount) {
        return of(this.getCenter(),
                this.getWidth() * amount.getX(),
                this.getHeight() * amount.getY()
        );
    }
    //endregion
}
