package net.mofusya.mechanical_ageing.machinetiles.direction;

public enum DirectionType {
    NONE, UP, DOWN, RIGHT, LEFT, FRONT, BACK;

    public static int size() {
        return DirectionType.values().length;
    }

    public DirectionType next() {
        int direction = this.ordinal();
        if (direction >= DirectionType.size() - 1) {
            direction = 0;
        } else {
            direction++;
        }
        return DirectionType.values()[direction];
    }
}
