package net.mofusya.mechanical_ageing.recipes;

import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.mofusya.ornatelib.lang.UnLong;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class UnLongJsonReader {

    private static final Map<String, Supplier<UnLong>> DIGITS = digits();

    private final String digitLoc;
    private final String multiplierLoc;

    public UnLongJsonReader(String digitLoc, String multiplierLoc) {
        this.digitLoc = digitLoc;
        this.multiplierLoc = multiplierLoc;
    }

    public UnLong get(JsonObject jsonObject){
        UnLong unLong = DIGITS.get(GsonHelper.getAsString(jsonObject, this.getDigitLoc())).get();
        return unLong.multi(GsonHelper.getAsLong(jsonObject, this.getMultiplierLoc()));
    }

    public String getDigitLoc() {
        return digitLoc;
    }

    public String getMultiplierLoc() {
        return multiplierLoc;
    }

    private static Map<String, Supplier<UnLong>> digits(){
        Map<String, Supplier<UnLong>> digits = new HashMap<>();

        addTo(digits, "zero", UnLong::zero);
        addTo(digits, "one", UnLong::one);
        addTo(digits, "ten", UnLong::ten);
        addTo(digits, "hundred", UnLong::hundred);
        addTo(digits, "thousand", UnLong::thousand);
        addTo(digits, "million", UnLong::million);

        addTo(digits, "billion", UnLong::billion);
        addTo(digits, "trillion", UnLong::trillion);
        addTo(digits, "quadrillion", UnLong::quadrillion);

        addTo(digits, "quintillion", UnLong::quintillion);
        addTo(digits, "sextillion", UnLong::sextillion);
        addTo(digits, "septillion", UnLong::septillion);

        addTo(digits, "octillion", UnLong::octillion);
        addTo(digits, "nonillion", UnLong::nonillion);
        addTo(digits, "decillion", UnLong::decillion);

        addTo(digits, "undecillion", UnLong::undecillion);
        addTo(digits, "duodecillion", UnLong::duodecillion);
        addTo(digits, "tredecillion", UnLong::tredecillion);

        addTo(digits, "quattuordecillion", UnLong::quattuordecillion);
        addTo(digits, "quindecillion", UnLong::quindecillion);
        addTo(digits, "sedecillion", UnLong::sedecillion);

        addTo(digits, "septendecillion", UnLong::septendecillion);
        addTo(digits, "octodecillion", UnLong::octodecillion);
        addTo(digits, "novendecillion", UnLong::novendecillion);

        addTo(digits, "vigintillion", UnLong::vigintillion);
        addTo(digits, "unvigintillion", UnLong::unvigintillion);
        addTo(digits, "duovigintillion", UnLong::duovigintillion);

        addTo(digits, "tresvigintillion", UnLong::tresvigintillion);
        addTo(digits, "quattuorvigintillion", UnLong::quattuorvigintillion);
        addTo(digits, "quinvigintillion", UnLong::quinvigintillion);

        addTo(digits, "sesvigintillion", UnLong::sesvigintillion);
        addTo(digits, "septemvigintillion", UnLong::septemvigintillion);
        addTo(digits, "octovigintillion", UnLong::octovigintillion);

        addTo(digits, "novemvigintillion", UnLong::novemvigintillion);
        addTo(digits, "trigintillion", UnLong::trigintillion);
        addTo(digits, "untrigintillion", UnLong::untrigintillion);

        addTo(digits, "duotrigintillion", UnLong::duotrigintillion);
        addTo(digits, "trestrigintillion", UnLong::trestrigintillion);
        addTo(digits, "quattuortrigintillion", UnLong::quattuortrigintillion);

        addTo(digits, "quintrigintillion", UnLong::quintrigintillion);
        addTo(digits, "sestrigintillion", UnLong::sestrigintillion);
        addTo(digits, "septentrigintillion", UnLong::septentrigintillion);

        addTo(digits, "octotrigintillion", UnLong::octotrigintillion);
        addTo(digits, "noventrigintillion", UnLong::noventrigintillion);
        addTo(digits, "quadragintillion", UnLong::quadragintillion);

        return digits;
    }

    public static void addTo(Map<String, Supplier<UnLong>> digits, String digit, Supplier<UnLong> unLongCreator){
        digits.put(digit, unLongCreator);
        digits.put(digit.toUpperCase(), unLongCreator);
    }
}
