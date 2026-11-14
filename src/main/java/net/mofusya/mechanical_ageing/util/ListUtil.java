package net.mofusya.mechanical_ageing.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class ListUtil {
    public static <T> List<T> createFilledListFor(int indexCount, Supplier<T> fill){
        List<T> filledList = new ArrayList<>();
        for (int i = 0; i < indexCount; i++) {
            filledList.add(fill.get());
        }
        return filledList;
    }
}
