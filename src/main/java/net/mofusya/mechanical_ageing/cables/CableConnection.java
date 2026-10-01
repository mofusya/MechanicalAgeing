package net.mofusya.mechanical_ageing.cables;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum CableConnection implements StringRepresentable {
    NONE, NEUTRAL, INSERT, EXTRACT;

    public boolean is(CableConnection cableConnection){
        return this == cableConnection;
    }

    public CableConnection next(){
        return switch (this){
            case NONE -> NEUTRAL;
            case NEUTRAL -> INSERT;
            case INSERT -> EXTRACT;
            case EXTRACT -> NONE;
        };
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
