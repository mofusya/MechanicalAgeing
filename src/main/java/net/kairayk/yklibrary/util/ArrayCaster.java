package net.kairayk.yklibrary.util;

import java.util.Arrays;

public class ArrayCaster {
    public static Long[] toLong(long[] array){
        return Arrays.stream(array).boxed().toArray(Long[]::new);
    }
    public static Integer[] toInteger(int[] array){
        return Arrays.stream(array).boxed().toArray(Integer[]::new);
    }
    public static Double[] toDouble(double[] array){
        return Arrays.stream(array).boxed().toArray(Double[]::new);
    }
    public static Float[] toFloat(float[] array){
        Float[] out = new Float[array.length];
        for (int i = 0; i < array.length; i++) out[i] = array[i];
        return out;
    }

    public static long[] fromLong(Long[] array){
        return Arrays.stream(array).mapToLong(i->i).toArray();
    }
    public static int[] fromInteger(Integer[] array){
        return Arrays.stream(array).mapToInt(i->i).toArray();
    }
    public static double[] fromDouble(Double[] array){
        return Arrays.stream(array).mapToDouble(i->i).toArray();
    }
    public static float[] fromFloat(Float[] array){
        float[] out = new float[array.length];
        for (int i = 0; i < array.length; i++) out[i] = array[i];
        return out;
    }
}
