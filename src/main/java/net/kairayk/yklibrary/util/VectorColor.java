package net.kairayk.yklibrary.util;

import java.awt.*;

public record VectorColor(float r, float g, float b, float a) {
    public static final VectorColor WHITE = new VectorColor(1.0f, 1.0f, 1.0f, 1.0f);
    public static final VectorColor BLACK = new VectorColor(0.0f, 0.0f, 0.0f, 1.0f);
    public static final VectorColor TRANSPARENT = new VectorColor(0.0f, 0.0f, 0.0f, 0.0f);
    public static final VectorColor RED = new VectorColor(1.0f, 0.0f, 0.0f, 1.0f);
    public static final VectorColor ORANGE = new VectorColor(1.0f, 0.5f, 0.0f, 1.0f);
    public static final VectorColor YELLOW = new VectorColor(1.0f, 1.0f, 0.0f, 1.0f);
    public static final VectorColor CHARTREUSE = new VectorColor(0.5f, 1.0f, 0.0f, 1.0f);
    public static final VectorColor GREEN = new VectorColor(0.0f, 1.0f, 0.0f, 1.0f);
    public static final VectorColor SPRINGGREEN = new VectorColor(0.0f, 1.0f, 0.5f, 1.0f);
    public static final VectorColor CYAN = new VectorColor(0.0f, 1.0f, 1.0f, 1.0f);
    public static final VectorColor AZURE = new VectorColor(0.0f, 0.5f, 1.0f, 1.0f);
    public static final VectorColor BLUE = new VectorColor(0.0f, 0.0f, 1.0f, 1.0f);
    public static final VectorColor VIOLET = new VectorColor(0.5f, 0.0f, 1.0f, 1.0f);
    public static final VectorColor MAGENTA = new VectorColor(1.0f, 0.0f, 1.0f, 1.0f);
    public static final VectorColor ROSE = new VectorColor(1.0f, 0.0f, 0.5f, 1.0f);

    //Format -> 1.0 1.0, 1.0, 1.0
    public VectorColor() {
        this(0.8f, 0.8f, 0.8f, 1f);
    }

    public VectorColor(float amount) {
        this(amount, amount, amount, 1.0f);
    }

    public static VectorColor of(float r, float g, float b, float a) {
        return new VectorColor(r, g, b, a);
    }


    //region Vector Calc

    //region conversion from
    public static VectorColor fromAwtColor(Color color) {
        return new VectorColor(
                color.getRed() / 255.0f,
                color.getGreen() / 255.0f,
                color.getBlue() / 255.0f,
                color.getAlpha() / 255.0f
        );
    }

    public static VectorColor fromARGB(int argb) {
        float a = ((argb >> 24) & 0xFF) / 255.0f;
        float r = ((argb >> 16) & 0xFF) / 255.0f;
        float g = ((argb >> 8) & 0xFF) / 255.0f;
        float b = (argb & 0xFF) / 255.0f;
        return new VectorColor(r, g, b, a);
    }

    public VectorColor clamp() {
        return new VectorColor(
                Math.max(0.0f, Math.min(1.0f, this.r)),
                Math.max(0.0f, Math.min(1.0f, this.g)),
                Math.max(0.0f, Math.min(1.0f, this.b)),
                Math.max(0.0f, Math.min(1.0f, this.a))
        );
    }
    //endregion

    //region add
    public VectorColor add(VectorColor other) {
        return new VectorColor(
                this.r + other.r,
                this.g + other.g,
                this.b + other.b,
                this.a + other.a
        );
    }

    public VectorColor add(float fr, float fg, float fb, float fa) {
        return this.add(new VectorColor(fr, fg, fb, fa));
    }

    public VectorColor add(float factor) {
        return this.add(new VectorColor(factor, factor, factor, factor));
    }
    //endregion

    //region multiply
    public VectorColor multiply(VectorColor other) {
        return new VectorColor(
                this.r * other.r,
                this.g * other.g,
                this.b * other.b,
                this.a * other.a
        );
    }

    public VectorColor multiply(float fr, float fg, float fb, float fa) {
        return this.multiply(new VectorColor(fr, fg, fb, fa));
    }

    public VectorColor multiply(float factor) {
        return this.multiply(new VectorColor(factor, factor, factor, factor));
    }
    //endregion


    //region color helper

    public VectorColor lerp(VectorColor target, float delta) {
        return new VectorColor(
                this.r + (target.r - this.r) * delta,
                this.g + (target.g - this.g) * delta,
                this.b + (target.b - this.b) * delta,
                this.a + (target.a - this.a) * delta
        );
    }

    public VectorColor normalize() {
        float v = this.getLength();
        if (v <= 0f) return this;
        return new VectorColor(
                (this.r / v),
                (this.g / v),
                (this.b / v),
                this.a
        );
    }

    public float getLength() {
        return (float) Math.sqrt(this.r * this.r + this.g * this.g + this.b * this.b);
    }

    public VectorColor setHue(float amount, boolean isDegrees) {
        return shiftHue(amount - getHue(isDegrees), isDegrees);
    }

    public VectorColor shiftHue(float amount, boolean isDegrees) {
        double radian = isDegrees ? amount * Math.PI / 180 : amount;

        // 極座標シータを2次元座標へ変換
        double c = Math.cos(radian);
        double s = Math.sin(radian);
        double omc = 1.0f - c;

        // 行列成分を算出 (1/sqrt(3) ≒ 0.577350269f)
        double sqrt3sin = 0.577350269f * s;
        double div13omc = 0.333333333f * omc;

        double e0 = div13omc + c;
        double eA = div13omc + sqrt3sin;
        double eB = div13omc - sqrt3sin;

        // 変換行列を適用
        return new VectorColor(
                (float) (this.r * e0 + this.g * eB + this.b * eA),
                (float) (this.r * eA + this.g * e0 + this.b * eB),
                (float) (this.r * eB + this.g * eA + this.b * e0),
                this.a
        );
    }

    public VectorColor shiftHue(float radian) {
        return shiftHue(radian, false);
    }
    //endregion

    public VectorColor shiftChroma(float amount) {
        float chroma = getChroma();
        if (chroma == 0f) return this;
        return setChroma(chroma + amount);
    }

    //region conversion to
    public Color toAwtColor() {
        return new Color(
                Math.max(0.0f, Math.min(1.0f, this.r)),
                Math.max(0.0f, Math.min(1.0f, this.g)),
                Math.max(0.0f, Math.min(1.0f, this.b)),
                Math.max(0.0f, Math.min(1.0f, this.a))
        );
    }
    //endregion

    public int toARGB() {
        int ai = (int) (Math.max(0.0f, Math.min(1.0f, this.a)) * 255) & 0xFF;
        int ri = (int) (Math.max(0.0f, Math.min(1.0f, this.r)) * 255) & 0xFF;
        int gi = (int) (Math.max(0.0f, Math.min(1.0f, this.g)) * 255) & 0xFF;
        int bi = (int) (Math.max(0.0f, Math.min(1.0f, this.b)) * 255) & 0xFF;
        return (ai << 24) | (ri << 16) | (gi << 8) | bi;
    }

    //region getter
    public float getMax() {
        return Math.max(this.r, Math.max(this.g, this.b));
    }

    public float getMin() {
        return Math.min(this.r, Math.min(this.g, this.b));
    }
    //endregion

    public float getHue() {
        float max = getMax();
        float min = getMin();
        float delta = max - min;

        if (delta <= 0.00001f) return 0f;

        float hue;
        if (max == this.r) {
            hue = (this.g - this.b) / delta;
        } else if (max == this.g) {
            hue = ((this.b - this.r) / delta) + 2.0f;
        } else {
            hue = ((this.r - this.g) / delta) + 4.0f;
        }

        hue *= 60f;
        if (hue < 0f) hue += 360f;
        return hue;
    }

    public VectorColor setHue(float radian) {
        return shiftHue(radian - getHue(false), false);
    }

    public float getHue(boolean isDegrees) {
        float degrees = getHue();
        if (isDegrees) {
            return degrees;
        }
        return degrees * (float) Math.PI / 180.0f;
    }

    public float getChroma() {
        float max = getMax();
        if (max <= 0f) return 0f;
        return (max - getMin()) / max;
    }

    public VectorColor setChroma(float amount) {
        float chroma = getChroma();
        if (chroma == 0f) return this;

        float max = getMax();

        float nr = this.r / max;
        float ng = this.g / max;
        float nb = this.b / max;


        float ratio = amount / chroma;

        return new VectorColor(
                max * (1f - (1f - nr) * ratio),
                max * (1f - (1f - ng) * ratio),
                max * (1f - (1f - nb) * ratio),
                this.a
        );
    }
    //endregion
}
