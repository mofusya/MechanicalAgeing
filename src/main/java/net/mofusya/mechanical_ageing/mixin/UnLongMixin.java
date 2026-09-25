package net.mofusya.mechanical_ageing.mixin;

import net.mofusya.ornatelib.lang.UnLong;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin({UnLong.class})
public class UnLongMixin {

    /*
    @Final
    @Shadow(remap = false)
    private static long LAYER_MAX_VALUE_PLUS_ONE;
    */

    /**
     * @author Mofusya
     * @reason For testing purposes.
     */
    /*
    @Overwrite(remap = false)
    public Float simulateDivAndGetFloat(@NotNull UnLong div) {
        if (isZero$MAg(div)) return Float.POSITIVE_INFINITY;

        UnLong self = (UnLong) (Object) this;

        AtomicInteger largestIndex = new AtomicInteger();
        self.forEachI((value, index) -> {
            if (value > 0 && largestIndex.get() < index) {
                largestIndex.set(index);
                return true;
            }
            return false;
        }, true);

        div.forEachI((value, index) -> {
            if (value > 0 && largestIndex.get() < index) {
                largestIndex.set(index);
                return true;
            }
            return false;
        }, true);

        float floatingSelf = (float) self.getValue(largestIndex.get()) * (float) LAYER_MAX_VALUE_PLUS_ONE;
        if (largestIndex.get() - 1 >= 0) {
            floatingSelf += (float) self.getValue(largestIndex.get() - 1);
        }

        float floatingDiv = (float) div.getValue(largestIndex.get()) * (float) LAYER_MAX_VALUE_PLUS_ONE;
        if (largestIndex.get() - 1 >= 0) {
            floatingDiv += (float) div.getValue(largestIndex.get() - 1);
        }
        if (floatingDiv <= 0f) return floatingDiv = 1f;

        return floatingSelf / floatingDiv;
    }


    @Unique
    private static boolean isZero$MAg(UnLong unLong) {
        return unLong.equals(UnLong.zero());
    }

     */
}
