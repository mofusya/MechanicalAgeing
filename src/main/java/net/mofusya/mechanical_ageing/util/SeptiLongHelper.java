package net.mofusya.mechanical_ageing.util;

import net.mofusya.ornatelib.lang.SeptiLong;
import net.mofusya.ornatelib.lang.SeptiLongValue;

import java.util.HashMap;
import java.util.Map;

public class SeptiLongHelper {
    private static final Map<SeptiLongValue, String> PREFIX = prefix();
    public static String convertToStringAndAddPrefix(SeptiLong septiLong){
        for (SeptiLongValue divide : PREFIX.keySet()){
            if (septiLong.copy().divideAndGetFloat(divide.get()) >= 1){
                var value = septiLong.copy().divide(divide.get());
                return value + PREFIX.get(divide);
            }
        }
        return septiLong.toString();
    }

    private static Map<SeptiLongValue, String> prefix(){
        Map<SeptiLongValue, String> suffix = new HashMap<>();
        suffix.put(SeptiLongValue.QUINTRIGINTILLION,"QiTg");
        suffix.put(SeptiLongValue.QUATTUORTRIGINTILLION,"QaTg");
        suffix.put(SeptiLongValue.TRESTRIGINTILLION,"TTg");
        suffix.put(SeptiLongValue.DUOTRIGINTILLION,"DTg");
        suffix.put(SeptiLongValue.UNTRIGINTILLION,"UTg");
        suffix.put(SeptiLongValue.TRIGINTILLION,"Tg");
        suffix.put(SeptiLongValue.NOVEMVIGINTILLION,"NoVg");
        suffix.put(SeptiLongValue.OCTOVIGINTILLION,"OcVg");
        suffix.put(SeptiLongValue.SEPTEMVIGINTILLION,"SpVg");
        suffix.put(SeptiLongValue.SESVIGINTILLION,"SeVg");
        suffix.put(SeptiLongValue.QUINVIGINTILLION,"QiVg");
        suffix.put(SeptiLongValue.QUATTUORVIGINTILLION,"QaVg");
        suffix.put(SeptiLongValue.TRESVIGINTILLION,"TVg");
        suffix.put(SeptiLongValue.DUOVIGINTILLION,"DVg");
        suffix.put(SeptiLongValue.UNVIGINTILLION,"UVg");
        suffix.put(SeptiLongValue.VIGINTILLION,"Vg");
        suffix.put(SeptiLongValue.NOVENDECILLION, "NoDe");
        suffix.put(SeptiLongValue.OCTODECILLION, "OcDe");
        suffix.put(SeptiLongValue.SEPTENDECILLION, "SpDe");
        suffix.put(SeptiLongValue.SEDECILLION, "SeDe");
        suffix.put(SeptiLongValue.QUINDECILLION, "QiDe");
        suffix.put(SeptiLongValue.QUATTUORDECILLION, "QaDe");
        suffix.put(SeptiLongValue.TREDECILLION, "TDe");
        suffix.put(SeptiLongValue.DUODECILLION, "DDe");
        suffix.put(SeptiLongValue.UNDECILLION, "UDe");
        suffix.put(SeptiLongValue.DECILLION, "De");
        suffix.put(SeptiLongValue.NONILLION, "No");
        suffix.put(SeptiLongValue.OCTILLION, "Oc");
        suffix.put(SeptiLongValue.SEPTILLION, "Sp");
        suffix.put(SeptiLongValue.SEXTILLION, "Se");
        suffix.put(SeptiLongValue.QUINTILLION, "Qi");
        suffix.put(SeptiLongValue.QUADRILLION, "Qa");
        suffix.put(SeptiLongValue.TRILLION, "T");
        suffix.put(SeptiLongValue.BILLION, "B");
        suffix.put(SeptiLongValue.MILLION, "M");
        suffix.put(SeptiLongValue.THOUSAND, "k");
        return suffix;
    }
}
